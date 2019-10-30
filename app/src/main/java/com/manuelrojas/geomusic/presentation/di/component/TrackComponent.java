package com.manuelrojas.geomusic.presentation.di.component;

import com.manuelrojas.geomusic.presentation.view.fragment.TrackDetailsFragment;
import com.manuelrojas.geomusic.presentation.di.module.ActivityModule;
import com.manuelrojas.geomusic.presentation.di.scope.PerActivity;
import com.manuelrojas.geomusic.presentation.view.fragment.ArtistDetailsFragment;
import com.manuelrojas.geomusic.presentation.view.fragment.ArtistListFragment;
import com.manuelrojas.geomusic.presentation.view.fragment.TrackListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface TrackComponent {

    void inject(TrackListFragment fragment);

    void inject(TrackDetailsFragment fragment);

    void inject(ArtistListFragment fragment);

    void inject(ArtistDetailsFragment fragment);

}
