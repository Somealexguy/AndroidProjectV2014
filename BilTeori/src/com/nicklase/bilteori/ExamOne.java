package com.nicklase.bilteori;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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

import com.nicklase.bilteori.R.id;
import com.nicklase.bilteori.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ExamOne extends Activity implements IExam {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;
	private static List<Question> allQuestions = new ArrayList<Question>();
	 private static List<Question> questions = new ArrayList<Question>();
	 //bruker hash table isteden for array for å vise at vi kan bruke det.
	 //søking i en hashtable gjøres også i konstant tid.
	 Hashtable<Integer,String> userAnswers = new Hashtable<Integer, String>();
	 private static int questionIndex=0;
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

	/// <summary>
    /// Gets the filestream from the input file.
    /// </summary>
	private InputStream getFileStream(String file){
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
	/// <summary>
    /// This is run when the application is created.
    /// </summary>
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		InputStream instream=null;
		instream=getFileStream("questions.xml");
		PullParser parser = new PullParser();
		if(allQuestions.isEmpty()){
			try {
				Log.w("myApp", "Start parse");
				allQuestions=parser.parse(instream);
				Log.w("myApp","End parse");
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

		
		findViewById(R.id.dummy_button).setOnTouchListener(
				mDelayHideTouchListener);
		
	}
	/// <summary>
    // Trigger the initial hide() shortly after the activity has been
	// created, to briefly hint to the user that UI controls
	// are available.
    /// </summary>

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		newExam();		
		delayedHide(100);
	}
	/// <summary>
    /// Makes a new exam.
    /// </summary>
	private void newExam(){
		userAnswers.clear();
		questions.clear();
		questionIndex=0;
		randomizeQuestions();
		getQuestionList();
		randomizeAlternatives();
		setUpButtons();
		setQuestion();
		createRadioButton(); 
	}
	/// <summary>
    /// sets up the buttons
    /// </summary>
	
	public void setUpButtons(){
		Button btnPrev=(Button) findViewById(R.id.btnPrev);
		Button btnNext=(Button) findViewById(R.id.btnNext);
		Button btnCommit=(Button) findViewById(R.id.btnCommit);
		btnPrev.setText("Prev");
		btnNext.setText("Next");
		
		updateProgress();
		
		btnPrev.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(questionIndex>0){
					registerUserAnswer();
					questionIndex--;
					setQuestion();
					createRadioButton();
					updateProgress();
					
				}
			}
		}); 
		btnCommit.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				sendResult();
//				updateProgress();
			}
		}); 
		
		
		
	
		btnNext.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(questionIndex<questions.size()-1){
						registerUserAnswer();
						questionIndex++;
						setQuestion();
						createRadioButton();
						updateProgress();
						
					}else{
						registerUserAnswer();
						sendResult();
					}
			}
		});
		
		
		
	}
	/// <summary>
    /// Creates the buttons
    /// </summary>
	public void createRadioButton() {
		int numberOfAlternatives=questions.get(questionIndex).getAlternatives().size();
		RadioGroup buttonGroup = (RadioGroup) findViewById(R.id.fullscreen_content);
		removeRadioButton();
		RadioButton[] rb = new RadioButton[numberOfAlternatives];
		 
		for (int i = 0; i < numberOfAlternatives; i++) {
			rb[i]  = new RadioButton(this);
	        buttonGroup.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
	        
	        ((RadioButton) buttonGroup.getChildAt(i)).setText(questions.get(questionIndex).getAlternatives().get(i).toString());
	    }
	
	}
	
	/// <summary>
    /// Sets the question text in textview textViewFormulation.
    /// </summary>
	public void setQuestion(){
		TextView formulationTextView = (TextView) findViewById(R.id.textViewFormulation);	
		formulationTextView.setText(questions.get(questionIndex).getFormulation());
		
	}
	
	
	/// <summary>
    /// Removes the radiobuttons from the radiogroup.
    /// </summary>
	public void removeRadioButton(){
		RadioGroup buttonGroup = (RadioGroup) findViewById(R.id.fullscreen_content);
		buttonGroup.removeAllViews();
	}
	/// <summary>
    /// Updates the progress bar
    /// </summary>
	public void updateProgress(){
		TextView q = (TextView) findViewById(R.id.textViewQuestionCount);
		q.setText(questionIndex+1+"/"+questions.size());
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
	/// <summary>
    /// Shuffles the questions.
    /// </summary>
	public void randomizeQuestions(){
		long seed = System.nanoTime();
		Collections.shuffle(allQuestions,new Random(seed));
	}
	/// <summary>
    /// Shuffles the Alternatives.
    /// </summary>	
	public void randomizeAlternatives(){
		long seed = System.nanoTime();
		for(int i=0;i<questions.size();i++){
		Collections.shuffle(questions.get(i).getAlternatives(),new Random(seed));
		}
	}
	/// <summary>
    /// Sets the questionslist to 45 questions
    /// </summary>
	public void getQuestionList(){
		for(int i=0; i<45;i++){
		questions.add(allQuestions.get(i));
		}
	}
	
	/// <summary>
    /// Registrers the user answer in the hashtable
    /// </summary>
	public void registerUserAnswer(){
		
		RadioGroup buttonGroup = (RadioGroup) findViewById(R.id.fullscreen_content);
		TextView checkAnswer = (TextView) findViewById(R.id.checkAnswer);
		String rightAnswer=questions.get(questionIndex).getRightAnswer();
		int selectedButton=buttonGroup.getCheckedRadioButtonId();
		if(selectedButton>-1){
		RadioButton radioButton = (RadioButton) findViewById(selectedButton);
			String selectedAnswer = (String) radioButton.getText();
			userAnswers.put(questionIndex,selectedAnswer);
			
			/* Debugg logikk */
			if(rightAnswer.equals(selectedAnswer) && selectedAnswer != null){
				checkAnswer.setText("Du svarte riktig");
				
			}else{
				checkAnswer.setText("Du svarte feil");
			}
			buttonGroup.clearCheck();
		}else{
			checkAnswer.setText("Du svarte ikke noe");
			userAnswers.put(questionIndex," ");
			buttonGroup.clearCheck();
		}
		/* Slutt på debugg logikk */
	}
	
	/// <summary>
    /// Sends the result to ResultActivity.
    /// </summary>
	public void sendResult(){
		Intent intent = new Intent(ExamOne.this, com.nicklase.bilteori.ResultActivity.class);
		String[][] arrays = convertResultToArray();
		
		Bundle bundle=new Bundle(); 
		bundle.putStringArray("questionsArray", arrays[0]);
		bundle.putStringArray("userAnswerArray", arrays[1]);
		bundle.putStringArray("questionsAnswerArray", arrays[2]);
		
         intent.putExtras(bundle);
         startActivity(intent);

}
	/// <summary>
    /// converts the result to a multidimensonal string array
    /// </summary>
	private String[][] convertResultToArray(){
		String [][] result = new String[3][questions.size()];
		String[] questionsToSend = new String[questions.size()];
		String[] userAnswersToSend = new String[questions.size()];
		String[] answersToSend = new String[questions.size()];
		
		for(int i =0;i<questions.size();i++){
			questionsToSend[i]=questions.get(i).getFormulation().toString();
			answersToSend[i]=questions.get(i).getRightAnswer().toString();
			userAnswersToSend[i]=userAnswers.get(i);
		}
		
		result[0]=questionsToSend;
		result[1]=userAnswersToSend;
		result[2]=answersToSend;
		
		return result;
	}
}
