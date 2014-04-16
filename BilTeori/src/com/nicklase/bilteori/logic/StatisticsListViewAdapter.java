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
 
public class StatisticsListViewAdapter extends BaseAdapter {

	 public ArrayList<HashMap<String,String>> list;
	    Activity activity;
	 
	    public StatisticsListViewAdapter(Activity activity, ArrayList<HashMap<String,String>> list) {
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
	    	   TextView txtFirst;
	    	   TextView txtSecond;
	           TextView txtThird;
	          
	      }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	 
	        // TODO Auto-generated method stub
	                ViewHolder holder;
	                LayoutInflater inflater =  activity.getLayoutInflater();
	 
	                if (convertView == null)
	                {
	                    convertView = inflater.inflate(R.layout.one_result_item, null);
	                    holder = new ViewHolder();
	                   
	                    holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstColumn);
	                    holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondColumn);
	                    holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdColumn);
	                   
	                    
	                    convertView.setTag(holder);
	                }
	                else
	                {
	                    holder = (ViewHolder) convertView.getTag();
	                }
	 
	                HashMap map = list.get(position);
	                holder.txtFirst.setText((CharSequence) map.get("first"));
	                holder.txtSecond.setText((CharSequence) map.get("second"));
	                holder.txtThird.setText((CharSequence) map.get("third"));
	               
	 
	            return convertView;
	    }
}
