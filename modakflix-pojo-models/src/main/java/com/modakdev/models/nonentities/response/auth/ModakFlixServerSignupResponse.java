package com.modakdev.models.nonentities.response.auth;


import com.modakdev.models.entities.ModakFlixServerUser;
import com.modakdev.models.entities.ModakFlixUser;

public class ModakFlixServerSignupResponse extends ModakFlixBaseResponse{
    ModakFlixServerUser user;

    public ModakFlixServerSignupResponse(ModakFlixServerUser user) {
        user.setPassword("****");
        this.user = user;
    }

    public ModakFlixServerSignupResponse() {
    }

    public ModakFlixServerUser getUser() {
        return user;
    }

    public void setUser(ModakFlixServerUser user) {
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
