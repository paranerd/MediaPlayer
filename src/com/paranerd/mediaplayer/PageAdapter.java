package com.paranerd.mediaplayer;

import com.paranerd.mediaplayer.Fragments.AlbumFragment;
import com.paranerd.mediaplayer.Fragments.ArtistFragment;
import com.paranerd.mediaplayer.Fragments.FileManagerFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

	ActionBar mActionBar;
	private final int NUM_PAGES = 5;
	private final String[] titles = { "Albums", "Artists", "FileManager", "Page2",
			"Page1" };

	public PageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		if (pos == 0) {
			AlbumFragment alf = new AlbumFragment();
			return alf;
		} else if (pos == 1) {
			ArtistFragment arf = new ArtistFragment();
			return arf;
		} else {
			FileManagerFragment ff = new FileManagerFragment();
			return ff;
		}
	}
	
	@SuppressLint("NewApi")
	public void setActionBar( ActionBar bar ) {
    	mActionBar = bar;
    	}

	@Override
	public int getCount() {
		return NUM_PAGES;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

}