package com.nicklase.bilteori.gui;



import com.nicklase.bilteori.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpButtons();
	}
	
	public void bookClick(View view) {
		Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this,com.nicklase.bilteori.gui.BookActivity.class);
   	 	startActivity(intent);
	}
	
	
	public void setUpButtons(){
		final Button buttonBook = (Button) findViewById(R.id.btn_book);
		 buttonBook.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this,com.nicklase.bilteori.gui.BookActivity.class);
	        	 startActivity(intent);
	         }
	     });
		 final Button buttonExam = (Button) findViewById(R.id.btn_exam_one);
		 buttonExam.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
	        	 startActivity(intent);
	         }
	     });
	/*	 final Button buttonExamTwo = (Button) findViewById(R.id.btn_exam_two);
		 buttonExamTwo.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this, com.nicklase.bilteori.gui.ExamTwoActivity.class);
	        	 startActivity(intent);
	         }
	     }); */
		 final Button buttonSettings = (Button) findViewById(R.id.btn_setting);
		 buttonSettings.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 settings();
	         }
	     });
		 final Button buttonStatistics = (Button) findViewById(R.id.btn_stats);
		 buttonStatistics.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this, com.nicklase.bilteori.gui.StatisticsActivity.class);
	        	 startActivity(intent);
	         }
	     }); 
		 final Button buttonPicture = (Button) findViewById(R.id.btn_takePicture);
		 buttonPicture.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this, com.nicklase.bilteori.gui.TakePictureActivity.class);
	        	 startActivity(intent);
	         }
	     });
		 final Button buttonMap = (Button) findViewById(R.id.btn_map);
		 buttonMap.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this, com.nicklase.bilteori.gui.MapActivity.class);
	        	 startActivity(intent);
	         }
	     });	
	}
	
	private void settings(){
		 Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this, com.nicklase.bilteori.gui.SettingsActivity.class);
    	 startActivity(intent);
	}
	
	private void startTeori(){
		 Intent intent = new Intent(com.nicklase.bilteori.gui.MainActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
   	 startActivity(intent);
	}

	//Fyller actionbar i layout
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	//Hva som skjer n�r du velger settings p� actionbar
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch (item.getItemId()){
		case R.id.action_settings:
			settings();
			break;
			
		case R.id.action_examOne:
			startTeori();
			break;


		}
		return false;
		//super.onOptionsItemSelected(item);
		
	}

}
