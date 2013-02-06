package com.paranerd.mediaplayer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AlbumAdapter extends ArrayAdapter<Album> {

	private ArrayList<Album> albums;
	public AlbumAdapter (Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		//this.albums = albumList;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		AlbumViewHolder viewHolder;
		Album a;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.img_two_lines, null);
			//LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//convertView = inflater.inflate(R.layout.list_item, null);
			
			viewHolder = new AlbumViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.toprow);
			viewHolder.artist = (TextView) convertView.findViewById(R.id.bottomrow);
			viewHolder.cover = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		} else {
		viewHolder = (AlbumViewHolder) convertView.getTag();
		}
			a = getItem(position);

		
			viewHolder.name.setText(a.getAlbumName());
			viewHolder.artist.setText(a.getArtistName());
			viewHolder.cover.setImageBitmap(a.getCover());
		
		return convertView;
	}
	
    static class AlbumViewHolder {
        TextView name;
        TextView artist;
        ImageView cover;
    }
    
    public void setData(ArrayList<Album> arg1) {
    	clear();
    	if(arg1 != null) {
    		for (int i=0; i < arg1.size(); i++) {
    			add(arg1.get(i));
    		}
    	}
    }
}
