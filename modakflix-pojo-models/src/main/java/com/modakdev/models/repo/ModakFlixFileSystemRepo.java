package com.modakdev.models.repo;

import com.modakdev.models.entities.ModakFlixFileSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModakFlixFileSystemRepo extends JpaRepository<ModakFlixFileSystem, Long> {
    public Optional<ModakFlixFileSystem> findById(Long id);
    public List<ModakFlixFileSystem> findByNameContaining(String query);// For partial match
}
