package com.paranerd.mediaplayer.Fragments;

import java.util.ArrayList;

import com.paranerd.mediaplayer.AlbumLoader;
import com.paranerd.mediaplayer.Album;
import com.paranerd.mediaplayer.AlbumAdapter;
import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.Activities.AlbumTracks;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class AlbumFragment extends ListFragment implements LoaderManager.LoaderCallbacks<ArrayList<Album>>  {

	private View v;
	private Cursor cursor;
	private ArrayList<Album> albumList;
	private AlbumAdapter mAdapter;
	private ArrayList<Album> albums;
	
	private Uri[] mMediaSource = {null, MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI};

	public AlbumFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new View(getActivity());
		getLoaderManager().initLoader(1, null, this);
		mAdapter = new AlbumAdapter(getActivity(), android.R.layout.simple_list_item_1);
		setListAdapter(mAdapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.page_test, container, false);
		return v;
	}
	
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Toast.makeText(getActivity(), Album.getCoverPathNew(position), Toast.LENGTH_SHORT).show();
    	Intent albumtracks = new Intent(getActivity(), AlbumTracks.class);;
    	albumtracks.putExtra("Album",Album.getAlbumNameNew(position));
    	startActivity(albumtracks);
    }

	@Override
	public Loader<ArrayList<Album>> onCreateLoader(int arg0, Bundle arg1) {
		return new AlbumLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Album>> arg0, ArrayList<Album> arg1) {
		mAdapter.setData(arg1);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Album>> arg0) {
		mAdapter.setData(null);
	}
}
