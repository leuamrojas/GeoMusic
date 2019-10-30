package com.manuelrojas.geomusic.presentation.presenter;

import android.content.Context;

import com.manuelrojas.geomusic.domain.interactor.GetArtistsUseCase;
import com.manuelrojas.geomusic.presentation.mapper.ArtistModelDataMapper;
import com.manuelrojas.geomusic.presentation.view.ArtistListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ArtistListPresenterTest {

    ArtistListPresenter artistListPresenter;

    @Mock
    private Context context;
    @Mock private ArtistListView artistListView;
    @Mock private GetArtistsUseCase getArtistsUseCase;
    @Mock private ArtistModelDataMapper artistModelDataMapper;

    @Before
    public void setUp() {
        artistListPresenter = new ArtistListPresenter(getArtistsUseCase, artistModelDataMapper);
        artistListPresenter.attachView(artistListView);
    }

    @Test
    public void testArtistListPresenterInitialize() {
        given(artistListView.context()).willReturn(context);

        artistListPresenter.loadArtists(1, false);

        verify(artistListView).hideRetry();
        verify(artistListView).showLoading();
        verify(getArtistsUseCase).execute(any(DisposableObserver.class), any(GetArtistsUseCase.Params.class));
    }
}