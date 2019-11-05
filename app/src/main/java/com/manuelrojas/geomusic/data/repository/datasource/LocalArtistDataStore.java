package com.manuelrojas.geomusic.data.repository.datasource;

import com.manuelrojas.geomusic.data.entity.ArtistEntity;
import com.manuelrojas.geomusic.data.repository.datasource.db.ArtistDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class LocalArtistDataStore implements ArtistDataStore {

    private ArtistDao artistDao;

    @Inject
    public LocalArtistDataStore(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public Observable<List<ArtistEntity>> findAll() {
//        return artistDao.getAllArtists();
        return Observable.create(new ObservableOnSubscribe<List<ArtistEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArtistEntity>> emitter) {
                emitter.onNext(artistDao.getAllArtists());
                emitter.onComplete();
            }
        });

    }

    @Override
    public Observable<ArtistEntity> findById(String id) {
        return artistDao.getArtistById(id);
    }

    @Override
    public long save(ArtistEntity artist) {
        return artistDao.insertReplace(artist);
    }

    @Override
    public void update(ArtistEntity artist) {
        artistDao.update(artist);
    }

    @Override
    public Completable deleteAllArtists() {
        return artistDao.deleteAllArtists();
    }


}
