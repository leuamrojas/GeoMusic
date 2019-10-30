package com.manuelrojas.geomusic.data.entity.api;

import com.google.gson.annotations.SerializedName;

public class ArtistsApiResponse {

    @SerializedName("topartists")
    private TopArtistsApi topartists;

    public TopArtistsApi getTopartists() {
        return topartists;
    }

    public void setTopartists(TopArtistsApi topartists) {
        this.topartists = topartists;
    }
}
