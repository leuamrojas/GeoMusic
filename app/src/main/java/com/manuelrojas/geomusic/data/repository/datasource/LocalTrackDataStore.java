package com.manuelrojas.geomusic.data.repository.datasource;

import com.manuelrojas.geomusic.data.entity.TrackEntity;
import com.manuelrojas.geomusic.data.repository.datasource.db.TrackDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class LocalTrackDataStore implements TrackDataStore {

    private TrackDao trackDao;

    @Inject
    public LocalTrackDataStore(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

//    @Override
//    public Observable<List<TrackEntity>> findAll() {
//        return trackDao.getAllTracks();
//    }

    @Override
    public Observable<List<TrackEntity>> findAll() {
        return Observable.create(new ObservableOnSubscribe<List<TrackEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TrackEntity>> emitter) {
                emitter.onNext(trackDao.getAllTracks());
                emitter.onComplete();
            }
        });
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

    @Override
    public Completable deleteAllTracks() {
        return trackDao.deleteAllTracks();
    }
}
