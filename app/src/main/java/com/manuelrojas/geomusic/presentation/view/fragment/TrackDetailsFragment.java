package com.manuelrojas.geomusic.presentation.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.data.utils.ConvertUtil;
import com.manuelrojas.geomusic.presentation.di.component.TrackComponent;
import com.manuelrojas.geomusic.presentation.model.TrackModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackDetailsFragment extends BaseFragment {

    @BindView(R.id.iv_track_details)
    ImageView ivTrackDetails;

    @BindView(R.id.tv_track_details_artist)
    TextView tvTrackArtist;

    @BindView(R.id.tv_track_details_name)
    TextView tvTrackName;

    @BindView(R.id.tv_track_details_duration)
    TextView tvTrackDetailsDuration;

    @BindView(R.id.tv_track_details_url)
    TextView tvTrackDetailsUrl;

    @BindView(R.id.tv_track_details_listeners)
    TextView tvTrackDetailsListeners;

    @BindView(R.id.tv_track_details_rank)
    TextView tvTrackDetailsRank;

    private static final String PARAM_TRACK = "param_track";

    @Inject
    Picasso picasso;

    public static TrackDetailsFragment forUser(TrackModel trackModel) {
        final TrackDetailsFragment trackDetailsFragment = new TrackDetailsFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable(PARAM_TRACK, trackModel);
        trackDetailsFragment.setArguments(arguments);
        return trackDetailsFragment;
    }

    public TrackDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(TrackComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_track_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            TrackModel trackModel = currentTrack();
            renderTrack(trackModel);
        }
    }

    private void renderTrack(TrackModel trackModel) {
        if (trackModel != null) {
            picasso.load(trackModel.getImageLarge())
                    .fit()
                    .centerCrop()
//                    .resize(100,100)
//                    .transform(new RoundedCornersTransformation(10, 10))
//                    .placeholder(R.drawable.ic_launcher_foreground)
//                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivTrackDetails);
            tvTrackArtist.setText(trackModel.getArtistName());
            tvTrackName.setText(trackModel.getName());
            tvTrackDetailsDuration.setText( ConvertUtil.convertSecondsToMin(trackModel.getDuration()) );
            tvTrackDetailsUrl.setText(trackModel.getUrl());
            tvTrackDetailsRank.setText(String.valueOf(trackModel.getRank()));
            tvTrackDetailsListeners.setText(trackModel.getListeners());
        }
    }

    /**
     * Get current track from fragments arguments.
     */
    private TrackModel currentTrack() {
        final Bundle arguments = getArguments();
        return arguments.getParcelable(PARAM_TRACK);
    }
}
