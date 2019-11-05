package com.manuelrojas.geomusic.data.repository;

import android.util.Log;

import com.manuelrojas.geomusic.data.entity.TrackEntity;
import com.manuelrojas.geomusic.data.entity.mapper.TrackEntityDataMapper;
import com.manuelrojas.geomusic.data.repository.datasource.TrackDataStore;
import com.manuelrojas.geomusic.data.repository.datasource.sync.SyncApiTrack;
import com.manuelrojas.geomusic.domain.Track;
import com.manuelrojas.geomusic.domain.TrackRepository;
import com.manuelrojas.geomusic.data.utils.NetworkUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

public class TrackDataRepository implements TrackRepository {

    private TrackDataStore trackDataStore;
    private SyncApiTrack syncApiTrack;
    private TrackEntityDataMapper trackEntityDataMapper;

    @Inject
    NetworkUtil networkUtil;

    @Inject
    TrackDataRepository(TrackDataStore dataStore, TrackEntityDataMapper mapper,
                        SyncApiTrack syncApiTrack) {
        this.trackDataStore = dataStore;
        this.trackEntityDataMapper = mapper;
        this.syncApiTrack = syncApiTrack;
    }

    @Override
    public Observable<List<Track>> getTracks(int currentPage, boolean isDbInView) {
        if (networkUtil.isNetworkConnected()) {
            if (currentPage > 1) {
                if ( !isDbInView ) {
                    //App restart and Db has data. Get Db data
                    return getTracksFromDb()
                            .doOnNext(t -> Log.d("TrackDataRepository", "getTracksFromDb: " + t.size()))
                            .map(t -> trackEntityDataMapper.transform(t));
                } else {
                    //loadMore action. Get only the next page
                    return getTracksFromApi(currentPage)
                            .doOnNext(t -> Log.d("TrackDataRepository", "getTracksFromApi: " + t.size()))
                            .map(t -> trackEntityDataMapper.transform(t));
                }
            } else {
                //First load or swipe refresh
                return deleteTracksFromDb()
                        .andThen(getTracksFromApi(currentPage))
                        .map(t -> trackEntityDataMapper.transform(t));
            }
        } else {
            //Work with offline data
            return getTracksFromDb()
                    .map(t -> trackEntityDataMapper.transform(t));
        }

    }

    @Override
    public Observable<Track> getTrack(String id) {
        return trackDataStore.findById(id).map(t -> trackEntityDataMapper.transform(t));
    }

    private Observable<List<TrackEntity>> getTracksFromDb() {
        return trackDataStore.findAll();
    }

    private Observable<List<TrackEntity>> getTracksFromApi(int currentPage) {
        return syncApiTrack.syncTracks(currentPage);
    }

    private Completable deleteTracksFromDb() {
        return trackDataStore.deleteAllTracks();
    }

}
