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
	/// <summary>
	///   Constructor for the soundchannel class.
	/// </summary>
	public SoundChannel(Context context){
		this.context=context;
	}

	/// <summary>
	///   Plays the finish sound.
	/// </summary>
	public  void playFinishSound(){
		if(canPlaySound){
			// you need to send the context of the application and R.folder.filename
			theSound = MediaPlayer.create(this.context, R.raw.victorysound);
			//sets up a listener and runs the code when the sound has played
			theSound.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					//close the soundchannel so it can be used again
					closeSoundChannel();
					isReset=true;
				}
			});
			theSound.start();
			isActive=true;
		}


	}
	/// <summary>
	///   Resets the sound.
	/// </summary>

	public void resetSound(){
		theSound.reset();
	}
	/// <summary>
	///   Release the sound from the soundchannel.
	/// </summary>
	public void releaseSound(){
		theSound.release();
	}
	/// <summary>
	///   Resets and then releases the sound.
	/// </summary>
	public void closeSoundChannel(){
		if(isActive && !isReset){
			resetSound();
			releaseSound();
		}
	}
}
