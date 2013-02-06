package com.paranerd.mediaplayer.Fragments;

import java.util.ArrayList;

import com.paranerd.mediaplayer.Artist;
import com.paranerd.mediaplayer.ArtistAdapter;
import com.paranerd.mediaplayer.ArtistLoader;
import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.Activities.ArtistActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ArtistFragment extends ListFragment implements LoaderManager.LoaderCallbacks<ArrayList<Artist>> {

	private View v;
	ArrayList<Artist> artistList;
	private ArtistAdapter mAdapter;

	public ArtistFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = new View(getActivity());
		getLoaderManager().initLoader(1, null, this);
		mAdapter = new ArtistAdapter(getActivity(), android.R.layout.simple_list_item_1, null);
		setListAdapter(mAdapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.page_test, container, false);
		return v;
	}
	
    public void onListItemClick(ListView l, View v, int position, long id) {    	
    	Intent artistalbums = new Intent(getActivity(), ArtistActivity.class);
		artistalbums.putExtra("Artist", Artist.getArtistName(position));
		startActivity(artistalbums);
    }

	@Override
	public Loader<ArrayList<Artist>> onCreateLoader(int arg0, Bundle arg1) {
		return new ArtistLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Artist>> arg0, ArrayList<Artist> arg1) {
		mAdapter.setData(arg1);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Artist>> arg0) {
		mAdapter.setData(null);
	}
}
