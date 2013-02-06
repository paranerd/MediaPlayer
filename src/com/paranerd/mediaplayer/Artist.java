package com.paranerd.mediaplayer;

import java.util.ArrayList;

public class Artist {
	private String albumname;
	private int albumcount;
	private static ArrayList<Artist> artistlist;
	
	public Artist(String artistname, int albumcount) {
		this.albumname = artistname;
		this.albumcount = albumcount;
	}
	
	public String getArtistName() {
		return albumname;
	}
	
	public String getAlbumCount() {
		return String.valueOf(albumcount);
	}

	public static void setArtistList(ArrayList<Artist> list) {
		artistlist = list;
	}
	
	public static String getArtistName(int pos) {
		return artistlist.get(pos).getArtistName();
	}
}
