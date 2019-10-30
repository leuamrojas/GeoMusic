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
import com.manuelrojas.geomusic.presentation.di.component.TrackComponent;
import com.manuelrojas.geomusic.presentation.model.ArtistModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistDetailsFragment extends BaseFragment {

    @BindView(R.id.iv_artist_details)
    ImageView ivArtistDetails;

    @BindView(R.id.tv_artist_details_name)
    TextView tvArtistDetailsName;

    @BindView(R.id.tv_artist_details_url)
    TextView tvArtistDetailsUrl;

    @BindView(R.id.tv_artist_details_listeners)
    TextView tvArtistDetailsListeners;

    private static final String PARAM_ARTIST = "param_artist";

    @Inject
    Picasso picasso;

    public static ArtistDetailsFragment forUser(ArtistModel artistModel) {
        final ArtistDetailsFragment artistDetailsFragment = new ArtistDetailsFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable(PARAM_ARTIST, artistModel);
        artistDetailsFragment.setArguments(arguments);
        return artistDetailsFragment;
    }

    public ArtistDetailsFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_artist_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            ArtistModel artistModel = currentArtist();
            renderArtist(artistModel);
        }
    }

    private void renderArtist(ArtistModel artistModel) {
        if (artistModel != null) {
            picasso.load(artistModel.getImageLarge())
                    .fit()
                    .centerCrop()
//                    .resize(100,100)
//                    .transform(new RoundedCornersTransformation(10, 10))
//                    .placeholder(R.drawable.ic_launcher_foreground)
//                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivArtistDetails);
            tvArtistDetailsName.setText(artistModel.getName());
            tvArtistDetailsUrl.setText(artistModel.getUrl());
            tvArtistDetailsListeners.setText(artistModel.getListeners());
        }
    }

    /**
     * Get current artist from fragments arguments.
     */
    private ArtistModel currentArtist() {
        final Bundle arguments = getArguments();
        return arguments.getParcelable(PARAM_ARTIST);
    }

}
