package com.paranerd.mediaplayer.Activities;

import com.paranerd.mediaplayer.PlayerAdapter;
import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.R.id;
import com.paranerd.mediaplayer.R.layout;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class PlayerActivity extends FragmentActivity {

	private static final int NUM_ITEMS = 2;
	private PlayerAdapter mAdapter;
    private ViewPager mPager;
    
	private final static String PREFS_NAME = "PlayerSettings";
	private final static String PREFS_LASTTRACK = "LastTrack";
	private final static String PREFS_LASTALBUM = "LastAlbum";
	private final static String PREFS_CURRTRACK = "CurrentTrack";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putString(PREFS_CURRTRACK, "Hello").commit();
        try {
        	setContentView(R.layout.playeract);
        	mAdapter = new PlayerAdapter(getSupportFragmentManager());
            mPager = (ViewPager)findViewById(R.id.playerpager);
            mPager.setAdapter(mAdapter);
            mPager.setCurrentItem(1); // Das zweite Fragment wird beim Start angezeigt
        } catch (Exception e) {
		Log.e("ViewPager", e.toString());
		}
    }
    
	public void savePreferences() {
		getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putString(PREFS_CURRTRACK, "Hello").commit();
	}
}
