package com.modakdev.models.nonentities.response.auth;

import com.modakdev.models.entities.ModakFlixUser;

import java.util.ArrayList;
import java.util.List;

public class ModakFlixListUsersResponse extends ModakFlixBaseResponse{
    List<ModakFlixUser> users;

    public ModakFlixListUsersResponse()
    {
        users = new ArrayList<>();
    }

    public List<ModakFlixUser> getUsers() {
        return users;
    }

    public void setUsers(List<ModakFlixUser> users) {
        users.forEach(user->user.setPassword("***"));
        this.users = users;
    }

    @Override
    public String toString() {
        return "ModakFlixListUsersResponse{" +
                "users=" + users +
                ", txnId='" + txnId + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
