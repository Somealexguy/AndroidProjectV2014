package com.nicklase.bilteori.logic;



import com.nicklase.bilteori.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlinesFragment extends ListFragment{

	OnHeadlineSelectedListener mCallback;
	
	public interface OnHeadlineSelectedListener{
		
		public void onArticleSelected(int position);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		int layout = android.R.layout.simple_list_item_1;
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), layout, BokTekst.Headlines));
		
	
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try{
			mCallback = (OnHeadlineSelectedListener) activity;	
		}
		catch(ClassCastException e){
			throw new ClassCastException(activity.toString() +
					" must implement OnHeadlineSelectedListener");
		}
	}



	@Override
	public void onStart() {
		
		super.onStart();
		
		if(getFragmentManager().findFragmentById(R.id.article_fragment) != null){
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	public void onListItemClick(ListView l, View v, int position, long id){
		mCallback.onArticleSelected(position);
		getListView().setItemChecked(position, true);
	}
	
}
