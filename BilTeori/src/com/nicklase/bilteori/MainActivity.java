package com.nicklase.bilteori;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		setUpButtons();
	}
	
	public void setUpButtons(){
		final Button buttonBook = (Button) findViewById(R.id.btn_book);
		 buttonBook.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.MainActivity.this,com.nicklase.bilteori.BookActivity.class);
	        	 startActivity(intent);
	         }
	     });
		 final Button buttonExam = (Button) findViewById(R.id.btn_exam_one);
		 buttonExam.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.MainActivity.this, com.nicklase.bilteori.Exam_one.class);
	        	 startActivity(intent);
	         }
	     });
		 final Button buttonExamTwo = (Button) findViewById(R.id.btn_exam_two);
		 buttonExamTwo.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.MainActivity.this, com.nicklase.bilteori.Exam_two.class);
	        	 startActivity(intent);
	         }
	     });
		 final Button buttonSettings = (Button) findViewById(R.id.btn_setting);
		 buttonSettings.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.MainActivity.this, com.nicklase.bilteori.Settings.class);
	        	 startActivity(intent);
	         }
	     });
		 final Button buttonStatistics = (Button) findViewById(R.id.btn_stat);
		 buttonStatistics.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.MainActivity.this, com.nicklase.bilteori.Statistics.class);
	        	 startActivity(intent);
	         }
	     });
		 final Button buttonPicture = (Button) findViewById(R.id.btn_takePicture);
		 buttonPicture.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.MainActivity.this, com.nicklase.bilteori.Take_picture.class);
	        	 startActivity(intent);
	         }
	     });
		 final Button buttonMap = (Button) findViewById(R.id.btn_getLocation);
		 buttonMap.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	             // Perform action on click
	        	 Intent intent = new Intent(com.nicklase.bilteori.MainActivity.this, com.nicklase.bilteori.Map.class);
	        	 startActivity(intent);
	         }
	     });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
