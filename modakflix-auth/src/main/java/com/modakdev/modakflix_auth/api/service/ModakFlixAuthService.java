package com.modakdev.modakflix_auth.api.service;

import com.modakdev.models.entities.ModakFlixUser;
import org.springframework.stereotype.Service;


public interface ModakFlixAuthService {

    public String login(String username, String password);
    public boolean signUp(ModakFlixUser user);
    public void invalidateToken(String token);
    public boolean signUpAdmin(ModakFlixUser user);

}
