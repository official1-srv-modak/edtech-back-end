package com.modakdev.models.repo;

import com.modakdev.models.entities.ModakFlixServerUser;
import com.modakdev.models.entities.ModakFlixUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModakFlixServerUserRepo extends JpaRepository<ModakFlixServerUser, Long> {
    public ModakFlixServerUser findByUsername(String username);
}
