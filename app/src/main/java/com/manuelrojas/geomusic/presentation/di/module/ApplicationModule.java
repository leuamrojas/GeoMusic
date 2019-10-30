package com.manuelrojas.geomusic.presentation.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.manuelrojas.geomusic.data.repository.ArtistDataRepository;
import com.manuelrojas.geomusic.data.repository.TrackDataRepository;
import com.manuelrojas.geomusic.data.utils.TLSSocketFactory;
import com.manuelrojas.geomusic.domain.ArtistRepository;
import com.manuelrojas.geomusic.domain.TrackRepository;
import com.manuelrojas.geomusic.domain.executor.PostExecutionThread;
import com.manuelrojas.geomusic.domain.executor.ThreadExecutor;
import com.manuelrojas.geomusic.presentation.MainApplication;
import com.manuelrojas.geomusic.presentation.UIThread;
import com.manuelrojas.geomusic.data.executor.JobExecutor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class ApplicationModule {
    MainApplication mMainApplication;
    private static final String DAGGER_PREFS = "dagger-prefs";

    public ApplicationModule(MainApplication mainApplication) {
        mMainApplication = mainApplication;
    }

    @Provides
    @Singleton
    Context provideApplication() {
        return mMainApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs() {
        return mMainApplication.getSharedPreferences(DAGGER_PREFS, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Context context) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//                .registerTypeAdapter(ArtistApi.class, new SimpleDeserializer<ArtistApi>("artist"))
//                .registerTypeAdapter(RankApi.class, new SimpleDeserializer<RankApi>("@rank"))
//                .registerTypeAdapter(ImageApi.class, new ListDeserializer<ImageApi>("image"))
//                .registerTypeAdapter(StreamableApi.class, new SimpleDeserializer<StreamableApi>("streamable"))
//                .registerTypeAdapter(TrackArtistApi.class, new SimpleDeserializer<TrackArtistApi>("artist"))
//                .registerTypeAdapter(TrackApi.class, new ListDeserializer<TrackApi>("track"))
//                .registerTypeAdapter(TracksApi.class, new SimpleDeserializer<StreamableApi>("tracks"))
//                .registerTypeAdapter(ArtistApi.class, new ListDeserializer<ArtistApi>("artist"))
//                .registerTypeAdapter(TopArtistsApi.class, new SimpleDeserializer<TopArtistsApi>("topartists"));
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //Change SSL(Default) to TLS for devices running android API < 20
        TLSSocketFactory tlsSocketFactory = null;
        try {
            tlsSocketFactory = new TLSSocketFactory();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        client.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager());
        client.cache(cache);
        return  client.build();
    }

//    @Provides
//    @Singleton
//    Retrofit provideReftrofit(Gson gson, OkHttpClient okHttpClient) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .baseUrl(Constants.BASE_URL)
//                .client(okHttpClient)
//                .build();
//        return retrofit;
//    }

    @Provides
    @Singleton
    Picasso providePicasso(Context context, OkHttpClient okHttpClient) {
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        return picasso;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    TrackRepository provideTrackRepository(TrackDataRepository trackDataRepository) {
        return trackDataRepository;
    }

    @Provides
    @Singleton
    ArtistRepository provideArtistRepository(ArtistDataRepository artistDataRepository) {
        return artistDataRepository;
    }

}
