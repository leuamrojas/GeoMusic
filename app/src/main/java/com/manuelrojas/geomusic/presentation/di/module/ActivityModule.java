package com.manuelrojas.geomusic.presentation.di.module;

import android.app.Activity;

import com.manuelrojas.geomusic.presentation.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity providesActivity() {
        return  activity;
    }

}
