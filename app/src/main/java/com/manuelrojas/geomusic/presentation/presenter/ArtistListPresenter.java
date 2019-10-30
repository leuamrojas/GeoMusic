package com.manuelrojas.geomusic.presentation.presenter;

import com.manuelrojas.geomusic.domain.Artist;
import com.manuelrojas.geomusic.domain.exception.DefaultErrorBundle;
import com.manuelrojas.geomusic.domain.exception.ErrorBundle;
import com.manuelrojas.geomusic.domain.interactor.DefaultObserver;
import com.manuelrojas.geomusic.domain.interactor.GetArtistsUseCase;
import com.manuelrojas.geomusic.presentation.exception.ErrorMessageFactory;
import com.manuelrojas.geomusic.presentation.model.ArtistModel;
import com.manuelrojas.geomusic.presentation.view.ArtistListView;
import com.manuelrojas.geomusic.presentation.mapper.ArtistModelDataMapper;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class ArtistListPresenter implements Presenter<ArtistListView> {

    private static final String TAG = "ArtistListPresenter";
    private ArtistListView view;
    private final GetArtistsUseCase getArtistsUseCase;
    private final ArtistModelDataMapper artistModelDataMapper;
    private int page;

    @Inject
    public ArtistListPresenter(GetArtistsUseCase getArtistsUseCase,
                              ArtistModelDataMapper artistModelDataMapper) {
        this.getArtistsUseCase = getArtistsUseCase;
        this.artistModelDataMapper = artistModelDataMapper;
    }

    @Override
    public void attachView(ArtistListView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        getArtistsUseCase.dispose();
    }

    public void loadArtists(int page, boolean isDbInView) {
        this.page = page;
        if (page == 1) {
            hideViewRetry();
            showViewLoading();
        }
        getArtistsUseCase.execute(new GetArtistsObserver(), GetArtistsUseCase.Params.buildParams(page, isDbInView));
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

    private void showArtistsCollectionInView(Collection<Artist> artistCollection) {
        final Collection<ArtistModel> artistModelCollection =
                artistModelDataMapper.transform(artistCollection);
        view.renderArtistList(artistModelCollection);
    }

    private final class GetArtistsObserver extends DefaultObserver<List<Artist>> {

        @Override
        public void onNext(List<Artist> artists) {
            showArtistsCollectionInView(artists);
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
