package com.paranerd.mediaplayer;

import com.paranerd.mediaplayer.Fragments.CurrentAlbumFragment;
import com.paranerd.mediaplayer.Fragments.PlayerFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PlayerAdapter extends FragmentPagerAdapter {

	private final int NUM_PAGES = 2;

	public PlayerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		if (pos == 0) {
			CurrentAlbumFragment caf = new CurrentAlbumFragment();
			return caf;
		} else {
			PlayerFragment pf = new PlayerFragment();
			return pf;
		}
	}

	@Override
	public int getCount() {
		return NUM_PAGES;
	}
}