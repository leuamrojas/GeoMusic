package com.manuelrojas.geomusic.data.repository.datasource.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.manuelrojas.geomusic.data.entity.ArtistEntity;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface ArtistDao extends BaseDao<ArtistEntity> {

    @Query(value = "SELECT * FROM artist")
    Observable<List<ArtistEntity>> getAllArtists();

    @Query(value = "SELECT * FROM artist WHERE mbid = :artistId")
    Observable<ArtistEntity> getArtistById(String artistId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertReplace(ArtistEntity artistEntity);

    @Query("DELETE FROM artist")
    public void deleteAllArtists();

}
