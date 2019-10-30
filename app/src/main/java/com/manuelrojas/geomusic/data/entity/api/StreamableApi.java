package com.manuelrojas.geomusic.data.entity.api;

import com.google.gson.annotations.SerializedName;

public class StreamableApi {

    @SerializedName("#text")
    private String text;

    @SerializedName("fulltrack")
    private String fulltrack;

    @SerializedName("#text")
    public String getText() {
        return text;
    }

    @SerializedName("#text")
    public void setText(String text) {
        this.text = text;
    }

    @SerializedName("fulltrack")
    public String getFulltrack() {
        return fulltrack;
    }

    @SerializedName("fulltrack")
    public void setFulltrack(String fulltrack) {
        this.fulltrack = fulltrack;
    }
}