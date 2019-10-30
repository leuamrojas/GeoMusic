package com.manuelrojas.geomusic.presentation.mapper;

import com.manuelrojas.geomusic.domain.Track;
import com.manuelrojas.geomusic.presentation.model.TrackModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

public class TrackModelDataMapper {

    @Inject
    public TrackModelDataMapper() {}

    /**
     * Transform a {@link Track} into an {@link TrackModel}.
     *
     * @param track Object to be transformed.
     * @return {@link TrackModel}.
     */
    public TrackModel transform(Track track) {
        if (track == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final TrackModel trackModel = new TrackModel();
        trackModel.setId(track.getId());
        trackModel.setMbid(track.getMbid());
        trackModel.setName(track.getName());
        trackModel.setDuration(track.getDuration());
        trackModel.setListeners(track.getListeners());
        trackModel.setRank(track.getRank());
        trackModel.setArtistName(track.getArtistName());
        trackModel.setUrl(track.getUrl());
        trackModel.setImageSmall(track.getImageSmall());
        trackModel.setImageMedium(track.getImageMedium());
        trackModel.setImageLarge(track.getImageLarge());
        trackModel.setImageExtraLarge(track.getImageExtraLarge());

        return trackModel;
    }

    /**
     * Transform a Collection of {@link Track} into a Collection of {@link TrackModel}.
     *
     * @param trackCollection Objects to be transformed.
     * @return List of {@link TrackModel}.
     */
    public Collection<TrackModel> transform(Collection<Track> trackCollection) {
        Collection<TrackModel> trackModelCollection;

        if (trackCollection != null && !trackCollection.isEmpty()) {
            trackModelCollection = new ArrayList<>();
            for (Track track : trackCollection) {
                trackModelCollection.add(transform(track));
            }
        } else {
            trackModelCollection = Collections.emptyList();
        }

        return trackModelCollection;
    }
}
