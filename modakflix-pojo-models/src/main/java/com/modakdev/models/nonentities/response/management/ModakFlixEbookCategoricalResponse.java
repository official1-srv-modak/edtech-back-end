package com.modakdev.models.nonentities.response.management;

import com.modakdev.models.nonentities.response.auth.ModakFlixBaseResponse;

import java.util.Map;

public class ModakFlixEbookCategoricalResponse extends ModakFlixBaseResponse {
    Map<String, Object> folderMap;

    public ModakFlixEbookCategoricalResponse(Map<String, Object> folderMap) {
        this.folderMap = folderMap;
    }

    public ModakFlixEbookCategoricalResponse() {
    }

    public Map<String, Object> getFolderMap() {
        return folderMap;
    }

    public void setFolderMap(Map<String, Object> folderMap) {
        this.folderMap = folderMap;
    }

    @Override
    public String toString() {
        return "EbookCategoricalResponse{" +
                "folderMap=" + folderMap +
                ", txnId='" + txnId + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
