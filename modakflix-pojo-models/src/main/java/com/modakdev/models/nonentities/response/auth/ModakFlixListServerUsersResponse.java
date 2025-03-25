package com.modakdev.models.nonentities.response.auth;

import com.modakdev.models.entities.ModakFlixServerUser;
import com.modakdev.models.entities.ModakFlixUser;

import java.util.ArrayList;
import java.util.List;

public class ModakFlixListServerUsersResponse extends ModakFlixBaseResponse{
    List<ModakFlixServerUser> users;

    public ModakFlixListServerUsersResponse()
    {
        users = new ArrayList<>();
    }

    public List<ModakFlixServerUser> getUsers() {
        return users;
    }

    public void setUsers(List<ModakFlixServerUser> users) {
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
