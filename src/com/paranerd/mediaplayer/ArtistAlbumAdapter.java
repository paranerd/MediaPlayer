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

public class ArtistAlbumAdapter extends ArrayAdapter<Album> {

	private ArrayList<Album> albums;
	public ArtistAlbumAdapter (Context context, int textViewResourceId, ArrayList<Album> albums) {
		super(context, textViewResourceId, albums);
		this.albums = albums;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		AlbumViewHolder viewHolder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.img_two_lines, null);
			//LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//convertView = inflater.inflate(R.layout.list_item, null);
			
			viewHolder = new AlbumViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.toprow);
			viewHolder.artist = (TextView) convertView.findViewById(R.id.bottomrow);
			viewHolder.cover = (ImageView) convertView.findViewById(R.id.image);
		}
		
		Album a = albums.get(position);
		
		if (a != null) {
			String coverPath = a.getCoverPath();
			Drawable img = Drawable.createFromPath(coverPath);
			ImageView cover = (ImageView) convertView.findViewById(R.id.image);
			TextView name = (TextView) convertView.findViewById(R.id.toprow);
			TextView artist = (TextView) convertView.findViewById(R.id.bottomrow);
			
			if (name != null) {
				name.setText(a.getAlbumName());
			}
			if (artist != null) {
				artist.setText(a.getArtistName());
			}
			if (cover != null) {
				cover.setImageDrawable(img);
			}
		}
		
		return convertView;
	}
	
    static class AlbumViewHolder {
        TextView name;
        TextView artist;
        ImageView cover;
    }
}