package com.manuelrojas.geomusic.data.entity.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TracksApi {

    @SerializedName("track")
    private List<TrackApi> tracks;

    @SerializedName("@attr")
    private AttrApi attr;

    public List<TrackApi> getTracks() {
        return tracks;
    }

    public void setTrack(List<TrackApi> tracks) {
        this.tracks = tracks;
    }

    public AttrApi getAttr() {
        return attr;
    }

    public void setAttr(AttrApi attr) {
        this.attr = attr;
    }

}