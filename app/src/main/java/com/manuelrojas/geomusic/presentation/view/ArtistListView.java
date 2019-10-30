package com.manuelrojas.geomusic.presentation.view;

import com.manuelrojas.geomusic.presentation.model.ArtistModel;

import java.util.Collection;

public interface ArtistListView extends LoadDataView {

        void renderArtistList(Collection<ArtistModel> artistModelCollection);

        void showRetry();

        void hideRetry();
}
