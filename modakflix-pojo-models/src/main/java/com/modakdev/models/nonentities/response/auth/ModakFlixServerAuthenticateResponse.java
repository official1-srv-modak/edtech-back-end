package com.modakdev.models.nonentities.response.auth;


import com.modakdev.models.entities.ModakFlixServerUser;
import com.modakdev.models.entities.ModakFlixUser;

public class ModakFlixServerAuthenticateResponse extends ModakFlixBaseResponse{
    ModakFlixServerUser user;
    String token;

    public ModakFlixServerAuthenticateResponse() {
    }

    public ModakFlixServerAuthenticateResponse(ModakFlixServerUser user, String token) {
        this.user = user;
        this.token = token;
    }

    public ModakFlixServerUser getUser() {
        return user;
    }

    public void setUser(ModakFlixServerUser user) {
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
