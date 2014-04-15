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
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class StatisticsActivity extends Activity {
private ArrayList<Result> allStatistics = new ArrayList<Result>(); 
private ArrayList<HashMap<String, String>> list;
private ReadFromStatisticsFile task=null;
private StatisticsListViewAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new StatisticsFragment()).commit();
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		ListView listview = (ListView) findViewById(R.id.listViewEachResult);
        adapter = new StatisticsListViewAdapter(this, list);
        listview.setAdapter(adapter);
	}
private void setUp(){
	Button delete = (Button) findViewById(R.id.btnDeleteData);
	delete.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new DeleteStatisticsFile().execute();
		}
	});
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
	             
	             newReader.deleteStatistics();
	             return response;
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
				   
				  String[] eachResult= theResult[i].split("�");
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
	  	    	temp1.put(Constant.FIRST_COLUMN, "Fors�k: ");
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
