package com.manuelrojas.geomusic.presentation.view;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.presentation.view.fragment.ArtistListFragment;
import com.manuelrojas.geomusic.presentation.view.fragment.TrackListFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES =
            new int[] { R.string.tab_text_1, R.string.tab_text_2 };
    private final Context mContext;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrackListFragment();
            case 1:
                return new ArtistListFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2; // Show 2 total pages.
    }
}
