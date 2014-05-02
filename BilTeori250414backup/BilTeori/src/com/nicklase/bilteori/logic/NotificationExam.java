package com.nicklase.bilteori.logic;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.gui.MainActivity;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;

public class NotificationExam {
	private Context context;
	public final int NOTIFICATION_ID = 1;
	public static boolean canNotification=true;
	/// <summary>
	///   Starts a new notification.
	/// </summary>
	public void startInForeground(Context context)
	{	
		if(canNotification){
			this.context=context;
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(context)
			        .setSmallIcon(R.drawable.ic_launcher)
			        .setContentTitle("Teoritentamen")
			        .setAutoCancel(true)
			        .setContentText("Husk at du holder på med en teoritentamen!");
			// explicit intent for the Activity
			Intent resultIntent = new Intent(context, com.nicklase.bilteori.gui.ExamOneActivity.class);
			
			resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			
			PendingIntent resultPendingIntent = PendingIntent.getActivity(context,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
	
			mBuilder.setContentIntent(resultPendingIntent);
			
			NotificationManager mNotificationManager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());	
			}
		}
	/// <summary>
	///   Used to cancle the notification which is running.
	/// </summary>
	public void cancleNotification(int id){
		NotificationManager mNotificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(id);
		
	}

}
