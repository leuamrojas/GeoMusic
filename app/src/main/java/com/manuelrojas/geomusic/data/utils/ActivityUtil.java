package com.manuelrojas.geomusic.data.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class ActivityUtil {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, String fragmentTag) {
//        Preconditions.checkNotNull(fragmentManager);
//        Preconditions.checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, fragmentTag);
        transaction.commit();
    }

}
