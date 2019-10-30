package com.manuelrojas.geomusic.data.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPrefsUtil {

    private SharedPreferences mSharedPreferences;


    @Inject
    public SharedPrefsUtil(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

//    @Inject
    public SharedPrefsUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences("my-prefs", Context.MODE_PRIVATE);
    }

    public void put(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String get(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void put(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public int get(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

}
