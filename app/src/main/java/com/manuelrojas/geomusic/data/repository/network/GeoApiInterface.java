package com.manuelrojas.geomusic.data.repository.network;

import com.manuelrojas.geomusic.data.entity.api.TracksApiResponse;
import com.manuelrojas.geomusic.data.entity.api.ArtistsApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoApiInterface {

    @GET("/2.0/?method=geo.gettoptracks&country=spain&format=json&limit=20")
    Observable<TracksApiResponse> getTracks(@Query("api_key") String apikey, @Query("page") int currentPage);

    @GET("/2.0/?method=geo.gettopartists&country=spain&format=json&limit=20")
    Observable<ArtistsApiResponse> getArtists(@Query("api_key") String apikey, @Query("page") int currentPage);

}
