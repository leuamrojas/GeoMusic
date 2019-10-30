package com.manuelrojas.geomusic.presentation.mapper;

import com.manuelrojas.geomusic.domain.Artist;
import com.manuelrojas.geomusic.presentation.model.ArtistModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

public class ArtistModelDataMapper {

    @Inject
    public ArtistModelDataMapper() {}

    /**
     * Transform a {@link Artist} into an {@link ArtistModel}.
     *
     * @param artist Object to be transformed.
     * @return {@link ArtistModel}.
     */
    public ArtistModel transform(Artist artist) {
        if (artist == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final ArtistModel artistModel = new ArtistModel();
        artistModel.setMbid(artist.getMbid());
        artistModel.setName(artist.getName());
        artistModel.setListeners(artist.getListeners());
        artistModel.setUrl(artist.getUrl());
        artistModel.setImageSmall(artist.getImageSmall());
        artistModel.setImageMedium(artist.getImageMedium());
        artistModel.setImageLarge(artist.getImageLarge());
        artistModel.setImageExtraLarge(artist.getImageExtraLarge());

        return artistModel;
    }

    /**
     * Transform a Collection of {@link Artist} into a Collection of {@link ArtistModel}.
     *
     * @param artistCollection Objects to be transformed.
     * @return List of {@link ArtistModel}.
     */
    public Collection<ArtistModel> transform(Collection<Artist> artistCollection) {
        Collection<ArtistModel> artistModelCollection;

        if (artistCollection != null && !artistCollection.isEmpty()) {
            artistModelCollection = new ArrayList<>();
            for (Artist artist : artistCollection) {
                artistModelCollection.add(transform(artist));
            }
        } else {
            artistModelCollection = Collections.emptyList();
        }

        return artistModelCollection;
    }
}
