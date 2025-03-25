package com.modakdev.models.nonentities.request.auth;


import com.modakdev.models.entities.ModakFlixUser;

public class ModakFlixSignupRequest {

    ModakFlixUser user;

    public ModakFlixSignupRequest() {
    }

    public ModakFlixSignupRequest(ModakFlixUser user) {
        this.user = user;
    }

    public ModakFlixUser getUser() {
        return user;
    }

    public void setUser(ModakFlixUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ModakFlixSignupRequest{" +
                "user=" + user +
                '}';
    }
}
