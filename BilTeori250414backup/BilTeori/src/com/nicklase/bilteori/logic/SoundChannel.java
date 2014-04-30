package com.nicklase.bilteori.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.preference.PreferenceManager;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.gui.ExamOneActivity;

public class SoundChannel {
	
	public static boolean canPlaySound=true;
	private  Context context;
	private MediaPlayer theSound;
	private boolean isActive=false;
	private boolean isReset=false;
	public SoundChannel(Context context){
		this.context=context;
	}
	
	
	public  void playFinishSound(){
	if(canPlaySound){
		theSound = MediaPlayer.create(this.context, R.raw.victorysound);
		theSound.setOnCompletionListener(new OnCompletionListener() {
	        @Override
	        public void onCompletion(MediaPlayer mp) {
	        	closeSoundChannel();
	        	isReset=true;
	        }
	    });
		theSound.start();
		isActive=true;
	}
	
	
}
	
	public void resetSound(){
		theSound.reset();
	}
	public void releaseSound(){
		theSound.release();
	}
	public void closeSoundChannel(){
		if(isActive && !isReset){
		resetSound();
		releaseSound();
		}
	}
}
