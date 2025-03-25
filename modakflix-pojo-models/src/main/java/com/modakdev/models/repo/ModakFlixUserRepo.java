package com.modakdev.models.repo;

import com.modakdev.models.entities.ModakFlixUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModakFlixUserRepo extends JpaRepository<ModakFlixUser, Long> {

    public ModakFlixUser findByUsername(String username);
    public List<ModakFlixUser> findAll();

}
