package com.modakdev.models.repo;

import com.modakdev.models.entities.ModakFlixShowsWatched;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModakFlixShowsWatchedRepo extends JpaRepository<ModakFlixShowsWatched, Long> {
    public List<ModakFlixShowsWatched> findByUsernameContaining(String username);
}
