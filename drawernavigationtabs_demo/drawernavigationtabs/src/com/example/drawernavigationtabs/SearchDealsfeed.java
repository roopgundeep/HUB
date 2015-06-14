package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.adapter.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drawernavigationtabs.*;
import com.example.drawernavigationtabs.adapter.NewsFeedListAdapter;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.DealsFeedItem;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.example.drawernavigationtabs.detailpage.DemoDetailPageDeals;
import com.example.drawernavigationtabs.detailpage.DemoDetailPageNews;
import com.example.drawernavigationtabs.view.XListView;
import com.example.drawernavigationtabs.view.XListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

@SuppressLint("NewApi")
public class SearchDealsfeed extends FragmentActivity implements IXListViewListener {

	private static final String TAG = SearchDealsfeed.class.getSimpleName();
	private ListView listView;
	private XListView mListView;
	private DealsFeedListAdapter listAdapter;
	private List<DealsFeedItem> feedItems;
	private String URL_FEED = "http://54.201.62.76/facebookNews.json";
	
	private String FEED_0 = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=0";
	public String query=null;
	public String userID=null;
	private static String latestTimeStamp = "0";
	private String FEED = "http://54.68.32.118:8082/dataVenues";
	//private String FEED_lat_long = FEED+"?q="+query+"&userID="+userID+
		//	"&timestamp_Venues="+latestTimeStamp;
	String initial="http://54.68.32.118/venues/";
	private String FEED_lat_long =initial+query+".json";
	private Handler mHandler;
	private Double Lat=null;
	private Double Long=null;
	public SqlHandler sqlHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private static int cacheCount = 0;
	private String link=null;
	private ArrayList<String> listCacheName = new ArrayList<String>();
	GPSTracker gps;
	private ProgressBar progressBar;
	public static final void hello() {

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mainfeed);
		
		System.out.println("Search Query intent"+ getIntent().getStringExtra("link"));
		if(getIntent()!=null)
		{
			
			link=getIntent().getStringExtra("link");
			System.out.println("Search Query link inside"+ link);
		}
		System.out.println("Search Query link"+ getIntent());
		query=link;
		// listView = (ListView) rootView.findViewById(android.R.id.list);
		//FEED_lat_long +="/?type="+link;
		//Toast.makeText(getApplicationContext(), link, Toast.LENGTH_LONG).show();
		mListView = (XListView) findViewById(R.id.xListView);		
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		
		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#259b24"), android.graphics.PorterDuff.Mode.MULTIPLY);
		//Drawable p = getResources().getDrawable(R.drawable.progress);
		//progressBar.setProgressDrawable(p);
		mListView.setPullLoadEnable(true);
		mListView.setDivider(null);
		feedItems = new ArrayList<DealsFeedItem>();

