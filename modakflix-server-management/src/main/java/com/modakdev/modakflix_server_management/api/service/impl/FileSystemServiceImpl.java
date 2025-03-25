package com.modakdev.modakflix_server_management.api.service.impl;

import com.modakdev.modakflix_server_management.api.service.FileSystemService;
import com.modakdev.models.entities.ModakFlixFileSystem;
import com.modakdev.models.entities.ModakFlixShowsWatched;
import com.modakdev.models.nonentities.response.management.ModakFlixSearchResponse;
import com.modakdev.models.nonentities.response.management.ModakFlixShowsWatchedResponse;
import com.modakdev.models.repo.ModakFlixFileSystemRepo;
import com.modakdev.models.repo.ModakFlixShowsWatchedRepo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileSystemServiceImpl implements FileSystemService {

    @Autowired
    ModakFlixFileSystemRepo fileSystemRepo;

    @Autowired
    ModakFlixShowsWatchedRepo showsWatchedRepo;

    @Value("${ebook-folder-path}")
    private String ebookPath;

    private static final Logger logger = LoggerFactory.getLogger(FileSystemServiceImpl.class);


    @PostConstruct
    public void startOnStartUp()
    {
        logger.info("maintenance started on start up");
        maintainFileSystem();
    }

    @Override
    @Scheduled(cron = "0 */2 * * * *") // Runs every 2 minutes
    public void maintainFileSystem() {
        logger.info("maintenance");
    }

    public ModakFlixSearchResponse search(String query)
    {
        ModakFlixSearchResponse response = new ModakFlixSearchResponse();
        List<ModakFlixFileSystem> objects = fileSystemRepo.findByNameContaining(query);
        if(!objects.isEmpty())
        {
            response.setMessage("Successful");
            response.setStatus(HttpStatus.OK);
            response.setFileSystemArrayList(objects);
        }
        else
        {
            response.setMessage("Couldn't find anything matching : "+query);
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    public ModakFlixShowsWatchedResponse fetchShowsWatched(String username){
        ModakFlixShowsWatchedResponse response = new ModakFlixShowsWatchedResponse();
        List<ModakFlixShowsWatched> objects = showsWatchedRepo.findByUsernameContaining(username);
        if(!objects.isEmpty())
        {
            response.setMessage("Successful");
            response.setStatus(HttpStatus.OK);
            response.setShowsWatchedList(objects);
        }
        else
        {
            response.setMessage("Couldn't find the watch history of : "+username);
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
