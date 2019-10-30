package com.manuelrojas.geomusic.presentation;

import android.app.Application;

import com.manuelrojas.geomusic.BuildConfig;
import com.manuelrojas.geomusic.presentation.di.component.DaggerApplicationComponent;
import com.manuelrojas.geomusic.presentation.di.module.RoomModule;
import com.manuelrojas.geomusic.presentation.di.module.ApplicationModule;
import com.manuelrojas.geomusic.presentation.di.component.ApplicationComponent;

public class MainApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildApplicationComponent();
//        Stetho.initializeWithDefaults(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private void buildApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this);
        }
    }

}
