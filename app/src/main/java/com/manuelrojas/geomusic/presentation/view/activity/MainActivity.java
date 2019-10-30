package com.manuelrojas.geomusic.presentation.view.activity;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        // go straight to main if a token is stored
//        if (Util.getToken() != null) {
//            activityIntent = new Intent(this, MainActivity.class);
//        } else {
//            activityIntent = new Intent(this, LoginActivity.class);
//        }
//
//        startActivity(activityIntent);
//        finish();

//        String prefsToken = sharedPrefsUtil.get(PREFS_TOKEN, "");
//        if ( prefsToken.isEmpty() ) {
//            navigator.navigateToLogin(this);
//        } else {
//            navigator.navigateToRepoList(this, prefsToken);
//        }
//        finish();

        navigator.navigateToTrackList(this);
        finish();
    }

}
