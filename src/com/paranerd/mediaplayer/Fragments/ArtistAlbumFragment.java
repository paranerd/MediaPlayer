package com.paranerd.mediaplayer.Fragments;

import java.util.ArrayList;

import com.paranerd.mediaplayer.Album;
import com.paranerd.mediaplayer.ArtistAlbumAdapter;
import com.paranerd.mediaplayer.MusicUtils;
import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.Activities.AlbumTracks;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ArtistAlbumFragment extends ListFragment {

	View v;
	ArrayList<Album> albumList;
	String artistName;
	private ArtistAlbumAdapter mAdapter;
	
	public ArtistAlbumFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new View(getActivity());
		
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras!=null) {
			artistName = extras.getString("Artist");
		}
		
		//getLoaderManager().initLoader(1, null, this);
		//artistList = MusicUtils.getAllArtists(getActivity());
		albumList = MusicUtils.getAlbumsForArtist(getActivity(), artistName);
		mAdapter = new ArtistAlbumAdapter(getActivity(), android.R.layout.simple_list_item_1, albumList);
		setListAdapter(mAdapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.page_test, container, false);
		return v;
	}
	
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Intent artistalbums = new Intent(getActivity(), AlbumTracks.class);
		artistalbums.putExtra("Album", albumList.get(position).getAlbumName());
		startActivity(artistalbums);
    }
    
	/*@Override
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
	}*/
}
