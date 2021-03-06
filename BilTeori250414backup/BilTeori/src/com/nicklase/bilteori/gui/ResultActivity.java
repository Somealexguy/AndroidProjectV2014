package com.nicklase.bilteori.gui;

import java.util.ArrayList;
import java.util.HashMap;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.R.id;
import com.nicklase.bilteori.logic.Constant;
import com.nicklase.bilteori.logic.FileWriter;
import com.nicklase.bilteori.logic.ListViewAdapter;
import com.nicklase.bilteori.logic.SoundChannel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity{
	private String[] questionAnswerArray;
	private String[] questionsArray;
	private String[] userAnswerArray;
	private ArrayList<HashMap> list;
	private FileWriter statWriter= new FileWriter(Constant.WRITE_STATISTICS);
	private Context context=null;
	private int points =0;
	private String timeUsed;
	//this is set to 1 so you will just have to get 1 point to play victory sound.
	private int pointsToPass=1;
	private SoundChannel channel;

	/// <summary>
	/// Run when activity is created.
	/// </summary>
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_result);
		Bundle b = getIntent().getExtras();

		if (b != null) {
			questionsArray = b.getStringArray("questionsArray");
			userAnswerArray=b.getStringArray("userAnswerArray");;
			questionAnswerArray = b.getStringArray("questionsAnswerArray");
			timeUsed=(String) b.getCharSequence("tid");
		}
		ListView listview = (ListView) findViewById(R.id.resultList);
		populateList();
		ListViewAdapter adapter = new ListViewAdapter(this, list);
		listview.setAdapter(adapter); 
	}
	/// <summary>
	/// Run after create.
	/// </summary>
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		context=getApplicationContext();
		channel = new SoundChannel(context);
		checkAnswer();
		if(pointsToPass<=points){
			playPassingSound();
			Toast.makeText(context, "Gratulerer du bestod teoripr�ven!", Toast.LENGTH_LONG).show();
		}
		String result=points+"�"+timeUsed+";";
		statWriter.saveDataToFile(result,context);
	}
	/// <summary>
	/// will play the victorysound.
	/// </summary>
	private void playPassingSound(){
		channel.playFinishSound();
	}
	/// <summary>
	/// Checks the answers give in the exam.
	/// </summary>
	private void checkAnswer(){

		for(int i =0; i<questionAnswerArray.length;i++){
			String userAnswer=userAnswerArray[i];
			if(userAnswer!= "" && userAnswer!=null){
				if(userAnswer.equals(questionAnswerArray[i])){
					points++;
				}
			}

		} 

		TextView p = (TextView) findViewById(id.textPoeng);
		p.setText("Du fikk:"+points+"poeng.");		 


	}
	/// <summary>
	///   Inflates the action bar in the layout.
	/// </summary>
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	/// <summary>
	/// Populate the list for the listview .
	/// </summary>
	private void populateList() {

		list = new ArrayList<HashMap>();

		HashMap temp1 = new HashMap();
		temp1.put(Constant.FORM_COLUMN, "Sp�rsm�l");
		temp1.put(Constant.FIRST_COLUMN,"Riktig svar:");
		temp1.put(Constant.SECOND_COLUMN, "Ditt svar:");
		list.add(temp1);


		for(int i=0;i<questionsArray.length;i++){
			HashMap temp = new HashMap();
			temp.put(Constant.FORM_COLUMN, questionsArray[i]);
			temp.put(Constant.FIRST_COLUMN,questionAnswerArray[i]);
			temp.put(Constant.SECOND_COLUMN, userAnswerArray[i]);
			list.add(temp);
		} 
	}
	//		<summary>
	//		Run when back button is pressed.
	//		</summary>
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		//finishes the activity.
		finish();
	}
	//		<summary>
	//		Run when activity is closed.
	//		</summary>
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		points=0;
		channel.closeSoundChannel();
		Log.w("myApp","Du lukket resultat");
	}
}
