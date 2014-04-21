package com.nicklase.bilteori.logic;

import java.io.IOException;
import java.util.Random;

import com.nicklase.bilteori.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {

	  private static final String ACTION_CLICK = "ACTION_CLICK";
	  private FileWriter errorWriter= new FileWriter(Constant.WRITE_ERROR);
	  @Override
	  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	      int[] appWidgetIds) {

	    // Get all ids
	    ComponentName thisWidget = new ComponentName(context,
	    		com.nicklase.bilteori.logic.MyWidgetProvider.class);
	    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	    for (int widgetId : allWidgetIds) {
	    	String[] images={"feltforfart","gatetun","parkeringssone","vikeplikt","vegskulder","slash60sone","motortrafikkvei"};
	    	
	      // create some random data
	      int number = (new Random().nextInt(images.length));
	      
	      
	      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
	          R.layout.widget_layout);
	      
	      
	      remoteViews.setImageViewResource(R.id.imageViewSigns, getImageReousrce(context, images[number]));
	      Log.w("WidgetExample", String.valueOf(number));
	      // Set the text
	      remoteViews.setTextViewText(R.id.textViewUpdate, String.valueOf(number));
	      remoteViews.setTextViewText(R.id.textViewNavnPaSkilt, "navn:"+images[number]);

	      // Register an onClickListener
	      Intent intent = new Intent(context, com.nicklase.bilteori.logic.MyWidgetProvider.class);

	      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

	      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
	          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	      remoteViews.setOnClickPendingIntent(R.id.imageViewSigns, pendingIntent);
	      appWidgetManager.updateAppWidget(widgetId, remoteViews);
	    }
	  }
	  
	  private int getImageReousrce(Context context, String imagepath){
		  int imageResource=0;
		   try{
		   imageResource = context.getResources().getIdentifier(imagepath, "drawable", context.getPackageName());
		   }catch( Resources.NotFoundException e){
			   e.printStackTrace();
			 errorWriter.saveDataToFile(e.toString(), context);
		   }
		   
		  return  imageResource;
	  }
	  @Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "Bilteori widget slettet", Toast.LENGTH_SHORT).show();
	  }
	} 