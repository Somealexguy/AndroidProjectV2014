package com.nicklase.bilteori.gui;

import java.util.ArrayList;
import java.util.HashMap;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.R.id;
import com.nicklase.bilteori.logic.Constant;
import com.nicklase.bilteori.logic.FileWriter;
import com.nicklase.bilteori.logic.ListViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class ResultActivity extends Activity{
	private String[] questionAnswerArray;
	private String[] questionsArray;
	private String[] userAnswerArray;
	private ArrayList<HashMap> list;
	private final String FIRST_COLUMN ="first";
	private final String SECOND_COLUMN ="second";
	private final String FORM_COLUMN ="form";
	private FileWriter statWriter= new FileWriter(Constant.WRITE_STATISTICS);
	private Context context=null;
	private int poeng =0;
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
		checkAnswer();
		String sum=poeng+"";
		statWriter.saveDataToFile(sum,context);
	}
	/// <summary>
    /// Checks the answers give in the exam.
    /// </summary>
	private void checkAnswer(){
	
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
	/// <summary>
    /// Adds the meny and its items.
    /// </summary>
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 
	  /// <summary>
	    /// Populate the list for the listview .
	    /// </summary>
	    private void populateList() {
	 
	        list = new ArrayList<HashMap>();
	 
	    	HashMap temp1 = new HashMap();
	    	temp1.put(FORM_COLUMN, "Spørsmål");
            temp1.put(FIRST_COLUMN,"Riktig svar:");
            temp1.put(SECOND_COLUMN, "Ditt svar:");
            list.add(temp1);
            
           
	        for(int i=0;i<questionsArray.length;i++){
	        	HashMap temp = new HashMap();
	        	temp.put(FORM_COLUMN, questionsArray[i]);
	            temp.put(FIRST_COLUMN,questionAnswerArray[i]);
	            temp.put(SECOND_COLUMN, userAnswerArray[i]);
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
	    	poeng=0;
	    	Log.w("myApp","Du lukket resultat");
	    }
}
