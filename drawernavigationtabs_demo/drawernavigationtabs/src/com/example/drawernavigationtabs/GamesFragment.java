package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.CustomArrayAdapter;
import com.example.drawernavigationtabs.Menu;

import java.util.ArrayList;
import java.util.List;

import com.example.drawernavigationtabs.R;
import android.support.v4.app.ListFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("NewApi")
public class GamesFragment extends ListFragment {

	public List<Menu> menuItems= new ArrayList<Menu>();
	public ArrayList<Menu> initialItems= new ArrayList<Menu>();
	private ListView listView;
    private CustomArrayAdapter mAdapter;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_feeds, container, false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		mAdapter = new CustomArrayAdapter(getActivity(), android.R.id.list, initialItems);
		listView.setAdapter(mAdapter);
		
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		for(int i=0;i<20;i++){
			Menu object = new Menu();
			object.setTitle("News Title");
			object.setType("NEWS");
			object.setPubdate("12 May 2013");
			object.setTimestamp("1");
			//String imageUrl="http://upload.wikimedia.org/wikipedia/en/5/5a/Emeli-Sande-Our-Version-Of-Events.jpg";
			//object.setImageUrl(imageUrl);
	    	object.setDescription("this is the small description of the news");
	    	initialItems.add(object);
		}
		
	}
	
}
