package com.modakdev.models.nonentities.response.management;

import com.modakdev.models.entities.ModakFlixShowsWatched;
import com.modakdev.models.nonentities.response.auth.ModakFlixBaseResponse;

import java.util.List;

public class ModakFlixShowsWatchedResponse extends ModakFlixBaseResponse {
    List<ModakFlixShowsWatched> showsWatchedList;

    public ModakFlixShowsWatchedResponse(List<ModakFlixShowsWatched> showsWatchedList) {
        this.showsWatchedList = showsWatchedList;
    }

    public ModakFlixShowsWatchedResponse() {
    }

    public List<ModakFlixShowsWatched> getShowsWatchedList() {
        return showsWatchedList;
    }

    public void setShowsWatchedList(List<ModakFlixShowsWatched> showsWatchedList) {
        this.showsWatchedList = showsWatchedList;
    }

    @Override
    public String toString() {
        return "ModakFlixShowsWatchedResponse{" +
                "showsWatchedList=" + showsWatchedList +
                ", txnId='" + txnId + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
