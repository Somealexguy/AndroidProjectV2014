package com.nicklase.bilteori.gui;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.logic.CheckboxChecked;
import com.nicklase.bilteori.logic.NotificationExam;
import com.nicklase.bilteori.logic.SoundChannel;
import com.nicklase.bilteori.logic.Vibration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


//ved å extende PreferenceActivity blir innstillingene lagret automatisk
public class SettingsActivity extends PreferenceActivity{
	private CheckBoxPreference prefVib;
	private  CheckBoxPreference prefLyden;

             @Override
             public void onCreate(Bundle savedInstanceState) 
             {
                     super.onCreate(savedInstanceState);
                     addPreferencesFromResource(com.nicklase.bilteori.R.xml.user_settings);
           
                     if(CheckboxChecked.firstTime==1){
			              prefVib = (CheckBoxPreference)findPreference("prefViberasjon");
			              prefVib.setChecked(true);
			              prefLyden = (CheckBoxPreference)findPreference("prefLyd");
			              prefLyden.setChecked(true);
			              CheckboxChecked.firstTime = 0;
              
                     }
			
                     final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                     OnPreferenceClickListener p=   new OnPreferenceClickListener() {
                         
                    	 
                    	 public boolean onPreferenceClick(Preference preference) {
                    		 
                    		 if(sharedPrefs.getBoolean("prefViberasjon", true)){
                    		
                    			  Vibration.canVibrate=true;
                    		 }else{
                   		
                    					 Vibration.canVibrate=false;
                    		 }         
                    		 
                    		 if(sharedPrefs.getBoolean("prefLyd", true)){
                   			  SoundChannel.canPlaySound=true;
                   			  
                   		 }
                   		 else{
                   			 SoundChannel.canPlaySound=false;
              
                   		 }
                        	  
                        	  return false;
                              //open browser or intent here
                          }
                      };
                   
                     Preference myPref = (Preference) findPreference("prefViberasjon");
                     myPref.setOnPreferenceClickListener(p);
                     Preference myPrefSound = (Preference) findPreference("prefLyd");
                     myPrefSound.setOnPreferenceClickListener(p);

             }
        
             
         	
        /// <summary>
     	///   Starts the exam activity.
     	/// </summary>
       	private void changeToExamActivity(){
       		 Intent intent = new Intent(com.nicklase.bilteori.gui.SettingsActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
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
       			
       		case R.id.action_examOne:
       			changeToExamActivity();
       			break;


       		}
       		return false;
       		//super.onOptionsItemSelected(item);
       		
       	}
} 