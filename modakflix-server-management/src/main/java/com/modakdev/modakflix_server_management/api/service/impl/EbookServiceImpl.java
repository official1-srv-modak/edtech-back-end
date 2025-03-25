package com.modakdev.modakflix_server_management.api.service.impl;

import com.modakdev.modakflix_server_management.api.service.EbookService;
import com.modakdev.models.entities.ModakFLixEBook;
import com.modakdev.models.nonentities.response.management.ModakFlixEbookCategoricalResponse;
import com.modakdev.models.repo.ModakFlixEbookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EbookServiceImpl implements EbookService {

    @Autowired
    ModakFlixEbookRepo ebookRepo;

    @Override
    public ModakFlixEbookCategoricalResponse getDirectoryStructure(String ebookPath) {
        ModakFlixEbookCategoricalResponse response = new ModakFlixEbookCategoricalResponse();
        File directory = new File(ebookPath);
        Map<String, Object> map = refreshDirectory(directory);
        if(map != null)
        {
            response.setFolderMap(map);
            response.setMessage("Successful");
            response.setStatus(HttpStatus.OK);
        }else {
            response.setMessage("Some error in folder structure");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }



    private Map<String, Object> refreshDirectory(File folder)
    {
        Map<String, Object> folderMap = new ConcurrentHashMap<>();
        List<ModakFLixEBook> filesList = new ArrayList<>();
        List<Map<String, Object>> subfolders = new ArrayList<>();

        // Get the list of files and subfolders in the current folder
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively process subfolders and include them in the hierarchy
                    Map<String, Object> subfolderMap = refreshDirectory(file);
                    subfolderMap.put("folderName", file.getName());  // Add the folder name to the map
                    subfolders.add(subfolderMap);
                } else if (file.isFile()) {
                    // Add file name to the list of files
                    Optional<ModakFLixEBook> eBookOpt = ebookRepo.findByPath(file.getAbsolutePath());
                    System.out.println(file.getAbsolutePath());
                    System.out.println(file.getName());
                    if(eBookOpt.isPresent())
                    {
                        filesList.add(new ModakFLixEBook(eBookOpt.get().getId(), file.getAbsolutePath(), file.getName()));
                        if(!ebookRepo.findByPath(file.getAbsolutePath()).isPresent())
                            ebookRepo.save(new ModakFLixEBook(file.getAbsolutePath(), file.getName()));
                    }
                    else{
                        ebookRepo.save(new ModakFLixEBook(file.getAbsolutePath(), file.getName()));
                        filesList.add(new ModakFLixEBook(file.getAbsolutePath(), file.getName()));
                    }
                }
            }
        }
        /*if(filesList.size() > 0)
        {*/
            folderMap = new HashMap<>();
            // Add the list of files to the folderMap (files are leaf nodes)
            folderMap.put("files", filesList);

            // Add the list of subfolders to the folderMap (subfolders are nested)
            if (!subfolders.isEmpty()) {
                folderMap.put("subfolders", subfolders);
            }

            // Add the folder name to the folderMap
            folderMap.put("folderName", folder.getName());
        /*}else {
            folderMap.put("files", filesList);
        }*/

        return folderMap;
    }

    public ResponseEntity<byte[]> streamEbook(Long ebookId) {
        // Fetch the ebook from the repo by its ID
        ModakFLixEBook ebook = ebookRepo.findById(ebookId).orElse(null);

        // Check if the ebook exists in the database
        if (ebook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Get the file path of the ebook
        String ebookPath = ebook.getPath();
        File ebookFile = new File(ebookPath);

        // Check if the file exists
        if (!ebookFile.exists()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Open the ebook file as an InputStream for streaming
        try (InputStream ebookInputStream = new FileInputStream(ebookFile)) {
            // Set the content type (you can adjust this based on the file type, here it assumes PDF)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", ebookFile.getName());

            // Read the file into a byte array (or stream it directly for larger files)
            byte[] ebookBytes = ebookInputStream.readAllBytes();

            // Return the ebook as a response entity with proper headers for streaming
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(ebookBytes);
        } catch (IOException e) {
            // Return an error if an issue occurs while opening or reading the file
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
