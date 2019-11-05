package com.manuelrojas.geomusic.data.repository;

import com.manuelrojas.geomusic.data.entity.ArtistEntity;
import com.manuelrojas.geomusic.data.repository.datasource.sync.SyncApiArtist;
import com.manuelrojas.geomusic.data.utils.NetworkUtil;
import com.manuelrojas.geomusic.domain.Artist;
import com.manuelrojas.geomusic.data.entity.mapper.ArtistEntityDataMapper;
import com.manuelrojas.geomusic.data.repository.datasource.ArtistDataStore;
import com.manuelrojas.geomusic.domain.ArtistRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class ArtistDataRepository implements ArtistRepository {

    private ArtistDataStore artistDataStore;
    private SyncApiArtist syncApiArtist;
    private ArtistEntityDataMapper artistEntityDataMapper;

    @Inject
    NetworkUtil networkUtil;

    @Inject
    public ArtistDataRepository(ArtistDataStore dataStore, ArtistEntityDataMapper dataMapper,
                                SyncApiArtist syncApiArtist){
        this.artistDataStore = dataStore;
        this.artistEntityDataMapper = dataMapper;
        this.syncApiArtist = syncApiArtist;
    }

    @Override
    public Observable<List<Artist>> getArtists(int currentPage, boolean isDbInView) {
        if (networkUtil.isNetworkConnected()) {
            if (currentPage > 1) {
                if ( !isDbInView ) {
                    //App restart and Db has data. Get Db data
                    return getArtistsFromDb()
                            .map(t -> artistEntityDataMapper.transform(t));

                } else {
                    //loadMore action. Get only the next page
                    return getArtistsFromApi(currentPage)
                            .map(t -> artistEntityDataMapper.transform(t));
                }
            } else {
                //First load or swipe refresh
                return deleteArtistsFromDb()
                        .andThen(getArtistsFromApi(currentPage))
                        .map(t -> artistEntityDataMapper.transform(t));
            }
        } else {    //Work with offline data
            return getArtistsFromDb()
                    .map(t -> artistEntityDataMapper.transform(t));
        }

    }

    @Override
    public Observable<Artist> getArtist(String id) {
        return artistDataStore.findById(id).map(t -> artistEntityDataMapper.transform(t));
    }

    private Observable<List<ArtistEntity>> getArtistsFromDb() {
        return artistDataStore.findAll();
    }

    private Observable<List<ArtistEntity>> getArtistsFromApi(int currentPage) {
        return syncApiArtist.syncArtists(currentPage);
    }

    private Completable deleteArtistsFromDb() {
        return artistDataStore.deleteAllArtists();
    }
}
