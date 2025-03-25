package com.modakdev.models.nonentities.request.auth;

public class ModakFlixAuthenticateRequest {
    String token;


    public ModakFlixAuthenticateRequest(String token) {
        this.token = token;
    }

    public ModakFlixAuthenticateRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ModakFlixAuthenticateRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
