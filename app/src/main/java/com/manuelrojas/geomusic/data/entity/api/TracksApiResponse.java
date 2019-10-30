package com.manuelrojas.geomusic.data.entity.api;

import com.google.gson.annotations.SerializedName;

public class TracksApiResponse {

    @SerializedName("tracks")
    private TracksApi tracksApi;

    public TracksApi getTracksApi() {
        return tracksApi;
    }

    public void setTracksApi(TracksApi tracksApi) {
        this.tracksApi = tracksApi;
    }

}
