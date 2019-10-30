package com.manuelrojas.geomusic.data.entity.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopArtistsApi {

    @SerializedName("artist")
    private List<ArtistApi> artist = null;

    @SerializedName("@attr")
    private AttrApi attr;

    public List<ArtistApi> getArtists() {
        return artist;
    }

    public void setArtists(List<ArtistApi> artist) {
        this.artist = artist;
    }

    public AttrApi getAttr() {
        return attr;
    }

    public void setAttr(AttrApi attr) {
        this.attr = attr;
    }
}
