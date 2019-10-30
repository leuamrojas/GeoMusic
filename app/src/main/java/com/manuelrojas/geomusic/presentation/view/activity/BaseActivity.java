package com.manuelrojas.geomusic.presentation.view.activity;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.manuelrojas.geomusic.data.utils.ActivityUtil;
import com.manuelrojas.geomusic.presentation.MainApplication;
import com.manuelrojas.geomusic.presentation.di.component.ApplicationComponent;
import com.manuelrojas.geomusic.presentation.di.module.ActivityModule;
import com.manuelrojas.geomusic.presentation.navigation.Navigator;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     * @param fragmentTag Tag of the fragment  to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag) {
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),
                fragment, containerViewId, fragmentTag);
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((MainApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return ActivityModule
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
