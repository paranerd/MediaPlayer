package com.paranerd.mediaplayer.Fragments;

import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.R.layout;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CurrentAlbumFragment extends ListFragment {

	private Cursor cursor;
	private SimpleCursorAdapter mCursorAdapter;
	String album;
	View v;
	private Uri[] mMediaSource = {null, MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI};
	
	public CurrentAlbumFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new View(getActivity());
		
		/*String[] columns = { android.provider.MediaStore.Audio.Albums._ID,
		        android.provider.MediaStore.Audio.Albums.ALBUM};
			    cursor = getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
			            columns, null, null, null);
				mCursorAdapter = new SimpleCursorAdapter(getActivity(),
						android.R.layout.simple_list_item_1, null,
						new String[] { MediaStore.Audio.Albums.ALBUM },
						new int[] { android.R.id.text1 }, 0);
				setListAdapter(mCursorAdapter);*/
				//getLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.currentalbum, container, false);
		
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras!=null) {
    	album = extras.getString("AlbumPath");
		}
    	if(album!=null) {
      String[] columns = { MediaStore.Audio.Media.DATA,
              MediaStore.Audio.Media._ID,
              MediaStore.Audio.Media.TITLE };

          String where = android.provider.MediaStore.Audio.Media.ALBUM
              + "=?";

          /*String whereVal[] = { cursor.getString(cursor
              .getColumnIndex(MediaStore.Audio.Albums.ALBUM)) };*/
          String whereVal[] = {album};

          cursor = getActivity().getContentResolver().query(
              MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
              columns,
              where,
              whereVal,
              AudioColumns.TRACK + ", " + MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

          String[] displayFields = new String[] { MediaStore.Audio.Media.TITLE };
          int[] displayViews = new int[] { android.R.id.text1 };
          setListAdapter(new SimpleCursorAdapter(getActivity(),
              android.R.layout.simple_list_item_1, cursor,
              displayFields, displayViews, 0));
    	}
		return v;
	}
	
    public void onListItemClick(ListView l, View v, int position, long id) {
    	if (cursor.moveToPosition(position)) {
    		int index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
    		String path = cursor.getString(index);
    		PlayerFragment.playPauseResumeAudio(path);
    		
	    	/*Intent player = new Intent(this, PlayerActivity.class);
			player.putExtra("TrackPath", path);
			player.putExtra("AlbumPath", album);
			startActivity(player);*/
    	}
    }
/*
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Loader<Cursor> loader = new CursorLoader(getActivity(), mMediaSource[2],
				null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		mCursorAdapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mCursorAdapter.swapCursor(null);

	}*/
}
