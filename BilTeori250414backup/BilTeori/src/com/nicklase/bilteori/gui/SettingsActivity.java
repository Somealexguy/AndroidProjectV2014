package com.nicklase.bilteori.gui;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.logic.CheckboxChecked;
import com.nicklase.bilteori.logic.Constant;
import com.nicklase.bilteori.logic.Email;
import com.nicklase.bilteori.logic.FileWriter;
import com.nicklase.bilteori.logic.NotificationExam;
import com.nicklase.bilteori.logic.SoundChannel;
import com.nicklase.bilteori.logic.Vibration;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.MailTo;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.TwoStatePreference;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;


//ved å extende PreferenceActivity blir innstillingene lagret automatisk

@SuppressLint("ValidFragment")
public class SettingsActivity extends FragmentActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// Display the fragment as the main content.
		android.app.FragmentManager mFragmentManager = getFragmentManager();
		android.app.FragmentTransaction mFragmentTransaction = mFragmentManager
				.beginTransaction();
		SettingsFragment mPrefsFragment = new SettingsFragment(getApplicationContext());
		mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
		mFragmentTransaction.commit();
	}




	@SuppressLint("ValidFragment")
	public static class SettingsFragment extends PreferenceFragment 
	{
		private Context context;
		private CheckBoxPreference prefVib;
		private CheckBoxPreference prefLyden;
		private FileWriter errorWriter= new FileWriter(Constant.WRITE_ERROR);
		public SettingsFragment (Context context){
			this.context=context;
		}
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
			final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.context);
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
			myPref.setOnPreferenceClickListener((OnPreferenceClickListener) p);

			Preference myPrefSound = (Preference) findPreference("prefLyd");
			myPrefSound.setOnPreferenceClickListener(p);
			Preference sendAnbefal = (Preference) findPreference("sendAnbefalMail");
			sendAnbefal.setOnPreferenceClickListener (new OnPreferenceClickListener() {

				public boolean onPreferenceClick(Preference preference) {
					String subject = "Øv deg til klasse B sertifikatet, med bilteoriappen!";
					String message="Hei, jeg anbefaler at du laster ned bilteori appen på http://www.googleplay.com/bilteoriappen."+"\n"+ "Denne applikasjonen har hjulpet meg å bestå bilteorien.";
					Email email = new Email(subject,message);
					sendEmail(email);
					return true; 
				}

			}
					);
		}
		/// <summary>
		///   Opens an email client of the users choice.
		/// </summary>
		private void sendEmail(Email email){
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, email.getSubject());
			emailIntent.putExtra(Intent.EXTRA_TEXT, email.getTextMessage());

			emailIntent.setType("message/rfc822");
			try {
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			} catch (ActivityNotFoundException e) {
				errorWriter.saveDataToFile(e.toString(), context);
				Toast.makeText(this.context, "Applikasjonen finner ikke mailklienten.", Toast.LENGTH_SHORT).show();
			}
		}
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