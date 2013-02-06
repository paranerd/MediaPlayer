package com.paranerd.mediaplayer;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Album {
	private String albumname;
	private String artistname;
	private Bitmap img;
	private String coverpath;
	private static ArrayList<Album> albumlist;
	
	public Album(String albumname, String artistname, Bitmap cover, String coverPath) {
		this.albumname = albumname;
		this.artistname = artistname;
		this.coverpath = coverPath;
		this.img = cover;
		//albumlist.add(albumname);
		//addAlbum(albumname);
	}
	
	public String getAlbumName() {
		return this.albumname;
	}
	
	public String getArtistName() {
		return this.artistname;
	}
	
	public Bitmap getCover() {
		return this.img;
	}
	
	public static void setAlbumList(ArrayList<Album> list) {
		albumlist = list;
	}
	
	public static String getAlbumNameNew(int pos) {
		return albumlist.get(pos).getAlbumName();
	}
	
	public static String getCoverPathNew(int pos) {
		return albumlist.get(pos).getCoverPath();
	}
	
	public String getCoverPath() {
		return this.coverpath;
	}
}
