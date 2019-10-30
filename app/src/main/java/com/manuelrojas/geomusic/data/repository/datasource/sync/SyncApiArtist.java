package com.manuelrojas.geomusic.data.repository.datasource.sync;

import com.manuelrojas.geomusic.BuildConfig;
import com.manuelrojas.geomusic.data.entity.ArtistEntity;
import com.manuelrojas.geomusic.data.entity.api.ArtistApi;
import com.manuelrojas.geomusic.data.repository.network.RetrofitClient;
import com.manuelrojas.geomusic.data.entity.mapper.EntityApiMapper;
import com.manuelrojas.geomusic.data.repository.datasource.ArtistDataStore;
import com.manuelrojas.geomusic.data.repository.network.GeoApiInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SyncApiArtist {

    private RetrofitClient client;
    private ArtistDataStore artistDataStore;
    private EntityApiMapper entityApiMapper;

    @Inject
    public SyncApiArtist(RetrofitClient client, ArtistDataStore artistDataStore,
                         EntityApiMapper entityApiMapper) {
        this.client = client;
        this.artistDataStore = artistDataStore;
        this.entityApiMapper = entityApiMapper;
    }

    public Observable<List<ArtistEntity>> syncArtists(int currentPage) {
        GeoApiInterface api = client.getRetrofit().create(GeoApiInterface.class);
        return api.getArtists(BuildConfig.API_KEY, currentPage)
                .map(t -> t.getTopartists().getArtists())
                .flatMap(a -> setArtists(a));
    }

    private Observable<List<ArtistEntity>> setArtists(List<ArtistApi> newArtistApis) {
        return Observable.create(new ObservableOnSubscribe<List<ArtistEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArtistEntity>> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                List<ArtistEntity> artistEntityList = new ArrayList<>();
                for (ArtistApi artistApi : newArtistApis) {
                    ArtistEntity artistEntity = entityApiMapper.transformArtistEntity(artistApi);
                    long result = artistDataStore.save(artistEntity);
                    if (result >= 0)
                        artistEntityList.add(artistEntity);
                }
                emitter.onNext(artistEntityList);
            }
        });
    }
}
