package com.manuelrojas.geomusic.data.repository.datasource.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.manuelrojas.geomusic.data.entity.TrackEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface TrackDao extends BaseDao<TrackEntity> {

    @Query(value = "SELECT * FROM track")
//    Observable<List<TrackEntity>> getAllTracks();
    List<TrackEntity> getAllTracks();

    @Query(value = "SELECT * FROM track WHERE id = :trackId")
    Observable<TrackEntity> getTrackById(String trackId);

    @Query(value = "SELECT * FROM track WHERE artist_id = :artistId")
    Observable<List<TrackEntity>> getTrackByArtist(String artistId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertReplace(TrackEntity trackEntity);

    @Query("DELETE FROM track")
    Completable deleteAllTracks();

}
