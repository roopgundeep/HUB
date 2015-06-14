package com.example.drawernavigationtabs;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.drawernavigationtabs.adapter.NewsFeedListAdapter;
import com.example.drawernavigationtabs.adapter.GridViewAdapter;
import com.example.drawernavigationtabs.adapter.HomeButtonGridViewAdapter;
import com.example.drawernavigationtabs.adapter.OldFeedListAdapter;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.NewsFeedItem;
import com.example.drawernavigationtabs.data.HomeButtonGriditem;
import com.example.drawernavigationtabs.view.XListView;
import com.example.drawernavigationtabs.view.XListView.IXListViewListener;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends Fragment {

	public static final String TAG = Home.class.getSimpleName();
	public ListView mListView;
	public ArrayList<NewsFeedItem> feedItems =new ArrayList<NewsFeedItem>();
	private OldFeedListAdapter listAdapter;
	private Handler mHandler;
	public static String latestTimeStamp = "0";
	public ArrayList<String> listCacheName = new ArrayList<String>();
	public String FEED = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=";
	public String FEED_0 = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=0";
	GridView gridView;
	ArrayList<HomeButtonGriditem> gridItems = new ArrayList<HomeButtonGriditem>();
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	View rootView;

	public static Home newInstance() {
		return new Home();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HomeButtonGriditem button1 = new HomeButtonGriditem("News");
		HomeButtonGriditem button2 = new HomeButtonGriditem("Deals");
		gridItems.add(button1);
		gridItems.add(button2);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Drawable back = getResources().getDrawable( R.drawable.actionbar_homepage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		super.onResume();
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Drawable back = getResources().getDrawable( R.drawable.actionbar_homepage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		//getParentFragment().getActivity().getActionBar().setBackgroundDrawable(back);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Drawable back = getResources().getDrawable( R.drawable.actionbar_homepage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		
		rootView = inflater.inflate(R.layout.home_page, container, false);
		Button newButton = (Button) rootView.findViewById(R.id.newsbutton);
		Button dealsButton = (Button) rootView.findViewById(R.id.dealsbutton);
		Button moreNewButton = (Button) rootView.findViewById(R.id.moreNewsButton1);
		Button moreDealsButton = (Button) rootView.findViewById(R.id.moreDealButton1);
		newButton.setOnClickListener(new OnClickListener() {			 
			  @Override
			  public void onClick(View v) {
				  getActivity().getSupportFragmentManager()
	                .beginTransaction()
	                .replace(R.id.content_frame, NewsTabbedFragment.newInstance(), NewsTabbedFragment.TAG).commit();
			  }
		});
		moreNewButton.setOnClickListener(new OnClickListener() {			 
			  @Override
			  public void onClick(View v) {
				  getActivity().getSupportFragmentManager()
	                .beginTransaction()
	                .replace(R.id.content_frame, NewsTabbedFragment.newInstance(), NewsTabbedFragment.TAG).commit();
			  }
		});
		moreDealsButton.setOnClickListener(new OnClickListener() {			 
			  @Override
			  public void onClick(View v) {
				  getActivity().getSupportFragmentManager()
	                .beginTransaction()
	                .replace(R.id.content_frame, DealsTabbedFragment.newInstance(), DealsTabbedFragment.TAG).commit();
			  }
		});
		dealsButton.setOnClickListener(new OnClickListener() {			 
			  @Override
			  public void onClick(View v) {
				  getActivity().getSupportFragmentManager()
	                .beginTransaction()
	                .replace(R.id.content_frame, DealsTabbedFragment.newInstance(), DealsTabbedFragment.TAG).commit();
			  }
		});
		initialization();
		return rootView;
	}

	public void fill() {
		System.out.println("RoopHome:size:" + feedItems.size());

		for (int i = 0; i < feedItems.size() ; i++) {

			FeedImageView feedImageView = null;
			NetworkImageView fbFriendPic1 = null;
			NetworkImageView fbFriendPic2 = null;
			NetworkImageView fbFriendPic3 = null;
			TextView title = null;
			TextView description = null;
			TextView sourceName = null;
			NewsFeedItem item = feedItems.get(i);
			if (i == 2) {
				feedImageView = (FeedImageView) rootView.findViewById(R.id.newsFeedImage1);
				title = (TextView) rootView.findViewById(R.id.newsTitle1);
				description = (TextView) rootView.findViewById(R.id.newsDescription1);
				sourceName = (TextView) rootView.findViewById(R.id.newsSourcename1);
				fbFriendPic1 = (NetworkImageView) rootView.findViewById(R.id.news1friendPic1);
				fbFriendPic2 = (NetworkImageView) rootView.findViewById(R.id.news1friendPic2);
				fbFriendPic3 = (NetworkImageView) rootView.findViewById(R.id.news1friendPic3);
				TextView likedData = (TextView) rootView.findViewById(R.id.news1likedData);
				
				System.out.println("RoopHome:description:"+ item.getDescription());
				feedImageView.setImageUrl(item.getSourceImage(), imageLoader);
				title.setText(item.getName());
				description.setText(item.getDescription());
				sourceName.setText(item.getSourceName());
				fbFriendPic1.setImageUrl(item.getSourceImage(), imageLoader);
				fbFriendPic2.setImageUrl(item.getSourceImage(), imageLoader);
				fbFriendPic3.setImageUrl(item.getSourceImage(), imageLoader);
				likedData.setText(" also liked this");
			}
			else if (i == 1) {
				feedImageView = (FeedImageView) rootView
						.findViewById(R.id.newsFeedImage2);
				title = (TextView) rootView.findViewById(R.id.newsTitle2);
				description = (TextView) rootView.findViewById(R.id.newsDescription2);
				sourceName = (TextView) rootView.findViewById(R.id.newsSourcename2);
				fbFriendPic1 = (NetworkImageView) rootView.findViewById(R.id.news2friendPic1);
				fbFriendPic2 = (NetworkImageView) rootView.findViewById(R.id.news2friendPic2);
				fbFriendPic3 = (NetworkImageView) rootView.findViewById(R.id.news2friendPic3);
				TextView likedData = (TextView) rootView.findViewById(R.id.news2likedData);
				
				System.out.println("RoopHome:description:"+ item.getDescription());
				feedImageView.setImageUrl(item.getSourceImage(), imageLoader);
				title.setText(item.getName());
				description.setText(item.getDescription());
				sourceName.setText(item.getSourceName());
				fbFriendPic1.setImageUrl(item.getSourceImage(), imageLoader);
				fbFriendPic2.setImageUrl(item.getSourceImage(), imageLoader);
				fbFriendPic3.setImageUrl(item.getSourceImage(), imageLoader);
				likedData.setText(" also liked this");
			}
			else if (i == 0) {
				feedImageView = (FeedImageView) rootView.findViewById(R.id.dealsFeedImage1);
				title = (TextView) rootView.findViewById(R.id.dealsTitle1);
				description = (TextView) rootView.findViewById(R.id.dealsDescription1);				
				fbFriendPic1 = (NetworkImageView) rootView.findViewById(R.id.deals1friendPic1);
				fbFriendPic2 = (NetworkImageView) rootView.findViewById(R.id.deals1friendPic2);
				fbFriendPic3 = (NetworkImageView) rootView.findViewById(R.id.deals1friendPic3);
				TextView likedData = (TextView) rootView.findViewById(R.id.deals1likedData);
				
				System.out.println("RoopHome:description:"+ item.getDescription());
				feedImageView.setImageUrl(item.getSourceImage(), imageLoader);
				title.setText("Name");
				description.setText("Description");				
				fbFriendPic1.setImageUrl(item.getSourceImage(), imageLoader);
				fbFriendPic2.setImageUrl(item.getSourceImage(), imageLoader);
				fbFriendPic3.setImageUrl(item.getSourceImage(), imageLoader);
				likedData.setText(" also liked this");
			}
			else if (i == 5) {
				feedImageView = (FeedImageView) rootView.findViewById(R.id.dealsFeedImage2);
				title = (TextView) rootView.findViewById(R.id.dealsTitle2);
				description = (TextView) rootView.findViewById(R.id.dealsDescription2);				
				fbFriendPic1 = (NetworkImageView) rootView.findViewById(R.id.deals2friendPic1);
				fbFriendPic2 = (NetworkImageView) rootView.findViewById(R.id.deals2friendPic2);
				fbFriendPic3 = (NetworkImageView) rootView.findViewById(R.id.deals2friendPic3);
				TextView likedData = (TextView) rootView.findViewById(R.id.deals2likedData);
				
				System.out.println("RoopHome:description:"+ item.getDescription());
				feedImageView.setImageUrl(item.getSourceImage(), imageLoader);
				title.setText("Name");
				description.setText("Description");				
				fbFriendPic1.setImageUrl(item.getSourceImage(), imageLoader);
				fbFriendPic2.setImageUrl(item.getSourceImage(), imageLoader);
				fbFriendPic3.setImageUrl(item.getSourceImage(), imageLoader);
				likedData.setText(" also liked this");
			}
		}
	}
	
	public void initialization() {

		Cache cache = AppController.getInstance().getRequestQueue().getCache();

		if (listCacheName.isEmpty()) {
			listCacheName.add(FEED_0);
			// cache.clear();
		}
		System.out.println("roop: new size: " + listCacheName.size());

		for (int i = 0; i < listCacheName.size(); i++) {

			System.out.println("RoopHome: new i= " + i);
			String feed = listCacheName.get(i);
			// System.out.println("roop: new"+feed);
			Entry entry = cache.get(feed);
			if (entry != null) {
				System.out.println("roop: new Parse in cache");
				// fetch the data from cache
				try {
					String data = new String(entry.data, "UTF-8");
					// System.out.println("roop"+data);
					try {
						parseJsonFeed(new JSONObject(data), 1);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			} else {
				// making fresh volley request and getting json
				System.out.println("roop: new initialisation fetching");
				JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
						FEED_0, null, new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								VolleyLog.d(TAG,
										"Response: " + response.toString());
								System.out.println("roop: response");
								if (response != null) {
									System.out
											.println("roop: response not null");
									feedItems.clear();
									listCacheName.clear();
									listCacheName.add(FEED_0);
									parseJsonFeed(response, 0);
								}
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println("roop: error:"
										+ error.getMessage());
								VolleyLog.d(TAG, "Error: " + error.getMessage());
							}
						});
				// Adding request to volley request queue
				AppController.getInstance().addToRequestQueue(jsonReq);
				break;
			}
		}
	}

	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */

	private void parseJsonFeed(JSONObject response, int flag) {
		try {
			System.out.println("roop: in Parse");
			JSONArray feedArray = response.getJSONArray("news");

			System.out.println("roop: ParseFeed:" + feedArray.length());
			for (int i = 0; i < feedArray.length(); i++) {

				JSONObject feedObj = (JSONObject) feedArray.get(i);

				NewsFeedItem item = new NewsFeedItem();
				item.setCategoryID(feedObj.getInt("categoryID"));

				item.setGlobalId(feedObj.getInt("globalID"));

				item.setCategory(feedObj.getString("category"));

				item.setFreshness(feedObj.getInt("freshness"));

				item.setCreatedTime(feedObj.getString("created_time"));

				item.setSourceName(feedObj.getString("sourceName"));

				item.setPictureFB(feedObj.getString("pictureFB"));

				item.setSourceImage(feedObj.getString("sourceImage"));

				latestTimeStamp = feedObj.getString("timestamp");

				// Main Image
				if (feedObj.getString("pictureFB").isEmpty()) {
					item.setPictureFB(null);
				} else {
					item.setPictureFB(feedObj.getString("pictureFB"));
				}
				if (feedObj.getString("name").isEmpty()) {
					item.setName(null);
				} else {
					item.setName(feedObj.getString("name"));
				}
				if (feedObj.getString("description").isEmpty()) {
					item.setDescription(null);
				} else {
					item.setDescription(feedObj.getString("description"));
				}
				if (feedObj.getString("message").isEmpty()) {
					item.setMessage(null);
				} else {
					item.setMessage(feedObj.getString("message"));
				}
				if (feedObj.getString("link").isEmpty()) {
					item.setLink(null);
				} else {
					item.setLink(feedObj.getString("link"));
				}
				feedItems.add(item);
				System.out.println("RoopHome feedItem i" + i);
			}
			System.out.println("feedItem" + feedItems.size());
			fill();
			// notify data changes to list adapate

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
