package com.nicklase.bilteori.logic;

import android.content.Context;
import android.os.Vibrator;

public class Vibration {

	public static boolean canVibrate=true;
	/// <summary>
    /// Makes the phone vibrate for 0.2sec.
    /// </summary>
    public static void  shortVibrate(Context context){
    	if(canVibrate){
    	  Vibrator vibs = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	    	 vibs.vibrate(200);
    	}
      }
  /// <summary>
    /// Makes the phone vibrate for 2sec.
    /// </summary>
    public static void  longVibrate(Context context){
    	if(canVibrate){
    	  Vibrator vibs = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	    	 vibs.vibrate(2000);
    	}
      }
}
