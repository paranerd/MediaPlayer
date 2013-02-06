package com.paranerd.mediaplayer.Fragments;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.paranerd.mediaplayer.R;
import com.paranerd.mediaplayer.R.drawable;
import com.paranerd.mediaplayer.R.id;
import com.paranerd.mediaplayer.R.layout;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerFragment extends Fragment implements OnClickListener, OnTouchListener {

	private static MediaPlayer mediaPlayer;
	private static int playbackPosition;
	private static ImageButton play;
	private ImageButton stop;
	private TextView titelInfo;
	private static TextView albumInfo;
	private TextView duration;
	private TextView current;
	private TextView bar;
	private ImageView cover;
	boolean musicRunning = false;
	private static String audioPath, dirPath, currTrack;
	private int currentTrackID;
	private Thread update;
	private int width = 4;
	private int dur, cur; // duration of Track, current Position
	private final static String PREFS_NAME = "PlayerSettings";
	private final static String PREFS_LASTTRACK = "LastTrack";
	private final static String PREFS_LASTALBUM = "LastAlbum";
	private final static String PREFS_CURRTRACK = "CurrentTrack";
	private ArrayList<String> trackList;
	private View v;
	Context context;
	PlayerFragment fragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		trackList = new ArrayList<String>();
		savePreferences();
		getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).edit().putString(PREFS_CURRTRACK, "World").commit();
		//String test = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).getString(PREFS_CURRTRACK, null);

		audioPath = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).getString(PREFS_LASTTRACK, null);
		dirPath = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).getString(PREFS_LASTALBUM, null);
		currTrack = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).getString(PREFS_CURRTRACK, null); 
		
		if(audioPath!=null) {
			displayInfo();
		}
		
		if(dirPath!=null) {
			getLastAlbum(dirPath);
		}
		
		update = new Thread(new Runnable()
		{
			@Override
			public void run() {
				try {
					while(true) {
						display();
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
		            e.printStackTrace();
		        }
			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.player, container, false);
		play = (ImageButton) v.findViewById(R.id.playbutton);
		play.setBackgroundResource(R.drawable.play);
		play.setOnClickListener(this);
		stop = (ImageButton) v.findViewById(R.id.stopbutton);
		titelInfo = (TextView) v.findViewById(R.id.titelInfo);
		albumInfo = (TextView) v.findViewById(R.id.albumInfo);
		duration = (TextView) v.findViewById(R.id.duration);
		current = (TextView) v.findViewById(R.id.current);
		bar = (TextView) v.findViewById(R.id.bar);
		cover = (ImageView) v.findViewById(R.id.cover);
		Bundle extras = getActivity().getIntent().getExtras();
		if(extras!=null) {
		String newAudioPath = extras.getString("TrackPath");
		Toast.makeText(getActivity(), newAudioPath, Toast.LENGTH_SHORT).show();
	    if (newAudioPath != null)
	    {
	        if(currTrack != newAudioPath) {
	        	stopAudio();
	        	mediaPlayer=null;
	        	audioPath = newAudioPath;
				playPauseResumeAudio(audioPath);
	        }
	        //dirPath = extras.getString("DirPath");
			displayInfo();
			/*if(!update.isAlive()) {
				update.start();
			}*/
	    }
		}
	    else
	    {
	        // No extras provided
	    }
		return v;
	}

	public void getLastAlbum(String location) {
		ArrayList<String> path = new ArrayList<String>();
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	
    	for(int i=0; i < files.length; i++)
        {
    		File file = files[i];
    		path.add(file.getPath());
    		if(!file.isDirectory()) {
    			if(file.getAbsolutePath().equals(audioPath)) {
    				currentTrackID = i;
    			}
    			String fullPath = file.getAbsolutePath();
    			int dot = fullPath.lastIndexOf(".");
    			String ext = fullPath.substring(dot + 1);

    			if(ext.equals("mp3") || ext.equals("ogg")) {
    				trackList.add(file.getPath());
    			}
    		}
        }
	}
	@SuppressLint("NewApi")
	public void displayInfo() {
				MediaMetadataRetriever mmr = new MediaMetadataRetriever();
				mmr.setDataSource(audioPath);
				String trackName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
				String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
				String artistName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
				String length = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

				dur = Integer.parseInt(length);
				String durSec = String.valueOf((dur % 60000) / 1000);
				String durMin = String.valueOf(dur / 60000);
				if (durSec.length() == 1) {
			        duration.setText("0" + durMin + ":0" + durSec);
			    }else {
			        duration.setText("0" + durMin + ":" + durSec);
			    }
				if(trackName==null) {
					trackName = "Unknown";
				}
				if(albumName==null) {
					albumName = "Unknown";
				}
				if(artistName==null) {
					artistName = "Unknown";
				}
				/*File file = new File(dirPath+"/AlbumArtSmall.jpg");
				if(file.exists()) {
					Toast.makeText(getApplicationContext(), "Exists", Toast.LENGTH_SHORT).show();
					Bitmap bitmapImage = BitmapFactory.decodeFile(dirPath+"/AlbumArtSmall.jpg");
			        cover.setImageBitmap(bitmapImage);
				}*/
				titelInfo.setText(trackName);
				albumInfo.setText(albumName+" - "+artistName);	
	}
	
	public void display() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				width = (int) (480 * ((float)cur/(float)dur));
				bar.setWidth(width);
				if(mediaPlayer!=null) {
					cur = mediaPlayer.getCurrentPosition();
					String curSec = String.valueOf((cur % 60000) / 1000);
					String curMin = String.valueOf(cur / 60000);
					if (curSec.length() == 1) {
						current.setText("0" + curMin + ":0" + curSec);
					} else {
						current.setText("0" + curMin + ":" + curSec);
					}
				}
			}
		});
	}
	
    public static void playPauseResumeAudio(String path)
    {
    	if(path != null) {
    	if(mediaPlayer==null || path!=audioPath) {
    		
	    	try {
	        killMediaPlayer();
	        mediaPlayer = new MediaPlayer();
	        mediaPlayer.setDataSource(path);
	        mediaPlayer.prepare();
	        mediaPlayer.start();
	        
	        //PlayerFragment fragment = new PlayerFragment();
	        //fragment.savePreferences();
	        play.setBackgroundResource(R.drawable.pause);
		    } catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	// Player is running, pause it
    	else if(mediaPlayer!=null && mediaPlayer.isPlaying()) {
    		playbackPosition = mediaPlayer.getCurrentPosition();
    		mediaPlayer.pause();
    		play.setBackgroundResource(R.drawable.play);
    	}
    	// Player is paused, resume
    	else {
    		mediaPlayer.seekTo(playbackPosition);
    		mediaPlayer.start();
    		play.setBackgroundResource(R.drawable.pause);
    	}
    }
    }
    
    public static void stopAudio() {
    	if(mediaPlayer!=null) {
    		mediaPlayer.seekTo(0);
    		mediaPlayer.pause();
    		playbackPosition=0;
    	}
    }
    
    public void previousTrack() {
    	if(currentTrackID>0) {
    		currentTrackID--;
        	audioPath = trackList.get(currentTrackID);
        	try {
        		playPauseResumeAudio(audioPath);
        		displayInfo();
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
    	}
    }
    
    public void nextTrack() {
    	if(currentTrackID<trackList.size()-1) {
    		currentTrackID++;
        	audioPath = trackList.get(currentTrackID);
        	try {
    			playPauseResumeAudio(audioPath);
    			displayInfo();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    private static void killMediaPlayer() {
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.release();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void seek(int x) {
    	width = x;
		int position = (int) ((float) dur * ((float)x/480));
		mediaPlayer.seekTo(position);
		display();
    }
		
	public void ButtonOnClick(View v) {
		switch(v.getId()) {
		case R.id.playbutton:
			try {
				playPauseResumeAudio(audioPath);
				displayInfo();
				if(!update.isAlive()) {
					update.start();	
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.stopbutton:
			stopAudio();
            break;
		case R.id.browse:
			startActivityForResult(new Intent("com.example.simplayer.ALBUMLIST"), 1);
			break;
		case R.id.bprevioustrack:
			previousTrack();
			break;
		case R.id.bnexttrack:
			nextTrack();
			break;
		}
	}

	public void savePreferences() {
		//PlayerActivity act = new PlayerActivity();
		//Activity activity = getActivity();
		//((PlayerActivity) activity).savePreferences();
		//act.savePreferences();
		getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).edit().putString(PREFS_CURRTRACK, "World").commit();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch(action) {
		case MotionEvent.ACTION_DOWN: {
			if(event.getY() > 400 && event.getY() < 600) {
				seek((int) event.getX());
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if(event.getY() > 400 && event.getY() < 600) {
				seek((int) event.getX());
			}
			break;
		}
		case MotionEvent.ACTION_UP: {
			break;
		}
		}
		
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.playbutton:
			playPauseResumeAudio(audioPath);
			Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
		}
		
	}
}
