package com.paranerd.mediaplayer;

import com.paranerd.mediaplayer.Fragments.ArtistAlbumFragment;
import com.paranerd.mediaplayer.Fragments.ArtistSongsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ArtistPageAdapter extends FragmentPagerAdapter {

	private final int NUM_PAGES = 2;

	public ArtistPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		if (pos == 0) {
			ArtistAlbumFragment aaf = new ArtistAlbumFragment();
			return aaf;
		} else {
			ArtistSongsFragment asf = new ArtistSongsFragment();
			return asf;
		}
	}

	@Override
	public int getCount() {
		return NUM_PAGES;
	}
}