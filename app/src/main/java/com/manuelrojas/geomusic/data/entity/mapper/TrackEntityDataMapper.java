package com.manuelrojas.geomusic.data.entity.mapper;

import com.manuelrojas.geomusic.data.entity.TrackEntity;
import com.manuelrojas.geomusic.domain.Track;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class TrackEntityDataMapper {

    @Inject
    TrackEntityDataMapper() {}

    /**
     * Transform a {@link TrackEntity} into an {@link Track}.
     *
     * @param trackEntity Object to be transformed.
     * @return {@link Track} if valid {@link TrackEntity} otherwise null.
     */
    public Track transform(TrackEntity trackEntity) {
        Track track = null;
        if (trackEntity != null){
            track = new Track();
            track.setId(trackEntity.getId());
            track.setMbid(trackEntity.getMbid());
            track.setName(trackEntity.getName());
            track.setDuration(trackEntity.getDuration());
            track.setListeners(trackEntity.getListeners());
            track.setUrl(trackEntity.getUrl());
            track.setRank(trackEntity.getRank());
            track.setArtistName(trackEntity.getArtistName());
            track.setArtistUrl(trackEntity.getArtistUrl());
            track.setImageSmall(trackEntity.getImageSmall());
            track.setImageMedium(trackEntity.getImageMedium());
            track.setImageLarge(trackEntity.getImageLarge());
            track.setImageExtraLarge(trackEntity.getImageExtraLarge());
        }
        return track;
    }

    /**
     * Transform a List of {@link TrackEntity} into a Collection of {@link Track}.
     *
     * @param trackEntityCollection Object Collection to be transformed.
     * @return {@link Track} if valid {@link TrackEntity} otherwise null.
     */
    public List<Track> transform(Collection<TrackEntity> trackEntityCollection) {
        List<Track> trackList = new ArrayList<>(20);
        for (TrackEntity trackEntity : trackEntityCollection) {
            Track track = transform(trackEntity);
            if (track != null) {
                trackList.add(track);
            }
        }
        return trackList;
    }

}
