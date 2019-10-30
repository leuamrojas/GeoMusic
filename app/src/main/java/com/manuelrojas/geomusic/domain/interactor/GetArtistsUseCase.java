package com.manuelrojas.geomusic.domain.interactor;

import com.manuelrojas.geomusic.domain.Artist;
import com.manuelrojas.geomusic.domain.ArtistRepository;
import com.manuelrojas.geomusic.domain.executor.PostExecutionThread;
import com.manuelrojas.geomusic.domain.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetArtistsUseCase extends UseCase<List<Artist>, GetArtistsUseCase.Params> {

    private final ArtistRepository repository;

    @Inject
    public GetArtistsUseCase(ArtistRepository repository, ThreadExecutor threadExecutor,
                             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Observable<List<Artist>> buildUseCaseObservable(Params params) {
        return repository.getArtists(params.page, params.isDbInView);
    }

    public static final class Params {

        private final int page;
        private final boolean isDbInView;

        private Params(int page, boolean isDbInView) {
            this.page = page;
            this.isDbInView = isDbInView; //checks whether it's firstLoad or loadMore action
        }

        public static Params buildParams(int page, boolean isDbInView) {
            return new Params(page, isDbInView);
        }
    }
}
