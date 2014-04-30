package com.nicklase.bilteori.gui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicklase.bilteori.R;

public class TakePictureActivity extends Activity {
	public static final int MEDIA_TYPE_IMAGE = 1;
	Bitmap bitMap=null;

	/// <summary>
    /// This method is run on create.
    /// </summary>
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_picture);
		// Show the Up button in the action bar.
		setUpActionBar();
		final Button buttonTakePicture = (Button) findViewById(R.id.btnTakePicture);
		
		buttonTakePicture.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	takePicture();
	         }
	     });
	}

	/// <summary>
    /// Sets up the action bar if the api level is greater or equal to HONEYCOMB.
    /// </summary>
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setUpActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	/// <summary>
	///   Starts the settings activity.
	/// </summary>
	private void changeToSettingsActivity(){
		 Intent intent = new Intent(com.nicklase.bilteori.gui.TakePictureActivity.this, com.nicklase.bilteori.gui.SettingsActivity.class);
   	 startActivity(intent);
	}
	/// <summary>
	///   Starts the exam activity.
	/// </summary>
	private void changeToExamActivity(){
		 Intent intent = new Intent(com.nicklase.bilteori.gui.TakePictureActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
  	 startActivity(intent);
	}

	/// <summary>
	///   Inflates the action bar in the layout.
	/// </summary>
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/// <summary>
	///   When a menu item is clicked run some code.
	/// </summary>
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch (item.getItemId()){
		case R.id.action_settings:
			changeToSettingsActivity();
			break;
			
		case R.id.action_examOne:
			changeToExamActivity();
			break;


		}
		return false;
		//super.onOptionsItemSelected(item);
		
	}
	/// <summary>
    /// Starts the activity to take picture.
    /// </summary>
	private void takePicture(){
		if(hasCamera()&& hasDefualtCameraApp(MediaStore.ACTION_IMAGE_CAPTURE)){
	    // create intent with ACTION_IMAGE_CAPTURE action 
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    // start camera activity
	    startActivityForResult(intent, MEDIA_TYPE_IMAGE);
		}else{
			TextView header= (TextView)findViewById(R.id.textViewHeaderPicture);
			header.setText("Det er noe galt med kamera funksjonen.");
		}

	} 
	
	/// <summary>
    /// Gets the result from the last activity closed.
    /// </summary>
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
ImageView img = (ImageView) findViewById(R.id.imageView1);
    if (requestCode == MEDIA_TYPE_IMAGE && resultCode== RESULT_OK && intent != null){
        // get bundle
        Bundle extras = intent.getExtras();

        // get bitmap
        bitMap = (Bitmap) extras.get("data");
        img.setImageBitmap(bitMap);

    }
}
/// <summary>
///  Method to check you have Camera Apps
/// </summary>
private boolean hasDefualtCameraApp(String action){
    final PackageManager packageManager = getPackageManager();
    final Intent intent = new Intent(action);
    List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

    return list.size() > 0;

}
/// <summary>
///   Method to check if you have a Camera.
/// </summary>
private boolean hasCamera(){
    return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
}	

}
