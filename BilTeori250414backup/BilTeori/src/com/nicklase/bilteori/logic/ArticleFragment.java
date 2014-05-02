package com.nicklase.bilteori.logic;

import com.nicklase.bilteori.R;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ArticleFragment extends Fragment{	
	public final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;
	/// <summary>
	/// This is run when the application is created.
	/// </summary>
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		if(savedInstanceState != null){
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}

		return inflater.inflate(R.layout.article_view, container, false);
	}
	/// <summary>
	/// Recreates the instance when the application is resumed, after destroy.
	/// </summary>
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(ARG_POSITION, mCurrentPosition);
	}

	/// <summary>
	/// This method is run when you start the fragment.
	/// </summary>
	@Override
	public void onStart() {
		super.onStart();

		Bundle args = getArguments();
		if(args != null){

			updateArticleView(args.getInt(ARG_POSITION));
			Log.v("blah", "blah blah");

		}
		else if(mCurrentPosition != -1){
			updateArticleView(mCurrentPosition);
		}
	}


	/// <summary>
	/// Updates the article view.
	/// </summary>
	public void updateArticleView(int position){
		TextView article = (TextView) getActivity().findViewById(R.id.article);

		article.setText(BookText.Articles[position]);
		mCurrentPosition = position;		
	}


}
