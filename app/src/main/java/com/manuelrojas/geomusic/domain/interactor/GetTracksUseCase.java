package com.manuelrojas.geomusic.domain.interactor;

import com.manuelrojas.geomusic.domain.Track;
import com.manuelrojas.geomusic.domain.TrackRepository;
import com.manuelrojas.geomusic.domain.executor.PostExecutionThread;
import com.manuelrojas.geomusic.domain.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetTracksUseCase extends UseCase<List<Track>, GetTracksUseCase.Params> {

    private final TrackRepository repository;

    @Inject
    public GetTracksUseCase(TrackRepository repository, ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Observable<List<Track>> buildUseCaseObservable(Params params) {
        return repository.getTracks(params.page, params.isDbInView);
    }

    public static final class Params {

        private final int page;
        private final boolean isDbInView;

        private Params(int page, boolean isDbInView) {
            this.page = page;
            this.isDbInView = isDbInView; //checks whether it's a first load or load more action
        }

        public static Params buildParams(int page, boolean isDbInView) {
            return new Params(page, isDbInView);
        }
    }
}
