package com.manuelrojas.geomusic.presentation.di.module;

import android.app.Application;

import androidx.room.Room;

import com.manuelrojas.geomusic.data.repository.datasource.LocalTrackDataStore;
import com.manuelrojas.geomusic.data.repository.datasource.TrackDataStore;
import com.manuelrojas.geomusic.data.repository.datasource.db.ArtistDao;
import com.manuelrojas.geomusic.data.repository.datasource.db.TrackDao;
import com.manuelrojas.geomusic.data.repository.datasource.db.TrackDatabase;
import com.manuelrojas.geomusic.data.repository.datasource.ArtistDataStore;
import com.manuelrojas.geomusic.data.repository.datasource.LocalArtistDataStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private TrackDatabase trackDatabase;

    public RoomModule(Application mApplication) {
        trackDatabase = Room.databaseBuilder(mApplication, TrackDatabase.class, "track-db").build();
    }

    @Singleton
    @Provides
    TrackDatabase providesRoomDatabase() {
        return trackDatabase;
    }

    @Singleton
    @Provides
    ArtistDao providesArtistDao(TrackDatabase database) {
        return database.getArtistDao();
    }

    @Singleton
    @Provides
    ArtistDataStore providesArtistDataStore(ArtistDao artistDao) {
        return new LocalArtistDataStore(artistDao);
    }

    @Singleton
    @Provides
    TrackDao providesTrackDao(TrackDatabase database) {
        return database.getTrackDao();
    }

    @Singleton
    @Provides
    TrackDataStore providesTrackDataStore(TrackDao trackDao) {
        return new LocalTrackDataStore(trackDao);
    }

}