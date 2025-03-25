package com.modakdev.modakflix_stream.api.service;

import com.modakdev.models.entities.ModakFlixFileSystem;
import com.modakdev.models.repo.ModakFlixFileSystemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modakdev.*;

import java.util.Optional;

@Service
public class FileSystemServiceImpl {
    @Autowired
    ModakFlixFileSystemRepo repo;

    public ModakFlixFileSystem getShowById(Long id)
    {
        Optional<ModakFlixFileSystem> showFetch = repo.findById(id);
        return showFetch.orElse(null);
    }
}
