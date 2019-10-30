package com.manuelrojas.geomusic.presentation.presenter;

import com.manuelrojas.geomusic.domain.Track;
import com.manuelrojas.geomusic.domain.exception.DefaultErrorBundle;
import com.manuelrojas.geomusic.domain.exception.ErrorBundle;
import com.manuelrojas.geomusic.domain.interactor.DefaultObserver;
import com.manuelrojas.geomusic.domain.interactor.GetTracksUseCase;
import com.manuelrojas.geomusic.presentation.view.TrackListView;
import com.manuelrojas.geomusic.presentation.exception.ErrorMessageFactory;
import com.manuelrojas.geomusic.presentation.mapper.TrackModelDataMapper;
import com.manuelrojas.geomusic.presentation.model.TrackModel;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class TrackListPresenter implements Presenter<TrackListView> {

    private static final String TAG = "TrackListPresenter";
    private TrackListView view;
    private final GetTracksUseCase getTracksUseCase;
    private final TrackModelDataMapper trackModelDataMapper;
    private int page;

    @Inject
    public TrackListPresenter(GetTracksUseCase getTracksUseCase,
                              TrackModelDataMapper trackModelDataMapper) {
        this.getTracksUseCase = getTracksUseCase;
        this.trackModelDataMapper = trackModelDataMapper;
    }

    @Override
    public void attachView(TrackListView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        getTracksUseCase.dispose();
    }

    public void loadTracks(int page, boolean isDbInView) {
        this.page = page;
        if (page == 1) {
            hideViewRetry();
            showViewLoading();
        }
        getTracksUseCase.execute(new GetTracksObserver(), GetTracksUseCase.Params.buildParams(page, isDbInView));
    }

    private void showViewLoading() {
        view.showLoading();
    }

    private void hideViewLoading() {
        view.hideLoading();
    }

    private void showViewRetry() {
        view.showRetry();
    }

    private void hideViewRetry() {
        view.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(view.context(),
                errorBundle.getException());
        view.showError(errorMessage);
    }

    private void showTracksCollectionInView(Collection<Track> trackCollection) {
        final Collection<TrackModel> trackModelCollection =
                trackModelDataMapper.transform(trackCollection);
        view.renderTrackList(trackModelCollection);
    }

    private final class GetTracksObserver extends DefaultObserver<List<Track>> {

        @Override
        public void onNext(List<Track> tracks) {
            showTracksCollectionInView(tracks);
            if (page==1)
                hideViewLoading();
        }

        @Override
        public void onComplete() {
            if (page==1)
                hideViewLoading();

        }

        @Override
        public void onError(Throwable e) {
            if (page==1) {
                hideViewLoading();
                showViewRetry();
            }
            showErrorMessage(new DefaultErrorBundle((Exception) e));
        }
    }

}
