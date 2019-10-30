package com.manuelrojas.geomusic.data.repository.datasource;

import com.manuelrojas.geomusic.data.entity.ArtistEntity;
import com.manuelrojas.geomusic.data.repository.datasource.db.ArtistDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalArtistDataStore implements ArtistDataStore {

    private ArtistDao artistDao;

    @Inject
    public LocalArtistDataStore(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public Observable<List<ArtistEntity>> findAll() {
        return artistDao.getAllArtists();
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


}
