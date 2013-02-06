package com.paranerd.mediaplayer.Activities;

import com.paranerd.mediaplayer.PageAdapter;
import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.TabListener;
import com.paranerd.mediaplayer.Fragments.PlayerFragment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity {
    /** Called when the activity is first created. */

	private static final int NUM_ITEMS = 5;
	private PageAdapter mAdapter;
    private ViewPager mPager;
    public ActionBar mActionBar;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        	setContentView(R.layout.main);
        	mActionBar = getActionBar();
            
        	mAdapter = new PageAdapter(getSupportFragmentManager());
        	mAdapter.setActionBar(mActionBar);
            mPager = (ViewPager)findViewById(R.id.pager);
            mPager.setAdapter(mAdapter);
            
            mPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// Nothing
					}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// Nothing
					}

				@Override
				public void onPageSelected(int arg0) {
					//Log.d("ViewPager", "onPageSelected: " + arg0);
					mActionBar.getTabAt(arg0).select();
					}
            	});
        	
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			// Es soll keine Titelleiste angezeigt werden
			mActionBar.setDisplayShowTitleEnabled(false);
        	mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setHomeButtonEnabled(false);
            mActionBar.setDisplayUseLogoEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            
			Tab tab = mActionBar
					.newTab()
					.setText("Alben")
					.setTabListener(
							new TabListener<android.app.Fragment>(this, 0 + "", mPager));
			mActionBar.addTab(tab);
			tab = mActionBar
					.newTab()
					.setText("Artists")
					.setTabListener(
							new TabListener<android.app.Fragment>(this, 1 + "", mPager));
			mActionBar.addTab(tab);
			tab = mActionBar
					.newTab()
					.setText("FileManager")
					.setTabListener(
							new TabListener<android.app.Fragment>(this, 2 + "", mPager));
			mActionBar.addTab(tab);
			tab = mActionBar
					.newTab()
					.setText("AnotherOne")
					.setTabListener(
							new TabListener<android.app.Fragment>(this, 3 + "", mPager));
			mActionBar.addTab(tab);

			tab = mActionBar
					.newTab()
					.setText("AndAnotherOne")
					.setTabListener(
							new TabListener<android.app.Fragment>(this, 4 + "", mPager));
			mActionBar.addTab(tab);

			//mActionBar.getTabAt(2).select(); // wählt beim App-Start direkt den dritten Tab aus
			} 
        catch (Exception e) {
			Log.e("ViewPager", e.toString());
			}
    
    RelativeLayout layout =(RelativeLayout)findViewById(R.id.bottomplayer);
	layout.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivity(new Intent("com.paranerd.mediaplayer.PLAYERACTIVITY"));
		}
		
	});
	
	ImageButton play = (ImageButton) findViewById(R.id.play);
	play.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			PlayerFragment.playPauseResumeAudio(null);
		}
		
	});
	
	ImageButton next = (ImageButton) findViewById(R.id.nexttrack);
	next.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Next", Toast.LENGTH_SHORT).show();	
		}
		
	});
	
	ImageButton previous = (ImageButton) findViewById(R.id.previoustrack);
	previous.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "Previous", Toast.LENGTH_SHORT).show();	
		}
		
	});
}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	  if ((keyCode == KeyEvent.KEYCODE_BACK)) {
		  //finish();
	  }
	  return super.onKeyDown(keyCode, event);
  }
	}