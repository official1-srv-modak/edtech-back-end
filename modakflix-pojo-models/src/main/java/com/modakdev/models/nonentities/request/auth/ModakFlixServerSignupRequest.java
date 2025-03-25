package com.modakdev.models.nonentities.request.auth;


import com.modakdev.models.entities.ModakFlixServerUser;
import com.modakdev.models.entities.ModakFlixUser;

public class ModakFlixServerSignupRequest {

    ModakFlixServerUser user;

    public ModakFlixServerSignupRequest() {
    }

    public ModakFlixServerSignupRequest(ModakFlixServerUser user) {
        this.user = user;
    }

    public ModakFlixServerUser getUser() {
        return user;
    }

    public void setUser(ModakFlixServerUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ModakFlixSignupRequest{" +
                "user=" + user +
                '}';
    }
}
