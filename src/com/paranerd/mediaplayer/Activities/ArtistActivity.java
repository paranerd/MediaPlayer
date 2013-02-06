package com.paranerd.mediaplayer.Activities;

import com.paranerd.mediaplayer.ArtistPageAdapter;
import com.paranerd.mediaplayer.R;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class ArtistActivity extends FragmentActivity {

	String artist, path;
	private static final int NUM_ITEMS = 2;
	private ArtistPageAdapter mAdapter;
    private ViewPager mPager;
    Cursor cursor;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        	setContentView(R.layout.artistact);
            
        	mAdapter = new ArtistPageAdapter(getSupportFragmentManager());
            mPager = (ViewPager)findViewById(R.id.artistpager);
            mPager.setAdapter(mAdapter);
    	
    	Bundle extras = getIntent().getExtras();
    	artist = extras.getString("Artist");
    	/*Toast.makeText(this, artist, Toast.LENGTH_SHORT).show();
      String[] columns = {
              MediaStore.Audio.Media._ID,
              MediaStore.Audio.Media.ALBUM};

          String where = android.provider.MediaStore.Audio.Media.ARTIST
              + "=?" + ") GROUP BY (" + android.provider.MediaStore.Audio.Media.ALBUM;

          String whereVal[] = { cursor.getString(cursor
              .getColumnIndex(MediaStore.Audio.Albums.ALBUM)) };
          String whereVal[] = {artist};

          String orderBy = android.provider.MediaStore.Audio.Media.TITLE;

          cursor = getContentResolver().query(
              MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
              columns,
              where,
              whereVal,
              AudioColumns.ALBUM);

          String[] displayFields = new String[] { MediaStore.Audio.Media.ALBUM };
          int[] displayViews = new int[] { android.R.id.text1 };
          SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                  android.R.layout.simple_list_item_1, cursor,
                  displayFields, displayViews, 0);
          setListAdapter(adapter);*/
        } 
    catch (Exception e) {
		Log.e("ViewPager", e.toString());
		}
    }
    
    /*public void onListItemClick(ListView l, View v, int position, long id) {
    	if (cursor.moveToPosition(position)) {
    		//int index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
    		//String path = cursor.getString(index);
    		String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
    		
			Toast.makeText(this, album, Toast.LENGTH_SHORT).show();

	    	Intent albumtracks = new Intent(this, AlbumTracks.class);
			albumtracks.putExtra("Album", album);
			startActivity(albumtracks);
    	}
    }*/
}
