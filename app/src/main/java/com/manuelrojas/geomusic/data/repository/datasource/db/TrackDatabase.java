package com.manuelrojas.geomusic.data.repository.datasource.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.manuelrojas.geomusic.data.entity.ArtistEntity;
import com.manuelrojas.geomusic.data.entity.TrackEntity;

@Database(entities = {ArtistEntity.class, TrackEntity.class}, version = TrackDatabase.VERSION)
public abstract class TrackDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract ArtistDao getArtistDao();

    public abstract TrackDao getTrackDao();

}
