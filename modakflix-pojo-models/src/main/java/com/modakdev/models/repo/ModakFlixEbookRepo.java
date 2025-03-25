package com.modakdev.models.repo;

import com.modakdev.models.entities.ModakFLixEBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModakFlixEbookRepo extends JpaRepository<ModakFLixEBook, Long> {
    List<ModakFLixEBook> findAll();

    Optional<ModakFLixEBook> findById(Long id);

    Optional<ModakFLixEBook> findByPath(String path);

    // Case-insensitive search
    Optional<ModakFLixEBook> findByPathIgnoreCase(String path);
}
