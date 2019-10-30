package com.manuelrojas.geomusic;


import android.content.Context;

import com.manuelrojas.geomusic.domain.interactor.GetTracksUseCase;
import com.manuelrojas.geomusic.presentation.mapper.TrackModelDataMapper;
import com.manuelrojas.geomusic.presentation.presenter.TrackListPresenter;
import com.manuelrojas.geomusic.presentation.view.TrackListView;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TrackListPresenterTest2 {

    TrackListPresenter trackListPresenter;

    @Mock private Context context;
    @Mock private TrackListView trackListView;
    @Mock private GetTracksUseCase getTracksUseCase;
    @Mock private TrackModelDataMapper trackModelDataMapper;

    @Before
    public void setUp() {
        trackListPresenter = new TrackListPresenter(getTracksUseCase, trackModelDataMapper);
        trackListPresenter.attachView(trackListView);
    }

    @Test
    public void testTrackListPresenterInitialize() {
        given(trackListView.context()).willReturn(context);

        trackListPresenter.loadTracks(1, false);

        verify(trackListView).hideRetry();
        verify(trackListView).showLoading();
        verify(getTracksUseCase).execute(any(DisposableObserver.class), any(GetTracksUseCase.Params.class));
    }


}
