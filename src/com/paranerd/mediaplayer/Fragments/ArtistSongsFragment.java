package com.paranerd.mediaplayer.Fragments;

import java.util.ArrayList;

import com.paranerd.mediaplayer.ArtistAlbumAdapter;
import com.paranerd.mediaplayer.ArtistSongAdapter;
import com.paranerd.mediaplayer.MusicUtils;
import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.R.layout;
import com.paranerd.mediaplayer.Song;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ArtistSongsFragment extends ListFragment {

	private View v;
	private String artistName;
	private ArtistSongAdapter mAdapter;
	ArrayList<Song> songList;
	
	public ArtistSongsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new View(getActivity());
		
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras!=null) {
			artistName = extras.getString("Artist");
		}
		
		songList = MusicUtils.getSongsForArtist(getActivity(), artistName);
		String size = String.valueOf(songList.size());
		Toast.makeText(getActivity(), size, Toast.LENGTH_SHORT).show();
		mAdapter = new ArtistSongAdapter(getActivity(), android.R.layout.simple_list_item_1, songList);
		setListAdapter(mAdapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.page_test, container, false);
		return v;
	}
}
