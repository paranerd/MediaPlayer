package com.paranerd.mediaplayer;

import java.util.ArrayList;

import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.ArtistColumns;

public class ArtistAlbumLoader extends AsyncTaskLoader<ArrayList<Album>> {

	private ArrayList<Album> mAlbumList;
	private Context mContext;
	private Bitmap cover;
	private String artistName;
	
	public ArtistAlbumLoader(Context context, String artistname) {
		super(context);
		this.mContext = context;
		this.artistName = artistname;
	}

	@Override
	public ArrayList<Album> loadInBackground() {
		ArrayList<Album> albumForArtistList = new ArrayList<Album>();
		try {
		      String[] columns = {
		              MediaStore.Audio.Albums._ID,
		              MediaStore.Audio.Albums.ALBUM,
		              MediaStore.Audio.Albums.ARTIST };

		          String where = android.provider.MediaStore.Audio.Albums.ARTIST
		              + "=?" + ") GROUP BY (" + android.provider.MediaStore.Audio.Albums.ALBUM;

		          String whereVal[] = {artistName};

		          Cursor cursor = mContext.getContentResolver().query(
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
	                
	                Cursor cursor2 = mContext.getContentResolver().query(
	                		MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
	                		new String[] {BaseColumns._ID, MediaStore.Audio.Albums.ALBUM_ART},
	                		MediaStore.Audio.Albums.ALBUM+"=?",
	                		new String[] {albumName},
	                		null);
	                if(cursor2!=null && cursor2.moveToFirst()) {
	        		cursor2.moveToFirst();
	        		String coverPath = cursor2.getString(1);

	             // Create a new album
	        		final Album album = new Album(albumName, artistName, null, null);
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
	  Album.setAlbumList(albumForArtistList);
		} catch (Exception e) {
			Log.e("ArtistAlbumLoader", e.getMessage(), e);
		}
		return albumForArtistList;
	}
	
	Bitmap Shrinkmethod(String file, int width, int height){
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
	
	@Override
	public void deliverResult(ArrayList<Album> albumList) {
		if (isReset()) {
			if(albumList != null) {
				onReleaseResources(albumList);
				return;
			}
		}
		ArrayList<Album> oldAlbum = mAlbumList;
		mAlbumList = albumList;
		
		if(isStarted()) {
			super.deliverResult(albumList);
		}
		
		if(oldAlbum != null && oldAlbum != albumList) {
			onReleaseResources(oldAlbum);
		}
	}
	
	protected void onReleaseResources(ArrayList<Album> albumList) {
		// Nothing to do here for a simple List
	}
	
	@Override
	public void forceLoad() {
		super.forceLoad();
	}
	
	@Override
	protected void onStartLoading() {
		if(mAlbumList != null) {
			deliverResult(mAlbumList);
		}
		
		if(takeContentChanged()) {
			forceLoad();
		} else if (mAlbumList == null) {
			forceLoad();
		}
	}
	
	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onReset() {
		onStopLoading();
		
		if(mAlbumList != null) {
			onReleaseResources(mAlbumList);
			mAlbumList = null;
		}
	}
	
	@Override
	public void onCanceled(ArrayList<Album> albumList) {
		super.onCanceled(albumList);
		onReleaseResources(albumList);
	}
}
