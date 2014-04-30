package com.nicklase.bilteori.gui;

import java.util.ArrayList;
import java.util.HashMap;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.R.id;
import com.nicklase.bilteori.R.layout;
import com.nicklase.bilteori.R.menu;
import com.nicklase.bilteori.logic.Constant;
import com.nicklase.bilteori.logic.ListViewAdapter;
import com.nicklase.bilteori.logic.MyFileReader;
import com.nicklase.bilteori.logic.Result;
import com.nicklase.bilteori.logic.StatisticsListViewAdapter;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class StatisticsActivity extends Activity {
private ArrayList<Result> allStatistics = new ArrayList<Result>(); 
private ArrayList<HashMap<String, String>> list;
private ReadFromStatisticsFile task=null;
private StatisticsListViewAdapter adapter=null;
private Toast myToast;
private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new StatisticsFragment()).commit();
		}		
		myToast=new Toast(this);
	}
	/// <summary>
	///   Starts the settings activity.
	/// </summary>
	private void changeToSettingsActivtiy(){
		 Intent intent = new Intent(com.nicklase.bilteori.gui.StatisticsActivity.this, com.nicklase.bilteori.gui.SettingsActivity.class);
   	 startActivity(intent);
	}
		/// <summary>
		///   Starts the exam activity.
		/// </summary>
	private void changeToExamActivity(){
		 Intent intent = new Intent(com.nicklase.bilteori.gui.StatisticsActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
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
			changeToSettingsActivtiy();
			break;
			
		case R.id.action_examOne:
			changeToExamActivity();
			break;


		}
		return false;
		//super.onOptionsItemSelected(item);
		
	}

		@Override
		protected void onPostCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onPostCreate(savedInstanceState);
			task= new ReadFromStatisticsFile();
			task.execute();
			setUp();
			
		}
	private void setUpListItem(){
		listview = (ListView) findViewById(R.id.listViewEachResult);
        adapter = new StatisticsListViewAdapter(this, list);
        listview.setAdapter(adapter);
	}
private void setUp(){
	Button delete = (Button) findViewById(R.id.btnDeleteData);
	delete.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showAlertBox();
		}
	});
}

private void showAlertBox(){
	AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
	 
    // Setting Dialog Title
    alertDialog.setTitle("Bekreft sletting av data.");

    // Setting Dialog Message
    alertDialog.setMessage("Er du sikker på at du vil slette all brukerdata?");

    // Setting Icon to Dialog
    alertDialog.setIcon(R.drawable.forbudsskilt);

    // Setting Positive "Yes" Button
    alertDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
        	new DeleteStatisticsFile().execute();
        		allStatistics.clear();
        	 ((StatisticsListViewAdapter) listview.getAdapter()).clearList();
			 ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
			
			
        }
    });

    // Setting Negative "NO" Button
    alertDialog.setNegativeButton("Nei", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        // Write your code here to invoke NO event
        dialog.cancel();
        }
    });

    // Showing Alert Message
    alertDialog.show();
}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class StatisticsFragment extends Fragment {

			public StatisticsFragment() {
			}
	
			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
				View rootView = inflater.inflate(R.layout.fragment_statistics,
						container, false);
				return rootView;
			}
		}
	private class DeleteStatisticsFile extends AsyncTask<Void, Void, String> {
		
	   @Override
	   protected String doInBackground(Void... params) {
	         //Get All Route values
		   String response=null;
	             MyFileReader newReader= new MyFileReader();
	             if(newReader.deleteStatistics()=="ok")
	            	 response="ok";
	             
	             return response;
	   }
	   @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result=="ok"){
				myToast.makeText(getApplicationContext(), "Dataene ble slettet.", Toast.LENGTH_SHORT).show();
				
			}else{
				myToast.makeText(getApplicationContext(), "Dataene ble ikke slettet.", Toast.LENGTH_SHORT).show();
			}
		}
	}
private class ReadFromStatisticsFile extends AsyncTask<Void, Void, String> {
		String result;
	   @Override
	   protected String doInBackground(Void... params) {
	         //Get All Route values
	             MyFileReader newReader= new MyFileReader();
	             
	           result=  newReader.readStatistics();
	             
	         return result;
	
	   }
	
	   @Override
	   protected void onPostExecute(String result) {
		   String[] theResult=result.split(";");
		   if(result!=" " || result!=null){
			   for (int i = 0; i < theResult.length; i++) {
				   
				  String[] eachResult= theResult[i].split("¤");
				  if(eachResult.length==2){
					   Result r = new Result(eachResult[0],eachResult[1]);
						allStatistics.add(r);
				  }
				}
			   populateList();
			   setUpListItem();
		   }
		}
	}
	private void populateList(){
	       list = new ArrayList<HashMap<String,String>>();
	  	 
	  	    	HashMap<String, String> temp1 = new HashMap<String, String>();
	  	    	temp1.put(Constant.FIRST_COLUMN, "Forsøk: ");
	              temp1.put(Constant.SECOND_COLUMN,"Antall poeng: ");
	              temp1.put(Constant.THIRD_COLUMN, "Tid brukt:");
	              list.add(temp1);
	                   
	  	        for(int i=0;i<allStatistics.size();i++){
	  	        	HashMap temp = new HashMap();
	  	        	int id=i+1;
	  	        	temp.put(Constant.FIRST_COLUMN, id+"");
	  	            temp.put(Constant.SECOND_COLUMN, allStatistics.get(i).getPoints());
	  	            temp.put(Constant.THIRD_COLUMN, allStatistics.get(i).getTime());
	  	            list.add(temp);
	  	        } 
	}

	@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			allStatistics.clear();
			finish();
			
		}
}
