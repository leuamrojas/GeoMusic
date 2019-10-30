package com.manuelrojas.geomusic.data.entity.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackApi {

    @SerializedName("name")
    private String name;

    @SerializedName("duration")
    private String duration;

    @SerializedName("listeners")
    private String listeners;

    @SerializedName("mbid")
    private String mbid;

    @SerializedName("url")
    private String url;

    @SerializedName("streamable")
    private StreamableApi streamable;

    @SerializedName("artist")
    private TrackArtistApi artist;

    @SerializedName("image")
    private List<ImageApi> image = null;

    @SerializedName("@attr")
    private RankApi rank;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public StreamableApi getStreamable() {
        return streamable;
    }

    public void setStreamable(StreamableApi streamable) {
        this.streamable = streamable;
    }

    public TrackArtistApi getTrackArtistApi() {
        return artist;
    }

    public void setTrackArtistApi(TrackArtistApi artist) {
        this.artist = artist;
    }

    public List<ImageApi> getImage() {
        return image;
    }

    public void setImage(List<ImageApi> image) {
        this.image = image;
    }

    public RankApi getAttr() {
        return rank;
    }

    public void setAttr(RankApi rank) {
        this.rank = rank;
    }

}