package com.nicklase.bilteori.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.logic.PullParser;
import com.nicklase.bilteori.logic.Question;
import com.nicklase.bilteori.util.SystemUiHider;

public class ExamOneActivity extends Activity implements IExam {
	
	private static List<Question> allQuestions = new ArrayList<Question>();
	 private static List<Question> questions = new ArrayList<Question>();
	 //bruker hash table isteden for array for å vise at vi kan bruke det.
	 //søking i en hashtable gjøres også i konstant tid.
	private  Hashtable<Integer,String> userAnswers = new Hashtable<Integer, String>();
    private TextView timeLeft = null;
	private static int questionIndex=0;
	private long minutesUntilFinished=120;
	private	long secondsUntilFinished=minutesUntilFinished*60;
	private long millisUntilFinished=secondsUntilFinished*1000;
	private ExamTimer timer =null;
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
			//skriv til fil 
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
		
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
			newExam();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);                
         setContentView(R.layout.activity_exam_one);
         newExam();
 }
	/// <summary>
    /// Makes a new exam.
    /// </summary>
	private void newExam(){
		
		if(questions.size()<1){
			
		randomizeQuestions();
		getQuestionList();
		randomizeAlternatives();
		 timer = new ExamTimer(millisUntilFinished,1000);
		 timer.start();
		}
		timeLeft=(TextView) findViewById(R.id.textCountdownTime);
		setUpButtons();
		setQuestion();
		createRadioButton(); 
		
	}
	/// <summary>
    /// sets up the buttons
    /// </summary>
	
	public void setUpButtons(){
		final Button btnPrev=(Button) findViewById(R.id.btnPrev);
		final Button btnNext=(Button) findViewById(R.id.btnNext);
		Button btnCommit=(Button) findViewById(R.id.btnCommit);
		btnPrev.setText("Prev");
		btnNext.setText("Next");
		if(questionIndex<1){
			btnPrev.setVisibility(View.INVISIBLE);
			}
		updateProgress();
		
		btnPrev.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(questionIndex>1){
					registerUserAnswer();
					questionIndex--;
					setQuestion();
					createRadioButton();
					updateProgress();
				}else if(questionIndex==1){
					btnPrev.setVisibility(View.INVISIBLE);
					questionIndex--;
					registerUserAnswer();
					setQuestion();
					createRadioButton();
					updateProgress();
					
				}
				btnNext.setVisibility(View.VISIBLE);
			}
		}); 
		btnCommit.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				deliverResult();

			}
		}); 
		
		
		
	
		btnNext.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				int secondLastQuestion=questions.size()-2;
				if(questionIndex<secondLastQuestion){
						registerUserAnswer();
						questionIndex++;
						setQuestion();
						createRadioButton();
						updateProgress();
					}else if(questionIndex==secondLastQuestion){
						btnNext.setVisibility(View.INVISIBLE);
						registerUserAnswer();
						questionIndex++;
						setQuestion();
						createRadioButton();
						updateProgress();
					}
				
				btnPrev.setVisibility(View.VISIBLE);
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
			// I did this with code to show how to add colors dynamic
			rb[i].setTextColor(Color.parseColor("#FFFFFF"));
	        buttonGroup.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
	        
	        ((RadioButton) buttonGroup.getChildAt(i)).setText(questions.get(questionIndex).getAlternatives().get(i).toString());
	    }
	
	}
	
	 /// <summary>
    /// Sets the question text in textview textViewFormulation and if there is an image it will be visible.
    /// </summary>
 public void setQuestion(){
  TextView formulationTextView = (TextView) findViewById(R.id.textViewFormulation); 
  ImageView questionImageView =(ImageView) findViewById(R.id.imageView_questions);
  formulationTextView.setText(questions.get(questionIndex).getFormulation());
  
  if(questions.get(questionIndex).getImage()!=null){
	  questionImageView.setVisibility(View.VISIBLE);
   questionImageView.setImageResource(setImageResource());
  }else{
   questionImageView.setVisibility(View.GONE);
  } 
  
 }
 /// <summary>
 /// Sets the  image resource path.
 /// </summary>
 public  int setImageResource(){
   
   String uri = questions.get(questionIndex).getImage().trim();
   int imageResource=0;
   try{
   imageResource = this.getResources().getIdentifier(uri, "drawable", getPackageName());
   }catch( Resources.NotFoundException error){
    System.out.println(error);
    //write the exception to file.
   }
   
  return  imageResource;
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
	public void deliverResult(){
		registerUserAnswer();
		Intent intent = new Intent(ExamOneActivity.this, com.nicklase.bilteori.gui.ResultActivity.class);
		String[][] arrays = convertResultToArray();
		
		Bundle bundle=new Bundle(); 
		bundle.putStringArray("questionsArray", arrays[0]);
		bundle.putStringArray("userAnswerArray", arrays[1]);
		bundle.putStringArray("questionsAnswerArray", arrays[2]);
		
         intent.putExtras(bundle);
         startActivity(intent);
         finish();

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
	/// <summary>
    /// A method that makes a new timer from examTimer class
    /// </summary>


/// <summary>
/// A inner class which extends CountDownTimer
/// </summary>
	public class ExamTimer extends CountDownTimer {
		public ExamTimer(long millisInFuture, long countDownInterval) {
        	
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
        	deliverResult();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
        	double secondsUntilFinished = (millisUntilFinished/1000)%60;
        	
        	double minutsUntilFinished = (millisUntilFinished/1000)/60;
        	
        	
        	timeLeft.setText("Tid igjen: " + (int) minutsUntilFinished + " minutter " + (int) secondsUntilFinished  + " sekunder");
        }
        

    };
    @Override
    public void finish() {
    	// TODO Auto-generated method stub
    	super.finish();
    	timer.cancel();
    	Log.w("myApp", "Du lukket eksamen.");
    	userAnswers.clear();
    	allQuestions.clear();
		questions.clear();
		questionIndex=0;
    }
    
   
}
