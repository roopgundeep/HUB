package com.example.drawernavigationtabs.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.data.Griditem;
import com.example.drawernavigationtabs.data.HomeButtonGriditem;
import com.example.drawernavigationtabs.data.NavDrawerItem;
 
public class HomeButtonGridViewAdapter extends ArrayAdapter<HomeButtonGriditem> {
	
	private Context context;
	private ArrayList<HomeButtonGriditem> gridValues;
	LayoutInflater inflater;
	
	public HomeButtonGridViewAdapter(Context context, int textViewResourceId,ArrayList<HomeButtonGriditem> gridItems) {
		
		super(context, textViewResourceId, gridItems);
		this.context = context;
		this.gridValues = gridItems;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.home_button_grid_item, null);
		
		// get layout from mobile.xml
		convertView = inflater.inflate(R.layout.home_button_grid_item, null);
		
		TextView button = (TextView) convertView.findViewById(R.id.homebutton);
		HomeButtonGriditem item = gridValues.get(position);
		System.out.println("roopgundeep-home"+item.getName());
		button.setText(item.getName());
		//button.setBackgroundResource(R.drawable.rihanna);		
		return convertView;
	}
 
	@Override
	public int getCount() {
		return gridValues.size();
	}
 
	@Override
	public HomeButtonGriditem getItem(int location) {
		return gridValues.get(location);
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}
	
}