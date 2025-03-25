package com.modakdev.modakflix_server_management.api;

import com.modakdev.modakflix_server_management.api.service.impl.FileSystemServiceImpl;
import com.modakdev.models.nonentities.response.management.ModakFlixSearchResponse;
import com.modakdev.models.nonentities.response.management.ModakFlixShowsWatchedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagementController {

    @Autowired
    FileSystemServiceImpl service;

    @GetMapping("shows/get-shows-watched/{username}")
    public ModakFlixShowsWatchedResponse getShowsWatched(@PathVariable("username")String username)
    {
        return service.fetchShowsWatched(username);
    }

    @RequestMapping("search/{query}")
    public ModakFlixSearchResponse search(@PathVariable String query)
    {
        ModakFlixSearchResponse response = service.search(query);
        return response;
    }
}
