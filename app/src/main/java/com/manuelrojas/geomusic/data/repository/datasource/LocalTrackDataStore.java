package com.manuelrojas.geomusic.data.repository.datasource;

import com.manuelrojas.geomusic.data.entity.TrackEntity;
import com.manuelrojas.geomusic.data.repository.datasource.db.TrackDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalTrackDataStore implements TrackDataStore {

    private TrackDao trackDao;

    @Inject
    public LocalTrackDataStore(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Override
    public Observable<List<TrackEntity>> findAll() {
        return trackDao.getAllTracks();
    }

    @Override
    public Observable<TrackEntity> findById(String id) {
        return trackDao.getTrackById(id);
    }

    @Override
    public long save(TrackEntity track) {
        return trackDao.insertReplace(track);
    }

    @Override
    public void update(TrackEntity track) {
        trackDao.update(track);
    }
}
