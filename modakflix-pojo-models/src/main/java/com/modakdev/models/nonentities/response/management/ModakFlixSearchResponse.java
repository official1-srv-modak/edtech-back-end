package com.modakdev.models.nonentities.response.management;

import com.modakdev.models.entities.ModakFlixFileSystem;
import com.modakdev.models.nonentities.response.auth.ModakFlixBaseResponse;

import java.util.ArrayList;
import java.util.List;

public class ModakFlixSearchResponse extends ModakFlixBaseResponse {
    List<ModakFlixFileSystem> fileSystemArrayList;

    public ModakFlixSearchResponse(List<ModakFlixFileSystem> fileSystemArrayList) {
        this.fileSystemArrayList = fileSystemArrayList;
    }

    public ModakFlixSearchResponse() {
        fileSystemArrayList = new ArrayList<>();
    }

    public List<ModakFlixFileSystem> getFileSystemArrayList() {
        return fileSystemArrayList;
    }

    public void setFileSystemArrayList(List<ModakFlixFileSystem> fileSystemArrayList) {
        this.fileSystemArrayList = fileSystemArrayList;
    }

    @Override
    public String toString() {
        return "ModakFlixSearchResponse{" +
                "fileSystemArrayList=" + fileSystemArrayList +
                ", txnId='" + txnId + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
