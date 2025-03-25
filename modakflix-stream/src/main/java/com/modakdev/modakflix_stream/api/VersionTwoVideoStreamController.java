package com.modakdev.modakflix_stream.api;

import com.modakdev.modakflix_stream.api.service.FileSystemServiceImpl;
import com.modakdev.models.entities.ModakFlixFileSystem;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/video/v2")
public class VersionTwoVideoStreamController {

//    private static final String VIDEO_PATH = "path/to/your/video.mp4"; // Hardcoded video path
    private final String VIDEO_PATH = "movies/avengers.mp4";

    @Autowired
    private FileSystemServiceImpl fileSystemService;

    @GetMapping("/play/{id}")
    public ResponseEntity<byte[]> playVideo(@PathVariable("id") Long id,
                                            @RequestParam("resolution") String resolution,
                                            @RequestParam(value = "start", defaultValue = "0") long start// Accept start parameter
    ) throws IOException {
        // Get the video with the requested resolution
        File videoFile = getVideoWithResolution(id, resolution);

        // If the video does not exist, return a 404
        if (!videoFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Read the video into a byte array and return it
        try (InputStream videoInputStream = new FileInputStream(videoFile)) {
            byte[] videoBytes = videoInputStream.readAllBytes();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .body(videoBytes);
        }
    }

    public String getShowPath(Long id) // not good return fix this
    {
        ModakFlixFileSystem show = fileSystemService.getShowById(id);
        return show.getUrl();
    }

    // Method to generate video with the requested resolution using FFmpeg
    private File getVideoWithResolution(Long id, String resolution) throws IOException {
        // Hardcoded video path, as there's only one version of the video
        File videoFile = new File(getShowPath(id));

        // Output file path based on the requested resolution
        File outputFile = new File("movies/output/video_" + resolution + ".mp4");

        // If resolution is either 720p or 1080p, process the video
        if ("720p".equals(resolution) || "1080p".equals(resolution)) {
            if (!outputFile.exists()) {
                // Initialize FFmpeg library
                FFmpeg ffmpeg = new FFmpeg("/opt/homebrew/bin/ffmpeg"); // Path to your FFmpeg binary

                // Use FFmpegBuilder to configure the resizing process
                FFmpegBuilder builder = new FFmpegBuilder()
                        .setInput(videoFile.getAbsolutePath())  // Input file
                        .addOutput(outputFile.getAbsolutePath()) // Output file
                        .setVideoResolution("720p".equals(resolution) ? "1280x720" : "1920x1080") // Set video resolution
                        .done();

                // Run FFmpeg and process the video
                try {
                    ffmpeg.run(builder);
                } catch (IOException e) {
                    throw new IOException("Error processing video with FFmpeg", e);
                }
            }
        } else {
            // For invalid resolutions, return the original video
            return videoFile;
        }

        return outputFile;
    }
}
