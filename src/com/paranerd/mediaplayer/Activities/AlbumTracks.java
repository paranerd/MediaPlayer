package com.paranerd.mediaplayer.Activities;

import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.R.layout;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

public class AlbumTracks extends ListActivity {

	String album, path;
	Cursor cursor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.page_test);
    	
    	Bundle extras = getIntent().getExtras();
    	album = extras.getString("Album");
    	//path = extras.getString("AlbumPath");

      String[] columns = { MediaStore.Audio.Media.DATA,
              MediaStore.Audio.Media._ID,
              MediaStore.Audio.Media.TITLE };

          String where = android.provider.MediaStore.Audio.Media.ALBUM
              + "=?";

          String whereVal[] = {album};

          cursor = getContentResolver().query(
              MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
              columns,
              where,
              whereVal,
              AudioColumns.TRACK + ", " + MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

          String[] displayFields = new String[] { MediaStore.Audio.Media.TITLE };
          int[] displayViews = new int[] { android.R.id.text1 };
          setListAdapter(new SimpleCursorAdapter(this,
              android.R.layout.simple_list_item_1, cursor,
              displayFields, displayViews, 0));
    }
    
    public void onListItemClick(ListView l, View v, int position, long id) {
    	if (cursor.moveToPosition(position)) {
    		int index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
    		String path = cursor.getString(index);
    		
	    	Intent player = new Intent(this, PlayerActivity.class);
			player.putExtra("TrackPath", path);
			player.putExtra("AlbumPath", album);
			startActivity(player);
    	}
    }
}
