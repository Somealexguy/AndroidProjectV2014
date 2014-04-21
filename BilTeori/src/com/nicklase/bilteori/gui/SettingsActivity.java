package com.nicklase.bilteori.gui;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.logic.NotificationExam;
import com.nicklase.bilteori.logic.Sounds;
import com.nicklase.bilteori.logic.Vibration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


//ved å extende PreferenceActivity blir innstillingene lagret automatisk
public class SettingsActivity extends PreferenceActivity{
             
             @Override
             public void onCreate(Bundle savedInstanceState) 
             {
                     super.onCreate(savedInstanceState);
                     addPreferencesFromResource(com.nicklase.bilteori.R.xml.user_settings);
                     
			
                     final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                     OnPreferenceClickListener p=   new OnPreferenceClickListener() {
                         
                    	 
                    	 public boolean onPreferenceClick(Preference preference) {
                    		 
                    		 if(sharedPrefs.getBoolean("prefViberasjon", true)){
                    		
                    			  Vibration.canVibrate=true;
                    		 }else{
                   		
                    					 Vibration.canVibrate=false;
                    		 }         
                    		 
                    		 if(sharedPrefs.getBoolean("prefLyd", true)){
                   			  Sounds.canPlaySound=true;
                   			  
                   		 }
                   		 else{
                   			 Sounds.canPlaySound=false;
              
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
        
             
         	
       	
       	private void startTeori(){
       		 Intent intent = new Intent(com.nicklase.bilteori.gui.SettingsActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
          	 startActivity(intent);
       		}

       	//Fyller actionbar i layout
       	@Override
       	public boolean onCreateOptionsMenu(Menu menu) {
       		// Inflate the menu; this adds items to the action bar if it is present.
       		getMenuInflater().inflate(R.menu.main, menu);
       		return true;
       	}
       	
       	
       	//Hva som skjer når du velger settings på actionbar
       	@Override
       	public boolean onOptionsItemSelected(MenuItem item){
       		
       		switch (item.getItemId()){
       			
       		case R.id.action_examOne:
       			startTeori();
       			break;


       		}
       		return false;
       		//super.onOptionsItemSelected(item);
       		
       	}
} 