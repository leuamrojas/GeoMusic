package com.manuelrojas.geomusic.data.repository.datasource.sync;

import com.manuelrojas.geomusic.BuildConfig;
import com.manuelrojas.geomusic.data.repository.network.RetrofitClient;
import com.manuelrojas.geomusic.data.entity.TrackEntity;
import com.manuelrojas.geomusic.data.entity.api.TrackApi;
import com.manuelrojas.geomusic.data.entity.mapper.EntityApiMapper;
import com.manuelrojas.geomusic.data.repository.datasource.TrackDataStore;
import com.manuelrojas.geomusic.data.repository.network.GeoApiInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SyncApiTrack {

    private RetrofitClient client;
    private TrackDataStore trackDataStore;
    private EntityApiMapper entityApiMapper;

    @Inject
    public SyncApiTrack(RetrofitClient client, TrackDataStore trackDataStore,
                        EntityApiMapper entityApiMapper) {
        this.client = client;
        this.trackDataStore = trackDataStore;
        this.entityApiMapper = entityApiMapper;
    }

    public Observable<List<TrackEntity>> syncTracks(int currentPage) {
        GeoApiInterface api = client.getRetrofit().create(GeoApiInterface.class);

        return api.getTracks(BuildConfig.API_KEY, currentPage)
                .map(t -> t.getTracksApi().getTracks())
                .flatMap(trackApis -> setTracks(trackApis));
    }

    private Observable<List<TrackEntity>> setTracks(List<TrackApi> newTrackApis) {
        return Observable.create(new ObservableOnSubscribe<List<TrackEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TrackEntity>> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                List<TrackEntity> trackEntityList = new ArrayList<>();
                for (TrackApi trackApi : newTrackApis) {
                    TrackEntity trackEntity = entityApiMapper.transformTrackEntity(trackApi);
                    long result = trackDataStore.save(trackEntity);
                    if ( result > 0)
                        trackEntityList.add(trackEntity);
                }
                emitter.onNext(trackEntityList);
            }
        });
    }

}
