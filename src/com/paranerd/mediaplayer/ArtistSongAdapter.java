package com.paranerd.mediaplayer;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtistSongAdapter extends ArrayAdapter<Song> {

	private ArrayList<Song> songs;
	public ArtistSongAdapter (Context context, int textViewResourceId, ArrayList<Song> songs) {
		super(context, textViewResourceId, songs);
		this.songs = songs;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		AlbumViewHolder viewHolder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.img_two_lines, null);
			//LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//convertView = inflater.inflate(R.layout.list_item, null);
			
			viewHolder = new AlbumViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.toprow);
			viewHolder.album = (TextView) convertView.findViewById(R.id.bottomrow);
			//viewHolder.cover = (ImageView) convertView.findViewById(R.id.image);
		}
		
		Song a = songs.get(position);
		
		if (a != null) {
			//String coverPath = a.getCoverPath();
			//Drawable img = Drawable.createFromPath(coverPath);
			//ImageView cover = (ImageView) convertView.findViewById(R.id.image);
			TextView name = (TextView) convertView.findViewById(R.id.toprow);
			TextView album = (TextView) convertView.findViewById(R.id.bottomrow);
			
			if (name != null) {
				name.setText(a.getSongName());
			}
			if (album != null) {
				album.setText(a.getAlbumName());
			}
			//if (cover != null) {
			//	cover.setImageDrawable(img);
			//}
		}
		
		return convertView;
	}
	
    static class AlbumViewHolder {
        TextView name;
        TextView album;
        //ImageView cover;
    }
}