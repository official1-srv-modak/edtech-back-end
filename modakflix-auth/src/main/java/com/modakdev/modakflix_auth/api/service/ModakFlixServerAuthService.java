package com.modakdev.modakflix_auth.api.service;

import com.modakdev.models.entities.ModakFlixServerUser;

public interface ModakFlixServerAuthService {
    public String login(String username, String password);
    public boolean generateApiKey(ModakFlixServerUser user);
    public void invalidateToken(String token);
    public boolean signUpAdmin(ModakFlixServerUser user);
}
