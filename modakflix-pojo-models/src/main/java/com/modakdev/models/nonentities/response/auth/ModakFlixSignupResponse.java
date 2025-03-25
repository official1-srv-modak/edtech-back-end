package com.modakdev.models.nonentities.response.auth;


import com.modakdev.models.entities.ModakFlixUser;

public class ModakFlixSignupResponse extends ModakFlixBaseResponse{
    ModakFlixUser user;

    public ModakFlixSignupResponse(ModakFlixUser user) {
        user.setPassword("****");
        this.user = user;
    }

    public ModakFlixSignupResponse() {
    }

    public ModakFlixUser getUser() {
        return user;
    }

    public void setUser(ModakFlixUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ModakFlixSignupResponse{" +
                "user=" + user +
                ", txnId='" + txnId + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
