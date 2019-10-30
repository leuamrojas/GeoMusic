package com.manuelrojas.geomusic.domain;

import java.util.List;

import io.reactivex.Observable;

public interface ArtistRepository {

    Observable<List<Artist>> getArtists(int currentPage, boolean isDbInView);

    Observable<Artist> getArtist(String id);

}
