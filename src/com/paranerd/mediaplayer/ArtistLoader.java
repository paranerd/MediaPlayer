package com.paranerd.mediaplayer;

import java.util.ArrayList;

import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.ArtistColumns;

public class ArtistLoader extends AsyncTaskLoader<ArrayList<Artist>> {

	private ArrayList<Artist> mArtistList;
	private Context mContext;
	private Bitmap cover;
	
	public ArtistLoader(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	public ArrayList<Artist> loadInBackground() {
		try {
		 mArtistList = new ArrayList<Artist>();
			String[] columns = { BaseColumns._ID,
			        ArtistColumns.ARTIST,
			        ArtistColumns.NUMBER_OF_ALBUMS };
			
			Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
				            columns, null, null, null);
			if(cursor!=null && cursor.moveToFirst()) {
	            do {
	                // Get artist name
	                final String artistname = cursor.getString(1);

	                // Get album count
	                final int albumcount = cursor.getInt(2);
	                
	                // Create a new artist
	                final Artist artist = new Artist(artistname, albumcount);
	                mArtistList.add(artist);
	            } while (cursor.moveToNext());
			}
	            
	        if (cursor != null) {
	            cursor.close();
	            cursor = null;
	        }
	  Artist.setArtistList(mArtistList);
		} catch (Exception e) {
			Log.e("ArtistLoader", e.getMessage(), e);
		}
		return mArtistList;
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
	public void deliverResult(ArrayList<Artist> artistList) {
		if (isReset()) {
			if(artistList != null) {
				onReleaseResources(artistList);
				return;
			}
		}
		ArrayList<Artist> oldArtist = mArtistList;
		mArtistList = artistList;
		
		if(isStarted()) {
			super.deliverResult(artistList);
		}
		
		if(oldArtist != null && oldArtist != artistList) {
			onReleaseResources(oldArtist);
		}
	}
	
	protected void onReleaseResources(ArrayList<Artist> artistList) {
		// Nothing to do here for a simple List
	}
	
	@Override
	public void forceLoad() {
		super.forceLoad();
	}
	
	@Override
	protected void onStartLoading() {
		if(mArtistList != null) {
			deliverResult(mArtistList);
		}
		
		if(takeContentChanged()) {
			forceLoad();
		} else if (mArtistList == null) {
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
		
		if(mArtistList != null) {
			onReleaseResources(mArtistList);
			mArtistList = null;
		}
	}
	
	@Override
	public void onCanceled(ArrayList<Artist> artistList) {
		super.onCanceled(artistList);
		onReleaseResources(artistList);
	}
}
