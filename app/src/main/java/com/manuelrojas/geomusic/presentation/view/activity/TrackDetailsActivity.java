package com.manuelrojas.geomusic.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.presentation.di.HasComponent;
import com.manuelrojas.geomusic.presentation.di.component.DaggerTrackComponent;
import com.manuelrojas.geomusic.presentation.di.component.TrackComponent;
import com.manuelrojas.geomusic.presentation.model.TrackModel;
import com.manuelrojas.geomusic.presentation.view.fragment.TrackDetailsFragment;

public class TrackDetailsActivity extends BaseActivity implements HasComponent<TrackComponent> {

    private static final String INTENT_EXTRA_PARAM_TRACK = "org.android10.INTENT_PARAM_TRACK";
    private static final String INSTANCE_STATE_PARAM_TRACK = "org.android10.STATE_PARAM_TRACK";

    public static Intent getCallingIntent(Context context, TrackModel track) {
        Intent callingIntent = new Intent(context, TrackDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_TRACK, track);
        return callingIntent;
    }

    private TrackModel trackModel;
    private TrackComponent trackComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.trackModel = getIntent().getParcelableExtra(INTENT_EXTRA_PARAM_TRACK);
            addFragment(R.id.fragmentContainerTrackDetails, TrackDetailsFragment.forUser(trackModel),
                    "TrackDetailsFragment");
        } else {
            this.trackModel = savedInstanceState.getParcelable(INSTANCE_STATE_PARAM_TRACK);
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
