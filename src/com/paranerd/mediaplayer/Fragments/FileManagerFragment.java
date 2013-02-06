package com.paranerd.mediaplayer.Fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.R.id;
import com.paranerd.mediaplayer.R.layout;
import com.paranerd.mediaplayer.Activities.PlayerActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileManagerFragment extends ListFragment implements LoaderCallbacks<Cursor> {

	private List<String> item = null;
	private List<String> path = null;
	private List<String> trackList = null;
	private String root="/storage/";
	private View v;
	private Cursor cursor;
	private SimpleCursorAdapter mCursorAdapter;
	private TextView myPath;
	
	private Uri[] mMediaSource = {null, MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI};

	public FileManagerFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = new View(getActivity());
		
		String[] columns = { android.provider.MediaStore.Audio.Artists._ID,
		        android.provider.MediaStore.Audio.Artists.ARTIST};
			    cursor = getActivity().getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
			            columns, null, null, null);
				mCursorAdapter = new SimpleCursorAdapter(getActivity(),
						android.R.layout.simple_list_item_1, null,
						new String[] { MediaStore.Audio.Artists.ARTIST },
						new int[] { android.R.id.text1 }, 0);
				setListAdapter(mCursorAdapter);
				getLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.page_three, container, false);
		myPath = (TextView) v.findViewById(R.id.path);
		getDir(root);
		return v;
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Loader<Cursor> loader = new CursorLoader(getActivity(), mMediaSource[1],
				null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		mCursorAdapter.swapCursor(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mCursorAdapter.swapCursor(null);

	}
	
    private void getDir(String dirPath) {
    	myPath.setText("Location: ");
    	item = new ArrayList<String>(); // Enthält die Namen der Dateien
    	path = new ArrayList<String>(); // Enthält den vollständigen Pfad der Datei
    	trackList = new ArrayList<String>(); // Enthält nur die ausgewählten Musikdateien
    	File f = new File(dirPath);
    	File[] files = f.listFiles();

    	if(!dirPath.equals(root))
    	{
    		//item.add(root);
    		//path.add(root);
    		item.add("../");
    		path.add(f.getParent());
    	}
    	
        Comparator<? super File> filecomparator = new Comparator<File>(){
       	  
       	  public int compare(File file1, File file2) {

       	   if(file1.isDirectory()){
       	    if (file2.isDirectory()){
       	     return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
       	    } else {
       	     return -1;
       	    }
       	   } else {
       	    if (file2.isDirectory()){
       	     return 1;
       	    } else {
       	     return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
       	    }
       	   }
       	  }  
       	 };
       	 
    	Arrays.sort(files, filecomparator);

     for(int i=0; i < files.length; i++)
     {
    	 File file = files[i];
    	 path.add(file.getPath());
    	 if(file.isDirectory())
    		 item.add(file.getName() + "/");
    	 else {
    		 String fullPath = file.getAbsolutePath();
    		 int dot = fullPath.lastIndexOf(".");
    		 String ext = fullPath.substring(dot + 1);

    		 if(ext.equals("mp3") || ext.equals("ogg")) {
        		 item.add(file.getName());
        		 trackList.add(file.getPath());
    		 }
    	 }
     }
     ArrayAdapter<String> fileList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, item);
     //fileList.getFilter().filter("etc"); --> later, for filtering!
     setListAdapter(fileList);
    }
    
    public void onListItemClick(ListView l, View v, int position, long id) {
    	// If FileManager-Fragment
    		File file = new File(path.get(position));
    		if (file.isDirectory())
    		{
    			if(file.canRead())
    				getDir(path.get(position));
    			else
    			{
    				Toast.makeText(getActivity(), "Folder can't be read!", Toast.LENGTH_SHORT).show();
    			}
    		}
    		else
    		{
    			Intent intent = new Intent(getActivity(), PlayerActivity.class);
    			intent.putExtra("TrackPath", file.getAbsolutePath());
    			//intent.putExtra("DirPath", file.getParent());
    			startActivity(intent);
    		}
    }

}
