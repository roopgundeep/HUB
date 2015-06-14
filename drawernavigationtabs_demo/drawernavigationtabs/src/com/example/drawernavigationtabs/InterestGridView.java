package com.example.drawernavigationtabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.adapter.GridViewAdapter;
import com.example.drawernavigationtabs.data.NewsFeedItem;
import com.example.drawernavigationtabs.data.Griditem;
import com.example.drawernavigationtabs.database.SqlHandler;


import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class InterestGridView  extends Fragment{
	
	public final static String TAG = InterestGridView.class.getSimpleName();
	public ArrayList<Griditem> gridItems= new ArrayList<Griditem>();
	public Integer Count=0;
	public ArrayList<String> selectedItems=new ArrayList<String>();
	public ArrayList<String> ItemsInDB=new ArrayList<String>();
	
	public ArrayList<Integer> imageIds = new ArrayList<Integer>(Arrays.asList(
			R.drawable.business, R.drawable.entertainment, R.drawable.food,
			R.drawable.music, R.drawable.technology, R.drawable.politics,
			R.drawable.travel, R.drawable.art,R.drawable.sports,R.drawable.style));
	
	public ArrayList<String> labels = new ArrayList<String>(Arrays.asList(
			"Business", "Entertainment", "Food", "Music", "Technology", "Politics",
			"Travel", "Cricket", "Sports", "Style"));
	public SqlHandler sqlHandler;
	
	public InterestGridView() {
		// TODO Auto-generated constructor stub
	}
	
	public static InterestGridView newInstance() {
	        return new InterestGridView();
	}
	 
	GridView gridView;
	static final String[] MOBILE_OS = new String[] { 
		"Android", "iOS","Windows", "Blackberry" };
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.grid, container, false);
		gridView = (GridView) rootView.findViewById(R.id.gridView1);
		gridView.setAdapter(new GridViewAdapter(getActivity(),R.layout.grid_item, gridItems));
		 
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("NewApi")
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				//if(//v.findViewById(R.id.select).getVisibility()==View.VISIBLE){
					
					/*v.findViewById(R.id.textview).setVisibility(View.GONE);
					//v.findViewById(R.id.select).setVisibility(View.GONE);
					TextView choosed= (TextView) v.findViewById(R.id.label);
					selectedItems.remove(choosed.getText().toString());					
					Count=Count-1;*/
				//}
				//else{
					
					//v.findViewById(R.id.textview).setVisibility(View.VISIBLE);
					//v.findViewById(R.id.select).setVisibility(View.VISIBLE);
					TextView choosed= (TextView) v.findViewById(R.id.label);
					selectedItems.add(choosed.getText().toString());
					Griditem item1 = new Griditem("Sub Category1", R.drawable.business, -1);
					Griditem item2 = new Griditem("Sub Category2", R.drawable.business, -1);
					gridItems.add(position+1, item1);
					gridItems.add(position+2, item2);									
					gridView.deferNotifyDataSetChanged();
					Count=Count+1;
				//}
				
				if(Count>0){				
					rootView.findViewById(R.id.choose).setVisibility(View.GONE);
					rootView.findViewById(R.id.done).setVisibility(View.VISIBLE);
				}
				else{
					rootView.findViewById(R.id.choose).setVisibility(View.VISIBLE);
					rootView.findViewById(R.id.done).setVisibility(View.GONE);
				}
				
			}
		});
		Button done = (Button) rootView.findViewById(R.id.done);

		done.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
 
			     System.out.println("roop: choose"+selectedItems.size());
			     
			     for(int i=0;i<selectedItems.size();i++){
			    	 System.out.println("roop choose: "+selectedItems.get(i));
			    	 
			    	 String value='"'+selectedItems.get(i).toString()+'"';
			    	 
			    	 String query = "INSERT INTO INTERESTS(name) " +
					    		"values ("
					      + value + ")";
				    sqlHandler.executeQuery(query);
			     }
			     getFragmentManager()
	                .beginTransaction()
	                .replace(R.id.content_frame,
	                        InterestGridView.newInstance(),
	                        InterestGridView.TAG).commit();
			  }			  
		});
		return rootView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//read from DB and check which are selected
		sqlHandler = new SqlHandler(getActivity());
		String query = "select * from INTERESTS";
		String checkQuery="SELECT COUNT(*) FROM INTERESTS";
	//	Cursor c1 = sqlHandler.selectQuery(checkQuery);
		Count = 0;
		ItemsInDB.clear();

		Cursor c2 = sqlHandler.selectQuery(query);
		if (c2 != null) {
			if (c2.moveToFirst()) {
				do{
					ItemsInDB.add(c2.getString(c2.getColumnIndex("name")));
				}while (c2.moveToNext());
			}
			
		}
		for(int i=0;i<labels.size();i++){
			
			if(ItemsInDB.contains(labels.get(i))){
				System.out.println("roop: choose: "+labels.get(i)+imageIds.get(i)+i);
			}
			else{
				Griditem item = new Griditem(labels.get(i),imageIds.get(i),i);
				gridItems.add(item);
			}
		}
	}
}
