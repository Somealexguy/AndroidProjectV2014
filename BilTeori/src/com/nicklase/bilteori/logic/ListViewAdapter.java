package com.nicklase.bilteori.logic;
import java.util.ArrayList;
import java.util.HashMap;

import com.nicklase.bilteori.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class ListViewAdapter extends BaseAdapter {

	 public ArrayList<HashMap> list;
	    Activity activity;
	 
	    public ListViewAdapter(Activity activity, ArrayList<HashMap> list) {
	        super();
	        this.activity = activity;
	        this.list = list;
	    }
	 
	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return list.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return list.get(position);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return 0;
	    }
	 
	    private class ViewHolder {
	    	   TextView txtForm;
	    	   TextView txtFirst;
	           TextView txtSecond;
	          
	      }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	 
	        // TODO Auto-generated method stub
	                ViewHolder holder;
	                LayoutInflater inflater =  activity.getLayoutInflater();
	 
	                if (convertView == null)
	                {
	                    convertView = inflater.inflate(R.layout.result_list_item, null);
	                    holder = new ViewHolder();
	                   
	                    holder.txtForm = (TextView) convertView.findViewById(R.id.formulationResult);
	                    holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
	                    holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
	                   
	                    
	                    convertView.setTag(holder);
	                }
	                else
	                {
	                    holder = (ViewHolder) convertView.getTag();
	                }
	 
	                HashMap map = list.get(position);
	                holder.txtForm.setText((CharSequence) map.get("form"));
	                holder.txtFirst.setText((CharSequence) map.get("first"));
	                holder.txtSecond.setText((CharSequence) map.get("second"));
	               
	 
	            return convertView;
	    }
}
