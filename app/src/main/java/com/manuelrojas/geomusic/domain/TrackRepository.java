package com.manuelrojas.geomusic.domain;

import java.util.List;

import io.reactivex.Observable;

public interface TrackRepository {

    Observable<List<Track>> getTracks(int currentPage, boolean isDbInView);

    Observable<Track> getTrack(String id);

}
