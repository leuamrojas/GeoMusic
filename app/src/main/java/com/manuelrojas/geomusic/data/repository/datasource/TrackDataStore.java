package com.manuelrojas.geomusic.data.repository.datasource;

import com.manuelrojas.geomusic.data.entity.TrackEntity;

import java.util.List;

import io.reactivex.Observable;

public interface TrackDataStore {

    Observable<List<TrackEntity>> findAll();

    Observable<TrackEntity> findById(String id);

    long save(TrackEntity track);

    void update(TrackEntity track);
}
