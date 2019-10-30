package com.manuelrojas.geomusic.data.entity.api;

import com.google.gson.annotations.SerializedName;

public class RankApi {

    @SerializedName("rank")
    private String rank;

    @SerializedName("rank")
    public String getRank() {
        return rank;
    }

    @SerializedName("rank")
    public void setRank(String rank) {
        this.rank = rank;
    }
}