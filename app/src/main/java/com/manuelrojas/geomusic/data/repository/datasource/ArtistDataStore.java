package com.manuelrojas.geomusic.data.repository.datasource;

import com.manuelrojas.geomusic.data.entity.ArtistEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface ArtistDataStore {

    Observable<List<ArtistEntity>> findAll();

    Observable<ArtistEntity> findById(String id);

    long save(ArtistEntity artist);

    void update(ArtistEntity artist);

    Completable deleteAllArtists();
}
