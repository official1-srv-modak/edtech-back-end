package com.modakdev.modakflix_stream.api;

import com.modakdev.modakflix_stream.api.service.FileSystemServiceImpl;
import com.modakdev.models.entities.ModakFlixFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/video")
public class VideoStreamController {

    @Autowired
    private FileSystemServiceImpl fileSystemService;

    private final String videoPath1 = "movies/video.mp4";

    @GetMapping("/stream-basic/{id}/{ffmpeg-flag}")
    public ResponseEntity<Resource> streamVideo(@PathVariable("id") Long id,
                                                @PathVariable("ffmpeg-flag") boolean flag,
                                                @RequestParam(value = "resolution", defaultValue = "auto") String resolution,
                                                @RequestHeader HttpHeaders headers) {
        ModakFlixFileSystem show = getShowName(id);
        String videoUrl = show.getUrl();
        if(!flag) {
            try {
                String resolutionDimensions = resolution.equalsIgnoreCase("auto") ? getAutoResolution(headers) : getResolutionDimensions(resolution);

                // Ensure the URL is valid and encode spaces
                URI encodedUri = new URI(videoUrl.replace(" ", "%20"));
                Resource resource = new UrlResource(encodedUri.toURL());

                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                            .body(resource);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } catch (MalformedURLException | URISyntaxException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        else {
            try {
                String resolutionDimensions = resolution.equalsIgnoreCase("auto") ? getAutoResolution(headers) : getResolutionDimensions(resolution);
                URI encodedUri = new URI(videoUrl.replace(" ", "%20"));

                // Construct FFmpeg command to process the video from an HTTP URL
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "ffmpeg", "-re", "-i", "\""+videoUrl+"\"", "-vf", "scale=" + resolutionDimensions, "-c:v", "libx264", "-preset", "ultrafast", "-f", "mpegts", "-"
                );

                // Print the command to console
                System.out.println("Executing FFmpeg command: " + String.join(" ", processBuilder.command()));

                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                InputStream inputStream = process.getInputStream();

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .body(new InputStreamResource(inputStream));
            } catch (URISyntaxException | IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
    }

//    @GetMapping(value = "/stream-basic/{id}", produces = "video/mp4")
//    public ResponseEntity<InputStreamResource> streamVideo(
//            @RequestParam(value = "resolution", defaultValue = "auto") String resolution,
//            @PathVariable("id") Long id,
//            @RequestHeader HttpHeaders headers) {
//
//        ModakFlixFileSystem show = getShowName(id);
//        String videoPath = show.getUrl();
//
//        return getVideoStream(resolution, headers, videoPath);
//    }

    private ResponseEntity<InputStreamResource> getVideoStream(String resolution, HttpHeaders headers, String videoPath) {
        // Determine resolution based on 'auto' or user input
        String resolutionDimensions = resolution.equalsIgnoreCase("auto") ? getAutoResolution(headers) : getResolutionDimensions(resolution);

        try {
            // Check for Range header for partial content streaming
            String range = headers.getFirst(HttpHeaders.RANGE);
            if (range != null && range.startsWith("bytes=")) { // browser run
                // Parse range
                String[] ranges = range.substring(6).split("-");
                long start = Long.parseLong(ranges[0]);
                long end = (ranges.length > 1) ? Long.parseLong(ranges[1]) : -1;

                // Open the video file for partial content
                RandomAccessFile file = new RandomAccessFile(videoPath, "r");
                long fileSize = file.length();

                // Validate the range values
                start = Math.min(start, fileSize);
                end = (end == -1) ? fileSize - 1 : Math.min(end, fileSize - 1);

                file.seek(start);

                // Set response headers for partial content
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.parseMediaType("video/mp4"));
                responseHeaders.set(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize);
                responseHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(end - start + 1));

                // Create an InputStream for the partial content
                long finalStart = start;
                long finalEnd = end;
                InputStream partialContentStream = new InputStream() {
                    private long bytesRemaining = finalEnd - finalStart + 1;

                    @Override
                    public int read() throws IOException {
                        if (bytesRemaining <= 0) {
                            return -1;  // End of stream
                        }
                        bytesRemaining--;
                        return file.read();  // Read one byte
                    }

                    @Override
                    public int read(byte[] b, int off, int len) throws IOException {
                        if (bytesRemaining <= 0) {
                            return -1;  // End of stream
                        }
                        int toRead = (int) Math.min(len, bytesRemaining);
                        int bytesRead = file.read(b, off, toRead);
                        if (bytesRead != -1) {
                            bytesRemaining -= bytesRead;
                        }
                        return bytesRead;
                    }
                };

                // Return partial content response
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .headers(responseHeaders)
                        .body(new InputStreamResource(partialContentStream));

            } else { // not from browser --> stream it
                // If no range, return full video
                System.out.println("Running ffmpeg command: ffmpeg -i " + videoPath + " -vf scale=" + resolutionDimensions + " -vcodec libx264 -acodec aac -f mp4 -movflags frag_keyframe+empty_moov -");
                ProcessBuilder builder = new ProcessBuilder(
                        "ffmpeg",
                        "-i", videoPath,
                        "-vf", "scale=" + resolutionDimensions,
                        "-vcodec", "libx264",       // Set video codec to H.264 for MP4 compatibility
                        "-acodec", "aac",           // Set audio codec to AAC
                        "-f", "mp4",                // Output format
                        "-movflags", "frag_keyframe+empty_moov", // Enables fragmented MP4 for streaming
                        "-");

                Process process = builder.start();
                InputStream videoStream = process.getInputStream();

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.parseMediaType("video/mp4"));

                // Return full video stream
                return new ResponseEntity<>(new InputStreamResource(videoStream), responseHeaders, HttpStatus.OK);
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/stream-direct", produces = "video/mp4")
    public ResponseEntity<InputStreamResource> streamVideoDirect(@RequestParam(value = "resolution", defaultValue = "auto") String resolution,
                                                                 @RequestParam(value = "start", defaultValue = "0") long start, // Accept start parameter
                                                                 @RequestHeader HttpHeaders headers) throws IOException {
        return getVideoStreamDirect(resolution, start, headers, videoPath1);
    }

    private ResponseEntity<InputStreamResource> getVideoStreamDirect(String resolution, long start, HttpHeaders headers, String videoPath) throws IOException {
        String resolutionDimensions = resolution.equalsIgnoreCase("auto") ? getAutoResolution(headers) : getResolutionDimensions(resolution);

        // ffmpeg command with -ss for starting position
        System.out.println("Running ffmpeg command: ffmpeg -i " + videoPath + " -ss " + start + " -vf scale=" + resolutionDimensions + " -vcodec libx264 -acodec aac -f mp4 -movflags frag_keyframe+empty_moov -");

        // Build the ffmpeg command with start position (-ss)
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg",
                "-i", "\""+videoPath+"\"",
                "-ss", String.valueOf(start), // Start position in seconds
                "-vf", "scale=" + resolutionDimensions,
                "-vcodec", "libx264",       // Set video codec to H.264 for MP4 compatibility
                "-acodec", "aac",           // Set audio codec to AAC
                "-f", "mp4",                // Output format
                "-movflags", "frag_keyframe+empty_moov", // Enables fragmented MP4 for streaming
                "-"
        );

        // Start the process
        Process process = builder.start();
        InputStream videoStream = process.getInputStream();

        // Set the response headers for video content
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.parseMediaType("video/mp4"));

        // Return the full video stream starting from the specified position
        return new ResponseEntity<>(new InputStreamResource(videoStream), responseHeaders, HttpStatus.OK);
    }

    private ResponseEntity<InputStreamResource> getVideoStreamDirectWindows(String resolution, long start, HttpHeaders headers, String videoPath) throws IOException {
        // Determine resolution dimensions
        String resolutionDimensions = resolution.equalsIgnoreCase("auto") ? getAutoResolution(headers) : getResolutionDimensions(resolution);

        // Sanitize the videoPath to ensure compatibility with Windows
        String sanitizedVideoPath = videoPath.replace("\\", "/"); // Convert backslashes to forward slashes for consistency

        // Debug log for the ffmpeg command
        System.out.println("Running ffmpeg command: ffmpeg -i \"" + sanitizedVideoPath + "\" -ss " + start + " -vf scale=" + resolutionDimensions +
                " -vcodec libx264 -acodec aac -f mp4 -movflags frag_keyframe+empty_moov -");

        // Build the ffmpeg command
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg",
                "-i", sanitizedVideoPath,        // Video input path
                "-ss", String.valueOf(start),   // Start position in seconds
                "-vf", "scale=" + resolutionDimensions, // Resolution scaling
                "-vcodec", "libx264",           // Video codec
                "-acodec", "aac",               // Audio codec
                "-f", "mp4",                    // Output format
                "-movflags", "frag_keyframe+empty_moov", // Fragmented MP4 for streaming
                "-"                              // Output to stdout
        );

        // Set the working directory for compatibility
        builder.directory(new File(System.getProperty("user.dir")));

        // Add environment variables if needed for Windows
        builder.environment().put("PATH", System.getenv("PATH"));

        // Start the ffmpeg process
        Process process = builder.start();
        InputStream videoStream = process.getInputStream();

        // Set the response headers for video content
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.parseMediaType("video/mp4"));

