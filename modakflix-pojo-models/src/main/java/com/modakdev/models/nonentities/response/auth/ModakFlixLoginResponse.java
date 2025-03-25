package com.modakdev.models.nonentities.response.auth;

public class ModakFlixLoginResponse extends ModakFlixBaseResponse{
    String token;

    public ModakFlixLoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
