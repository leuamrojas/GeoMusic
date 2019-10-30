package com.manuelrojas.geomusic.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.presentation.di.HasComponent;
import com.manuelrojas.geomusic.presentation.di.component.DaggerTrackComponent;
import com.manuelrojas.geomusic.presentation.di.component.TrackComponent;
import com.manuelrojas.geomusic.presentation.model.ArtistModel;
import com.manuelrojas.geomusic.presentation.view.fragment.ArtistDetailsFragment;

public class ArtistDetailsActivity extends BaseActivity implements HasComponent<TrackComponent> {

    private static final String INTENT_EXTRA_PARAM_ARTIST = "org.android10.INTENT_PARAM_ARTIST";
    private static final String INSTANCE_STATE_PARAM_ARTIST = "org.android10.STATE_PARAM_ARTIST";

    public static Intent getCallingIntent(Context context, ArtistModel artist) {
        Intent callingIntent = new Intent(context, ArtistDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ARTIST, artist);
        return callingIntent;
    }

    private ArtistModel artistModel;
    private TrackComponent trackComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.artistModel = getIntent().getParcelableExtra(INTENT_EXTRA_PARAM_ARTIST);
            addFragment(R.id.fragmentContainerArtistDetails, ArtistDetailsFragment.forUser(artistModel),
                    "ArtistDetailsFragment");
        } else {
            this.artistModel = savedInstanceState.getParcelable(INSTANCE_STATE_PARAM_ARTIST);
        }
    }

    private void initializeInjector() {
        this.trackComponent = DaggerTrackComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public TrackComponent getComponent() {
        return trackComponent;
    }
}