        // Return the video stream as the response
        return new ResponseEntity<>(new InputStreamResource(videoStream), responseHeaders, HttpStatus.OK);
    }



    private String getResolutionDimensions(String resolution) {
        switch (resolution.toLowerCase()) {
            case "144p":
                return "256x144";
            case "240p":
                return "426x240";
            case "360p":
                return "640x360";
            case "480p":
                return "854x480";
            case "720p":
                return "1280x720";
            case "1080p":
                return "1920x1080";
            default:
                return "1280x720"; // default to 720p if resolution not recognized
        }
    }

    private String getAutoResolution(HttpHeaders headers) {
        // Get network latency from client via header 'X-Network-Latency'
        String latencyHeader = headers.getFirst("X-Network-Latency");
        if (latencyHeader != null) {
            try {
                long latency = Long.parseLong(latencyHeader);
                return resolveResolutionBasedOnLatency(latency);
            } catch (NumberFormatException e) {
                // If invalid latency value, fallback to default behavior
                return "1280x720";
            }
        }
        return "1280x720"; // Default fallback if no latency header is present
    }

    private String resolveResolutionBasedOnLatency(long latency) {
        if (latency < 100) {
            return "1280x720"; // 720p for fast connections
        } else if (latency < 300) {
            return "854x480"; // 480p for moderate connections
        } else {
            return "426x240"; // 240p for slow connections
        }
    }


    @GetMapping("get-show/{id}")
    public ModakFlixFileSystem getShowName(@PathVariable Long id) // not good return fix this
    {
        ModakFlixFileSystem show = fileSystemService.getShowById(id);
        return show;
    }

    @GetMapping(value = "play-video/{id}", produces = "video/mp4")
    public ResponseEntity<InputStreamResource> playVideo(@PathVariable Long id,
                                                         @RequestParam(value = "resolution", defaultValue = "auto") String resolution,
                                                         @RequestParam(value = "start", defaultValue = "0") long start, // Accept start parameter
                                                         @RequestHeader HttpHeaders headers) throws IOException // for browser
    {
        ModakFlixFileSystem show = getShowName(id);
        String videoPath = show.getUrl();
        return getVideoStreamDirectWindows(resolution, start, headers, videoPath);
    }
}
