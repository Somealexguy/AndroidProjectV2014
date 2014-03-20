package com.nicklase.bilteori;

import com.nicklase.bilteori.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ResultActivity extends Activity{
	private String[] questionAnswerArray;
	private String[] questionsArray;
	private String[] userAnswerArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_result);
		Bundle b = getIntent().getExtras();
		
		  if (b != null) {
			  questionsArray = b.getStringArray("questionsArray");
				userAnswerArray=b.getStringArray("userAnswerArray");;
			  questionAnswerArray = b.getStringArray("questionsAnswerArray");
		  }
	       
		  
		  
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		checkAnswer();
	}
	
	private void checkAnswer(){
		int poeng =0;
		for(int i =0; i<questionAnswerArray.length;i++){
			String userAnswer=userAnswerArray[i];
			if(userAnswer!= "" && userAnswer!=null){
				if(userAnswer.equals(questionAnswerArray[i])){
					poeng++;
				}
			}
		
	} 
		
		TextView p = (TextView) findViewById(id.textPoeng);
		p.setText("Du fikk:"+poeng+"poeng.");		 
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
