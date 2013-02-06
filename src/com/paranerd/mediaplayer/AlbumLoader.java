package com.paranerd.mediaplayer;

import java.util.ArrayList;

import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AlbumColumns;

public class AlbumLoader extends AsyncTaskLoader<ArrayList<Album>> {

	private ArrayList<Album> mAlbumList;
	private Context mContext;
	private Bitmap cover;
	
	public AlbumLoader(Context context) {
		super(context);
		this.mContext = context;
		//Toast.makeText(mContext, "LoadinBackground", Toast.LENGTH_SHORT).show();
	}

	@Override
	public ArrayList<Album> loadInBackground() {
		try {
		 mAlbumList = new ArrayList<Album>();
		 String[] columns = { BaseColumns._ID,
			        AlbumColumns.ALBUM,
			        AlbumColumns.ARTIST,
			        AlbumColumns.ALBUM_ART };
			Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
				            columns, null, null, MediaStore.Audio.Albums.ALBUM);
			if(cursor!=null && cursor.moveToFirst()) {
	            do {
	                // Get album name
	                final String albumName = cursor.getString(1);

	                // Get artist name
	                final String artistName = cursor.getString(2);
	                
	                // Get album cover
	               final String coverPath = cursor.getString(3);
	                
	                // Get Cover from coverPath
	               if(coverPath != null) {
	            	   cover = Shrinkmethod(coverPath, 50, 50);
	               } else {
	            	   cover = null;
	               }
	                
	                // Create a new album
	                final Album album = new Album(albumName, artistName, cover, coverPath);
	                mAlbumList.add(album);
	            } while (cursor.moveToNext());
			}
	            
	        if (cursor != null) {
	            cursor.close();
	            cursor = null;
	        }
	  Album.setAlbumList(mAlbumList);
		} catch (Exception e) {
			Log.e("AlbumLoaderManager", e.getMessage(), e);
		}
		return mAlbumList;
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
