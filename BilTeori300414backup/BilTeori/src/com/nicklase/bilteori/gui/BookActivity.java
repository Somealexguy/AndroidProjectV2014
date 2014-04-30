package com.nicklase.bilteori.gui;

import com.nicklase.bilteori.R;
import com.nicklase.bilteori.logic.ArticleFragment;
import com.nicklase.bilteori.logic.HeadlinesFragment;

import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class BookActivity extends FragmentActivity

	implements HeadlinesFragment.OnHeadlineSelectedListener{

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_articles);
		
		if(findViewById(R.id.fragment_container) != null){
			if(savedInstanceState != null){
				return;
			}
			
			HeadlinesFragment firstFragment = new HeadlinesFragment();
			firstFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction()
			
			.add(R.id.fragment_container, firstFragment).commit();
		}
		
	}

	@Override
	public void onArticleSelected(int position) {
		ArticleFragment articleFrag = (ArticleFragment) getSupportFragmentManager()
				.findFragmentById(R.id.article_fragment);
		
		
		//første gang vi åpner en artikkel
		if(articleFrag != null){
			articleFrag.updateArticleView(position);
		}
		
		else{
			ArticleFragment newFragment = new ArticleFragment();
			Bundle args = new Bundle();
			args.putInt(ArticleFragment.ARG_POSITION, position);
			
			newFragment.setArguments(args);
			FragmentTransaction transaction =
					getSupportFragmentManager().beginTransaction();

			
			transaction.setCustomAnimations(R.animator.slide_down, R.animator.slide_up);
			transaction.replace(R.id.fragment_container, newFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		
	}
		
	/// <summary>
	///   Starts the settings activity.
	/// </summary>
	private void changeToSettingsActivtiy(){
		 Intent intent = new Intent(com.nicklase.bilteori.gui.BookActivity.this, com.nicklase.bilteori.gui.SettingsActivity.class);
   	 startActivity(intent);
	}
	/// <summary>
	///   Starts the exam activity.
	/// </summary>
	private void changeToExamActivity(){
		 Intent intent = new Intent(com.nicklase.bilteori.gui.BookActivity.this, com.nicklase.bilteori.gui.ExamOneActivity.class);
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
}