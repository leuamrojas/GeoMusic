package com.manuelrojas.geomusic.data.entity.mapper;

import com.manuelrojas.geomusic.data.entity.ArtistEntity;
import com.manuelrojas.geomusic.data.entity.TrackEntity;
import com.manuelrojas.geomusic.data.entity.api.ArtistApi;
import com.manuelrojas.geomusic.data.entity.api.TrackApi;

import javax.inject.Inject;

public class EntityApiMapper {

    @Inject
    public EntityApiMapper() {
    }

    public TrackEntity transformTrackEntity(TrackApi trackApi) {
        TrackEntity trackEntity = null;
        if (trackApi != null) {
            trackEntity = new TrackEntity();
            trackEntity.setArtistId(trackApi.getTrackArtistApi().getMbid());
            trackEntity.setArtistName(trackApi.getTrackArtistApi().getName());
            trackEntity.setArtistUrl(trackApi.getTrackArtistApi().getUrl());
            trackEntity.setName(trackApi.getName());
            trackEntity.setMbid(trackApi.getMbid());
            trackEntity.setDuration(trackApi.getDuration());
            trackEntity.setListeners(trackApi.getListeners());
            trackEntity.setUrl(trackApi.getUrl());
            trackEntity.setRank(Integer.parseInt(trackApi.getAttr().getRank()));
            trackEntity.setImageSmall(trackApi.getImage().get(0).getText());
            trackEntity.setImageMedium(trackApi.getImage().get(1).getText());
            trackEntity.setImageLarge(trackApi.getImage().get(2).getText());
            trackEntity.setImageExtraLarge(trackApi.getImage().get(3).getText());
        }
        return trackEntity;
    }

    public ArtistEntity transformArtistEntity(ArtistApi artistApi) {
        ArtistEntity artistEntity = null;
        if (artistApi != null) {
            artistEntity = new ArtistEntity();
            artistEntity.setMbid(artistApi.getMbid());
            artistEntity.setName(artistApi.getName());
            artistEntity.setUrl(artistApi.getUrl());
            artistEntity.setListeners(artistApi.getListeners());
            artistEntity.setImageSmall(artistApi.getImage().get(0).getText());
            artistEntity.setImageMedium(artistApi.getImage().get(1).getText());
            artistEntity.setImageLarge(artistApi.getImage().get(2).getText());
            artistEntity.setImageExtraLarge(artistApi.getImage().get(3).getText());
        }
        return artistEntity;
    }

}
