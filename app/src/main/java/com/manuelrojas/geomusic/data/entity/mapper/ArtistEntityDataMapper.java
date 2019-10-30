package com.manuelrojas.geomusic.data.entity.mapper;

import com.manuelrojas.geomusic.data.entity.ArtistEntity;
import com.manuelrojas.geomusic.domain.Artist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class ArtistEntityDataMapper {

    @Inject
    ArtistEntityDataMapper() {}

    /**
     * Transform a {@link ArtistEntity} into an {@link Artist}.
     *
     * @param artistEntity Object to be transformed.
     * @return {@link Artist} if valid {@link ArtistEntity} otherwise null.
     */
    public Artist transform(ArtistEntity artistEntity) {
        Artist artist = null;
        if (artistEntity != null){
            artist = new Artist();
            artist.setMbid(artistEntity.getMbid());
            artist.setName(artistEntity.getName());
            artist.setUrl(artistEntity.getUrl());
            artist.setListeners(artistEntity.getListeners());
            artist.setImageSmall(artistEntity.getImageSmall());
            artist.setImageMedium(artistEntity.getImageMedium());
            artist.setImageLarge(artistEntity.getImageLarge());
            artist.setImageExtraLarge(artistEntity.getImageExtraLarge());
        }
        return artist;
    }

    /**
     * Transform a List of {@link ArtistEntity} into a Collection of {@link Artist}.
     *
     * @param artistEntityCollection Object Collection to be transformed.
     * @return {@link Artist} if valid {@link ArtistEntity} otherwise null.
     */
    public List<Artist> transform(Collection<ArtistEntity> artistEntityCollection) {
        final List<Artist> artistList = new ArrayList<>(20);
        for (ArtistEntity artistEntity : artistEntityCollection) {
            Artist artist = transform(artistEntity);
            if (artist != null) {
                artistList.add(artist);
            }
        }
        return artistList;
    }

}
