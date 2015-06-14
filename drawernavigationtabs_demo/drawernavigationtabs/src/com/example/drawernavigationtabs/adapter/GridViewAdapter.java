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
import android.widget.ImageView;
import android.widget.TextView;
 
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.data.Griditem;
import com.example.drawernavigationtabs.data.NavDrawerItem;
 
public class GridViewAdapter extends ArrayAdapter<Griditem> {
	
	private Context context;
	private ArrayList<Griditem> gridValues;
	LayoutInflater inflater;
	
	public GridViewAdapter(Context context, int textViewResourceId,ArrayList<Griditem> gridItems) {
		
		super(context, textViewResourceId, gridItems);
		this.context = context;
		this.gridValues = gridItems;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.grid_item, null);
		// get layout from mobile.xml
		convertView = inflater.inflate(R.layout.grid_item, null);
		// set image based on selected text
		//ImageView imageView = (ImageView) convertView	.findViewById(R.id.grid_item_image);
		TextView labelView = (TextView) convertView.findViewById(R.id.label);

		// String mobile = mobileValues[position];
		Griditem item = gridValues.get(position);
		Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		System.out.println("roopgundeep size: width= "+height+"and width="+width);
		
		//imageView.setImageResource(item.getImageId());
		labelView.setText(item.getName());
		//imageView.setVisibility(View.GONE);
		//System.out.println("roopgundeep size: ImageView width= "+imageView.getWidth());
		return convertView;
	}
 
	@Override
	public int getCount() {
		return gridValues.size();
	}
 
	@Override
	public Griditem getItem(int location) {
		return gridValues.get(location);
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}
	
}