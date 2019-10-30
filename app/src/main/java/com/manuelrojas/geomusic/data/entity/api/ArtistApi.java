package com.manuelrojas.geomusic.data.entity.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistApi {

    @SerializedName("name")
    private String name;

    @SerializedName("listeners")
    private String listeners;

    @SerializedName("mbid")
    private String mbid;

    @SerializedName("url")
    private String url;

    @SerializedName("streamable")
    private String streamable;

    @SerializedName("image")
    private List<ImageApi> image = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStreamable() {
        return streamable;
    }

    public void setStreamable(String streamable) {
        this.streamable = streamable;
    }

    public List<ImageApi> getImage() {
        return image;
    }

    public void setImage(List<ImageApi> image) {
        this.image = image;
    }

}