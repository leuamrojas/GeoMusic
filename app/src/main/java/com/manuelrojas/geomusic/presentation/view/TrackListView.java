package com.manuelrojas.geomusic.presentation.view;

import com.manuelrojas.geomusic.presentation.model.TrackModel;

import java.util.Collection;

public interface TrackListView extends LoadDataView{

    void renderTrackList(Collection<TrackModel> trackModelCollection);

    void showRetry();

    void hideRetry();

}
