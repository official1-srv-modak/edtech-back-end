package com.modakdev.models.nonentities.response.auth;


import com.modakdev.models.entities.ModakFlixUser;

public class ModakFlixAuthenticateResponse extends ModakFlixBaseResponse{
    ModakFlixUser user;
    String token;

    public ModakFlixAuthenticateResponse() {
    }

    public ModakFlixAuthenticateResponse(ModakFlixUser user, String token) {
        this.user = user;
        this.token = token;
    }

    public ModakFlixUser getUser() {
        return user;
    }

    public void setUser(ModakFlixUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ModakFlixAuthenticateResponse{" +
                "user=" + user +
                ", txnId='" + txnId + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