		listAdapter = new DealsFeedListAdapter(this, R.id.xListView,
				feedItems);
		mListView.setAdapter(listAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		Drawable back = getResources().getDrawable( R.drawable.actionbar_dealspage_background_top );
		getActionBar().setBackgroundDrawable(back);
		getActionBar().show();       
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(link);
        //setHasOptionsMenu(false);
        //getActionBar().(R.drawable.homepage_background_top);
        sqlHandler = new SqlHandler(this);
		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM USERS");
		if(savedInstanceState == null) {        	        	
        	if (cursor!=null && cursor.getCount() > 0){
        		if (cursor.moveToFirst()) {
    				do{
    					System.out.println("roop Deal ID: "+cursor.getString(cursor.getColumnIndex("facebookId")));
    					userID=cursor.getString(cursor.getColumnIndex("facebookId"));
    				}while (cursor.moveToNext());
    			}
        		
        	}
		}
		FEED_lat_long =initial+query+".json";
		System.out.println("Search Query:" + FEED_lat_long);
		//FEED_lat_long = FEED+"?q="+query+"&userID="+userID+
			//	"&timestamp_Venues="+latestTimeStamp;
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parnet,
					android.view.View view, int position, long id) {
				
				/*String ids1 = "0";
				ids1 = '"' + ids1 + '"';
				String query1 = "INSERT INTO EVENTFLAG( value ) " + "values ("
						+ ids1 + ")";
				System.out.println("roop DB " + query1);
				System.out.println("Roop DB query fb query " + query1);
				sqlHandler.executeQuery(query1);
*/
				TextView feedImage_urlView=(TextView)view.findViewById(R.id.feedImage_url);
				String feedImage_url = feedImage_urlView.getText().toString();
				
				TextView distanceView=(TextView)view.findViewById(R.id.distance);
				String distance = distanceView.getText().toString();
				
				TextView VenueNameView=(TextView)view.findViewById(R.id.VenueName);
				String VenueName = VenueNameView.getText().toString();
				
				TextView feed_categoryView=(TextView)view.findViewById(R.id.feed_category);
				String feed_category = feed_categoryView.getText().toString();
				
				TextView phrasesView=(TextView)view.findViewById(R.id.phrases);
				String phrases = phrasesView.getText().toString();
				
				RatingBar ratingView=(RatingBar)view.findViewById(R.id.rating);
				float rating = ratingView.getRating();
				
				TextView friendPic1_urlView=(TextView)view.findViewById(R.id.friendPic1_url);
				String friendPic1_url = friendPic1_urlView.getText().toString();
				
				TextView friendPic2_urlView=(TextView)view.findViewById(R.id.friendPic2_url);
				String friendPic2_url = friendPic2_urlView.getText().toString();
				
				TextView friendPic3_urlView=(TextView)view.findViewById(R.id.friendPic3_url);
				String friendPic3_url = friendPic3_urlView.getText().toString();
				
				TextView dealArrayView =(TextView)view.findViewById(R.id.dealArray);
				String dealArray = dealArrayView.getText().toString();
				
				TextView addressView =(TextView)view.findViewById(R.id.address);
				String address = addressView.getText().toString();
				
				TextView canonicalUrlView = (TextView) view.findViewById(R.id.canonicalUrl);				
				String canonicalUrl = canonicalUrlView.getText().toString();
				
				TextView phoneView =(TextView)view.findViewById(R.id.phone);
				String phone = phoneView.getText().toString();
				
				TextView suggestionView = (TextView) view.findViewById(R.id.suggestion);
				String suggestion = suggestionView.getText().toString();
				
				Intent intent = new Intent(getApplicationContext(), DemoDetailPageDeals.class);
				intent.putExtra("feedImage_url", feedImage_url);
				intent.putExtra("distance", distance);
				intent.putExtra("VenueName", VenueName);
				intent.putExtra("feed_category", feed_category);
				intent.putExtra("rating", rating);
				intent.putExtra("friendPic1_url", friendPic1_url);
				intent.putExtra("friendPic2_url", friendPic2_url);
				intent.putExtra("friendPic3_url", friendPic3_url);
				intent.putExtra("dealArray", dealArray);
				intent.putExtra("address", address);
				intent.putExtra("phone", phone);
				intent.putExtra("phrases", phrases);
				intent.putExtra("canonicalUrl", canonicalUrl);
				intent.putExtra("suggestion", suggestion);
				
				//System.out.println("hello I am clicked"+ dealArray);
				startActivity(intent);

			}

		});
		// listView.setAdapter(listAdapter);
		if(Lat==null)
		{
			System.out.println("roop Deal first time Lat="+Lat);
			
			initialization(1);
		}
		else	
			initialization(0);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
	        finish();
	      
	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;

	    }
	    return true;
	}

	public void initialization(int flag) {
			
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		System.out.println("roop Deal: FEED_lat_long " + FEED_lat_long);
		if (listCacheName.isEmpty()) {
			listCacheName.add(FEED_lat_long);
			// cache.clear();
		}
		System.out.println("roop Deal: new size: " + listCacheName.size());
		int i=listCacheName.indexOf(FEED_lat_long);
		for (; i < listCacheName.size(); i++) {

			// System.out.println("roop: new i= "+i);
			if(i<0)
			{
				System.out.println("i is negative" + listCacheName.size());
			}
			String feed = listCacheName.get(i);
			// System.out.println("roop: new"+feed);
			Entry entry = cache.get(feed);

			if (entry != null) {
				System.out.println("roop Deal: new Parse in cache");
				// fetch the data from cache
				try {
					String data = new String(entry.data, "UTF-8");
					System.out.println("roop Deal data: "+data);
					try {
						progressBar.setVisibility(View.GONE);
						parseJsonFeed(new JSONObject(data), 1);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			} else {
				// making fresh volley request and getting json
				progressBar.setVisibility(View.VISIBLE);
				System.out.println("roop Deal: new initialisation fetching"+FEED_lat_long);
				JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
						FEED_lat_long, null, new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								VolleyLog.d(TAG,
										"Response: " + response.toString());
								System.out.println("roop Search: response"+response);
								if (response != null) {
									System.out
											.println("roop Deal: response not null");
									feedItems.clear();
									listCacheName.clear();
									progressBar.setVisibility(View.GONE);
									listCacheName.add(FEED_lat_long);
									parseJsonFeed(response, 0);
								}
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println("roop Deal: error:"
										+ error.getMessage());
								progressBar.setVisibility(View.GONE);
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
			
			System.out.println("roop Deal: in Parse");
			JSONArray feedArray = response.getJSONArray("venues");
			System.out.println("roop Deal: ParseFeed:" + feedArray.length());

			/* flag==1 means its loading more...so retaining previous tuples */
			if (flag == 0) {
				feedItems.clear();
			}
			System.out.println("roop Deal Deal: ParseFeed:" + feedArray.length());
			for (int i = 0; i < feedArray.length(); i++) {

				JSONObject feedObj = (JSONObject) feedArray.get(i);

				DealsFeedItem item = new DealsFeedItem();
				
				item.setCategory(feedObj.getString("categoryName"));
				item.setRating(feedObj.getDouble("rating"));				
				item.setPictureFB(feedObj.getString("venuePhoto"));	
				item.setTimeStamp(feedObj.getString("timestamp"));
				item.setFacebookFriend1(feedObj.getString("facebookFriend1"));
				item.setFacebookFriend2(feedObj.getString("facebookFriend2"));
				item.setFacebookFriend3(feedObj.getString("facebookFriend3"));
				item.setDealFlag(feedObj.getBoolean("dealFlag"));
				JSONArray praseArray = feedObj.getJSONArray("phrases");
				String phrases="";
				for(int j=0;j<praseArray.length();j++)
				{
					JSONObject phraseObj = (JSONObject) praseArray.get(j);
					phrases +=phraseObj.getString("phrase");
					if(j!=praseArray.length()-1)
						phrases+=" | ";
				}
				if (feedObj.getString("formattedAddress").isEmpty() || feedObj.getString("formattedAddress").compareTo("null")==0) {
					item.setAddress(null);
				} else {
					item.setAddress(feedObj.getString("formattedAddress"));
				}
				//System.out.println("roop Deal: time: " + phrases);
				item.setPhrase(phrases);
				int Distance =feedObj.getInt("distance");
				String distance=Distance+" m";
				if(Distance>1000)
				{
					int p=(Distance/1000);
					int remainder=(Distance%1000)/100;
					distance=p+"."+remainder+" km";
				}
				item.setDistance(distance);
				latestTimeStamp = feedObj.getString("timestamp");
				// Main Image
				if (feedObj.getString("venuePhoto").isEmpty()) {
					item.setPictureFB(null);
				} else {
					item.setPictureFB(feedObj.getString("venuePhoto"));
				}
				if (feedObj.getString("name").isEmpty()) {
					item.setName(null);
				} else {
					item.setName(feedObj.getString("name"));
				}
				System.out
						.println("roop Deal: time: " + latestTimeStamp + "at i=15");
				feedItems.add(item);
			}
			// notify data changes to list adapater
			listAdapter.notifyDataSetChanged();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void refreshItems(int flag) {

		Log.d("roop Deal", "roop: fetching");
		latestTimeStamp="0";
		FEED_lat_long =initial+query+".json";
		//FEED_lat_long = FEED+"?q="+query+"&userID="+userID+
			//	"&timestamp_Venues="+latestTimeStamp;
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, FEED_lat_long,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop Search: response"+response);
							listCacheName.clear();
							listCacheName.add(FEED_lat_long);							
							parseJsonFeed(response, 0);
							//progressBar.setVisibility(View.GONE);
							
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						//progressBar.setVisibility(View.GONE);
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});

		// Adding request to volley request queue
		AppController.getInstance().addToRequestQueue(jsonReq);

	}

	private void loadMore() {

		//String feed = FEED + latestTimeStamp;
		FEED_lat_long =initial+query+".json";
		//FEED_lat_long = FEED+"?q="+query+"&userID="+userID+
			//	"&timestamp_Venues="+latestTimeStamp;
		String feed =FEED_lat_long;
		System.out.println("roopgundeep Deal: new" + feed);
		listCacheName.add(feed);
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				listCacheName.get(listCacheName.size() - 1), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("roop Search: response"+response);
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: load More");
							parseJsonFeed(response, 1);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});

		// Adding request to volley request queue
		// Entry ent= ;
		AppController.getInstance().addToRequestQueue(jsonReq);

	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("Just");
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				System.out.println("roop: in on refresh");
				start = ++refreshCnt;
				refreshItems(0);
				// mAdapter.notifyDataSetChanged();
				// listAdapter = new
				// FeedListAdapter(getActivity(),android.R.id.list, feedItems);
				// mListView.setAdapter(listAdapter);
				/*
				 * mAdapter = new ArrayAdapter<String>(XListViewActivity.this,
				 * R.layout.list_item, items); mListView.setAdapter(mAdapter);
				 */
				onLoad();
			}
		}, 2000);

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				System.out.println("roop: in load more");
				// geneItems();
				loadMore();
				listAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);

	}

	

}