package com.paranerd.mediaplayer;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AlbumColumns;
import android.provider.MediaStore.Audio.ArtistColumns;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MusicUtils {

	private static ArrayList<Album> albumList = new ArrayList<Album>();
	private static ArrayList<Artist> artistList = new ArrayList<Artist>();
	private static Bitmap cover;
	
	public static ArrayList<Album> getAllAlbums(Context context) {
		String[] columns = { BaseColumns._ID,
		        AlbumColumns.ALBUM,
		        AlbumColumns.ARTIST,
		        AlbumColumns.ALBUM_ART };
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
			            columns, null, null, MediaStore.Audio.Albums.ALBUM);
		if(cursor!=null && cursor.moveToFirst()) {
            do {
                // Get album name
                final String albumName = cursor.getString(1);

                // Get artist name
                final String artistName = cursor.getString(2);
                
                // Get album cover
                final String coverPath = cursor.getString(3);
                
                if(coverPath != null) {
	            	   cover = Shrinkmethod(coverPath, 50, 50);
	               } else {
	            	   cover = null;
	               }
                
             // Create a new album
                //final Album album = new Album(albumName, artistName, coverPath);
                final Album album = new Album(albumName, artistName, cover, coverPath);
                albumList.add(album);
            } while (cursor.moveToNext());
		}
            
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return albumList;
	}

	public static ArrayList<Artist> getAllArtists(Context context) {
		String[] columns = { BaseColumns._ID,
		        ArtistColumns.ARTIST,
		        ArtistColumns.NUMBER_OF_ALBUMS };
		
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
			            columns, null, null, null);
		if(cursor!=null && cursor.moveToFirst()) {
            do {
                // Get artist name
                final String artistname = cursor.getString(1);

                // Get album count
                final int albumcount = cursor.getInt(2);
                
             // Create a new album
                final Artist artist = new Artist(artistname, albumcount);
                artistList.add(artist);
            } while (cursor.moveToNext());
		}
            
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return artistList;
	}
	
	public static ArrayList<Album> getAlbumsForArtist(Context context, String artistname) {
		ArrayList<Album> albumForArtistList = new ArrayList<Album>();
	      String[] columns = {
	              MediaStore.Audio.Albums._ID,
	              MediaStore.Audio.Albums.ALBUM,
	              MediaStore.Audio.Albums.ARTIST };

	          String where = android.provider.MediaStore.Audio.Albums.ARTIST
	              + "=?" + ") GROUP BY (" + android.provider.MediaStore.Audio.Albums.ALBUM;

	          String whereVal[] = {artistname};

	          Cursor cursor = context.getContentResolver().query(
	        		  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
	        		  columns,
		              where,
		              whereVal,
		              null);
		
		if(cursor!=null && cursor.moveToFirst()) {
            do {
                // Get album name
                final String albumName = cursor.getString(1);

                // Get artist name
                final String artistName = cursor.getString(2);
                
                Cursor cursor2 = context.getContentResolver().query(
                		MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                		new String[] {BaseColumns._ID, MediaStore.Audio.Albums.ALBUM_ART},
                		MediaStore.Audio.Albums.ALBUM+"=?",
                		new String[] {albumName},
                		null);
                if(cursor2!=null && cursor2.moveToFirst()) {
        		cursor2.moveToFirst();
        		String coverPath = cursor2.getString(1);

                // Get Cover from coverPath
               if(coverPath != null) {
            	   cover = Shrinkmethod(coverPath, 50, 50);
               } else {
            	   cover = null;
               }
               
             // Create a new album
        		final Album album = new Album(albumName, artistName, cover, coverPath);
                albumForArtistList.add(album);
                }
                if (cursor2 != null) {
                    cursor2.close();
                    cursor2 = null;
                }
            } while (cursor.moveToNext());
		}
            
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return albumForArtistList;
	}
	
	public static ArrayList<String> getSongsForAlbum(Context context, String albumname) {
	      String[] columns = { MediaStore.Audio.Media.DATA,
	              MediaStore.Audio.Media._ID,
	              MediaStore.Audio.Media.TITLE };
      String where = android.provider.MediaStore.Audio.Media.ALBUM
              + "=?";
      String whereVal[] = {albumname};
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
			    columns,
			    where,
			    whereVal,
			    AudioColumns.TRACK + ", " + MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		final int len = cursor.getCount();
		ArrayList<String> albumSongList = new ArrayList<String>();
		
		cursor.moveToFirst();
      int columnIndex = -1;
      try {
          columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.TITLE);
      } catch (final IllegalArgumentException notaplaylist) {
          columnIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID);
      }
      for (int i = 0; i < len; i++) {
          albumSongList.add(cursor.getString(columnIndex));
          cursor.moveToNext();
      }
      cursor.close();
      cursor = null;
      return albumSongList;
	}
	
	public static ArrayList<Song> getSongsForArtist(Context context, String artistName) {
		ArrayList<Song> artistSongList = new ArrayList<Song>();
		
	      String[] columns = { MediaStore.Audio.Media.DATA,
	              MediaStore.Audio.Media._ID,
	              MediaStore.Audio.Media.TITLE,
	              MediaStore.Audio.Media.ARTIST,
	              MediaStore.Audio.Media.ALBUM,
	              MediaStore.Audio.Media.DURATION};
	      String where = android.provider.MediaStore.Audio.Media.ARTIST
            + "=?";
    
	      String whereVal[] = {artistName};

	      Cursor cursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
			    columns,
			    where,
			    whereVal,
			    MediaStore.Audio.Media.TITLE
			    /*AudioColumns.TRACK + ", " + MediaStore.Audio.Media.DEFAULT_SORT_ORDER*/);

	      if(cursor!=null && cursor.moveToFirst()) {
            do {
                // Get songname
                final String songname = cursor.getString(2);

                // Get artistname
                final String artistname = cursor.getString(3);
                
                // Get albumname
                final String albumname = cursor.getString(4);
                
                // Get duration
                final String duration = cursor.getString(5);
                
                // Create a new album
                final Song song = new Song(songname, artistname, albumname, duration);
                artistSongList.add(song);
            } while (cursor.moveToNext());
		}
            
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return artistSongList;
	}
	
	static Bitmap Shrinkmethod(String file, int width, int height){
        BitmapFactory.Options bitopt=new BitmapFactory.Options();
        bitopt.inJustDecodeBounds=true;
        Bitmap bit=BitmapFactory.decodeFile(file, bitopt);

        int h=(int) Math.ceil(bitopt.outHeight/(float)height);
        int w=(int) Math.ceil(bitopt.outWidth/(float)width);

        if(h>1 || w>1){
            if(h>w){
                bitopt.inSampleSize=h;

            }else{
                bitopt.inSampleSize=w;
            }
        }
        bitopt.inJustDecodeBounds=false;
        bit=BitmapFactory.decodeFile(file, bitopt);

        return bit;
    }
}
