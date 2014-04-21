package com.nicklase.bilteori.logic;

import android.content.Context;
import android.media.MediaPlayer;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.gui.ExamOneActivity;

public class Sounds {
	
	public static boolean canPlaySound=true;
	
	public static void playFinishSound(Context context){
	if(canPlaySound){
		MediaPlayer lyden = MediaPlayer.create(context, R.raw.victorysound);
		lyden.start();
	}
	
	
}
}
