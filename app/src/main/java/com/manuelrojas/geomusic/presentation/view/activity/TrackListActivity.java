package com.manuelrojas.geomusic.presentation.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.presentation.di.HasComponent;
import com.manuelrojas.geomusic.presentation.di.component.DaggerTrackComponent;
import com.manuelrojas.geomusic.presentation.di.component.TrackComponent;
import com.manuelrojas.geomusic.presentation.model.ArtistModel;
import com.manuelrojas.geomusic.presentation.model.TrackModel;
import com.manuelrojas.geomusic.presentation.view.MainPagerAdapter;
import com.manuelrojas.geomusic.presentation.view.fragment.ArtistListFragment;
import com.manuelrojas.geomusic.presentation.view.fragment.TrackListFragment;
import com.google.android.material.tabs.TabLayout;

public class TrackListActivity extends BaseActivity implements HasComponent<TrackComponent>,
        TrackListFragment.TrackListListener, ArtistListFragment.ArtistListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, TrackListActivity.class);
    }

    private TrackComponent trackComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);

        this.initializeInjector();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        setViewPager();
    }

    private void initializeInjector() {
        this.trackComponent = DaggerTrackComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        setupSearchView(searchView);

        return true;
    }

    @Override
    public TrackComponent getComponent() {
        return trackComponent;
    }

    @Override
    public void onTrackClicked(TrackModel trackModel) {
        navigator.navigateToTrackDetails(this, trackModel);
    }

    @Override
    public void onArtistClicked(ArtistModel artistModel) {
        navigator.navigateToArtistDetails(this, artistModel);
    }

    private void setupSearchView(SearchView searchView) {

        Fragment page1 = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 0);
        Fragment page2 = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 1);

        TrackListFragment trackListFragment = (TrackListFragment) page1;
        ArtistListFragment artistListFragment = (ArtistListFragment) page2;

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (trackListFragment != null) {
                    trackListFragment.filterAdapterView(query);
                }
                if (artistListFragment != null) {
                    artistListFragment.filterAdapterView(query);
                }
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackListFragment.setSearchViewOpen(true);
                artistListFragment.setSearchViewOpen(true);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                trackListFragment.setSearchViewOpen(false);
                artistListFragment.setSearchViewOpen(false);
                return false;
            }
        });

        setSearchTextColor(searchView);
    }

    private void setSearchTextColor(SearchView searchView) {
        int searchId = getId(searchView, "android:id/search_src_text");
        EditText searchPlate = searchView.findViewById(searchId);
        searchPlate.setTextColor(getResources().getColor(R.color.colorWhite));
//        searchPlate.setBackgroundResource(R.drawable.edit_text_holo_light);
        searchPlate.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        searchId = getId(searchView, "android:id/search_button");
        ImageView mSearchHintIcon = searchView.findViewById(searchId);
        mSearchHintIcon.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);

        searchId = getId(searchView, "android:id/search_close_btn");
        ImageView closeButton = searchView.findViewById(searchId);
        closeButton.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
    }

    private int getId(SearchView searchView, String strId) {
        return searchView.getContext().getResources().getIdentifier(strId, null, null);
    }

    private void setViewPager() {

        MainPagerAdapter tabsPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }



}
