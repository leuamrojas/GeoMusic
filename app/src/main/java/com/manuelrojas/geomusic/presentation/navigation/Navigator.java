package com.manuelrojas.geomusic.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.manuelrojas.geomusic.presentation.model.ArtistModel;
import com.manuelrojas.geomusic.presentation.model.TrackModel;
import com.manuelrojas.geomusic.presentation.view.activity.TrackDetailsActivity;
import com.manuelrojas.geomusic.presentation.view.activity.TrackListActivity;
import com.manuelrojas.geomusic.presentation.view.activity.ArtistDetailsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {}

    public void navigateToTrackList(Context context) {
        if (context != null) {
            Intent intentToLaunch = TrackListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToTrackDetails(Context context, TrackModel trackModel) {
        if (context != null) {
            Intent intentToLaunch = TrackDetailsActivity.getCallingIntent(context, trackModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToArtistDetails(Context context, ArtistModel artistModel) {
        if (context != null) {
            Intent intentToLaunch = ArtistDetailsActivity.getCallingIntent(context, artistModel);
            context.startActivity(intentToLaunch);
        }
    }

}