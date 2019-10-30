package com.manuelrojas.geomusic.presentation.di.component;

import android.content.Context;

import com.manuelrojas.geomusic.data.utils.SharedPrefsUtil;
import com.manuelrojas.geomusic.domain.ArtistRepository;
import com.manuelrojas.geomusic.domain.TrackRepository;
import com.manuelrojas.geomusic.domain.executor.PostExecutionThread;
import com.manuelrojas.geomusic.domain.executor.ThreadExecutor;
import com.manuelrojas.geomusic.presentation.di.module.ApplicationModule;
import com.manuelrojas.geomusic.presentation.di.module.RoomModule;
import com.manuelrojas.geomusic.presentation.view.activity.BaseActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class} )
public interface ApplicationComponent {

    void inject (BaseActivity activity);

    //Exposed to sub-graphs.
    Context getApplication();
    SharedPrefsUtil getSharedPrefsUtil();
    Cache getOkHttpCache();
    Gson getGson();
    OkHttpClient getOkHttpClient();
//    Retrofit getRetrofit();
    Picasso getPicasso();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    TrackRepository trackRepository();
    ArtistRepository artistRepository();

}
