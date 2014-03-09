package com.nicklase.bilteori;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nicklase.bilteori.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ExamOne extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;
	 private static List<Question> questions = new ArrayList<Question>();
	
	 public int questionIndex=0;
	 
	// private static List<Question> newQuestionList = new ArrayList<Question>();

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	private InputStream getStream(String file){
		InputStream instream =null;
		AssetManager assetManager = getAssets();
		try {
			 instream = assetManager.open(file.trim());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return instream;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		InputStream instream=null;
		try {
			instream = this.getAssets().open("questions.xml");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.w("myApp","fikk ikke lest fil");
		}
		PullParser parser = new PullParser();
		
		try {
			Log.w("myApp", "Start parse");
			questions=parser.parse(instream);
			Log.w("myApp","End parse");
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		setContentView(R.layout.activity_exam_one);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);
		
		
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.dummy_button).setOnTouchListener(
				mDelayHideTouchListener);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		long seed = System.nanoTime();
		Collections.shuffle(questions,new Random(seed));
	
		setUpButtons();
		RadioGroup buttonGroup = (RadioGroup) findViewById(R.id.fullscreen_content);
		TextView formulationTextView = (TextView) findViewById(R.id.textViewFormulation);
		
		formulationTextView.setText(questions.get(questionIndex).getFormulation());
		for (int i = 0; i < questions.get(questionIndex).getAlternatives().size(); i++) {
	        ((RadioButton) buttonGroup.getChildAt(i)).setText(questions.get(questionIndex).getAlternatives().get(i).toString());
	    }
		
		delayedHide(100);
	}
	
	private void setUpButtons(){
		
		Button btnPrev=(Button) findViewById(R.id.btnPrev);
		Button btnNext=(Button) findViewById(R.id.btnNext);
		btnPrev.setText("Prev");
		btnNext.setText("Next");
		btnPrev.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(questionIndex>0){
					RadioGroup buttonGroup = (RadioGroup) findViewById(R.id.fullscreen_content);
					TextView formulationTextView = (TextView) findViewById(R.id.textViewFormulation);
					
					questionIndex--;
					formulationTextView.setText(questions.get(questionIndex).getFormulation());
					for (int i = 0; i < questions.get(questionIndex).getAlternatives().size(); i++) {
				        ((RadioButton) buttonGroup.getChildAt(i)).setText(questions.get(questionIndex).getAlternatives().get(i).toString());
				    }
					
				}
			}
		}); 
		
		
		
	
		btnNext.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(questionIndex<questions.size()){
						questionIndex++;
						RadioGroup buttonGroup = (RadioGroup) findViewById(R.id.fullscreen_content);
						TextView formulationTextView = (TextView) findViewById(R.id.textViewFormulation);
						
						formulationTextView.setText(questions.get(questionIndex).getFormulation());
						for (int i = 0; i < questions.get(questionIndex).getAlternatives().size(); i++) {
					        ((RadioButton) buttonGroup.getChildAt(i)).setText(questions.get(questionIndex).getAlternatives().get(i).toString());
					    }
						
					}
			}
		});
		
		
		
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	private static void RandomQuestions(){
		
		
		
	}

}
