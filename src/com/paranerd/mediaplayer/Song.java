package com.paranerd.mediaplayer;

public class Song {
	
	private String songname;
	private String artistname;
	private String albumname;
	private String duration;
	
	public Song(String name, String artist, String album, String dur) {
		this.songname = name;
		this.artistname = artist;
		this.albumname = album;
		this.duration = dur;
	}
	
	public String getSongName() {
		return songname;
	}
	
	public String getArtistName() {
		return artistname;
	}
	
	public String getAlbumName() {
		return albumname;
	}
	
	public String getDuration() {
		return duration;
	}

}
