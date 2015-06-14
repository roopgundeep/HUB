package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.adapter.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drawernavigationtabs.*;
import com.example.drawernavigationtabs.adapter.NewsFeedListAdapter;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.DealsFeedItem;
import com.example.drawernavigationtabs.data.EventsFeedItem;
import com.example.drawernavigationtabs.data.HomeFeedItem;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.example.drawernavigationtabs.detailpage.DemoDetailPageNews;
import com.example.drawernavigationtabs.view.XListView;
import com.example.drawernavigationtabs.view.XListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

@SuppressLint("NewApi")
public class Homefeed extends Fragment implements IXListViewListener {

	public static final String TAG = Homefeed.class.getSimpleName();
	private ListView listView;
	private XListView mListView;
	private HomeFeedListAdapter listAdapter;
	private int MY_SOCKET_TIMEOUT_MS=50000;
	private List<HomeFeedItem> feedItems;
	public Integer currentHour=null;
	public String userID=null;
	public int deals=0;
	public int news=0;
	public int events=0;
	private String URL_FEED = "http://54.201.62.76/facebookNews.json";	
	private String FEED_0 = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=0";
	private String FEED = "http://54.68.32.118:8083/dataHomePage?";
	//private String FEED_lat_long = FEED+"userID="+userID+"&currentHour="+currentHour+"&eventsFlag="+events;
	private String FEED_lat_long = "http://54.68.32.118/tejas/whack/homePage0.json";
	private String FEED_lat_long0 = "http://54.68.32.118/tejas/whack/homePage0.json";
	private String FEED_lat_long1 = "http://54.68.32.118/tejas/whack/homePage1.json";
	private String FEED_lat_long110 = "http://54.68.32.118/homePage110.json";
	private String FEED_lat_long111 = "http://54.68.32.118/homePage111.json";
	private Handler mHandler;
	private Double Lat = null;
	private Double Long = null;
	private int start = 0;
	
	public Calendar cal;
	public TimeZone tz;
	private static int refreshCnt = 0;
	private static int cacheCount = 0;
	private static String latestTimeStamp = "0";
	
	public SqlHandler sqlHandler;
	private ArrayList<String> listCacheName = new ArrayList<String>();
	GPSTracker gps;
	private ProgressBar progressBar;

	public static final void hello() {

	}
	public static Homefeed newInstance() {
		return new Homefeed();
	}
	public void changeURL(){
		 Cursor cursor = sqlHandler.selectQuery("SELECT * FROM FLAG");
		 if (cursor != null && cursor.getCount() > 0) {			
		 	if (cursor.moveToFirst()) {
		 		do {
		 			System.out.println("roop FLAG DB: "
		 					+ cursor.getInt(cursor.getColumnIndex("news")));
		 			news=cursor.getInt(cursor.getColumnIndex("news"));
		 			deals=cursor.getInt(cursor.getColumnIndex("deals"));
		 			events=cursor.getInt(cursor.getColumnIndex("events"));
		 		} while (cursor.moveToNext());
		 	}
		 }
		 System.out.println("roop change url: event val= "+events);
		 if(events==1)
		 {
		 	FEED_lat_long=FEED_lat_long1;
		 }
		 System.out.println("roop change url: FEED_lat_long val= "+FEED_lat_long);
		//else
			//FEED_lat_long=FEED_lat_long0;
		//FEED_lat_long = FEED+"userID="+userID+"&currentHour="+currentHour+"&eventsFlag="+events;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sqlHandler = new SqlHandler(getActivity());
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
		//changeURL();
		System.out.println("Print Status News="+news+" Events="+events+"Deals="+deals);
		cal = Calendar.getInstance();
		tz = cal.getTimeZone();
		Calendar rightNow = Calendar.getInstance();				
		currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
		changeURL();
		//listCacheName.clear();
		//FEED_lat_long = FEED+"userID="+userID+"&currentHour="+currentHour+"&eventsFlag="+events;
		// setHasOptionsMenu(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.mainfeed, container, false);
		// listView = (ListView) rootView.findViewById(android.R.id.list);

		mListView = (XListView) rootView.findViewById(R.id.xListView);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
		mListView.hidefooter();
		progressBar.getIndeterminateDrawable().setColorFilter(
				Color.parseColor("#bdbdbd"),
				android.graphics.PorterDuff.Mode.MULTIPLY);
		// Drawable p = getResources().getDrawable(R.drawable.progress);
		// progressBar.setProgressDrawable(p);
		// mListView.setPullLoadEnable(true);
		mListView.setDivider(null);
		feedItems = new ArrayList<HomeFeedItem>();

		listAdapter = new HomeFeedListAdapter(getActivity(), R.id.xListView,
				feedItems);
		mListView.setAdapter(listAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parnet,
					android.view.View view, int position, long id) {

			}
		});
		changeURL();
		initialization(0);
		return rootView;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		progressBar.getIndeterminateDrawable().setColorFilter(
				Color.parseColor("#bdbdbd"),
				android.graphics.PorterDuff.Mode.MULTIPLY);
		refreshItems(1);
	}
	public void initialization(int flag) {
		
		System.out.println("roop change url: FEED_lat_long val= "+FEED_lat_long);
		System.out.println("roop new: new size: " + FEED_lat_long);
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		//changeURL();
		//if(flag==0)
			//cache.clear();
		System.out.println("roop Deal1: FEED_lat_long " + FEED_lat_long +"--"+ flag);
		if (listCacheName.isEmpty()) {
			listCacheName.add(FEED_lat_long);
			 //cache.clear();
		}
		
		System.out.println("roop new: new size: " + listCacheName.size());
		int i = listCacheName.indexOf(FEED_lat_long);
		System.out.println("roop new: i=  " + i);
		for (; i < listCacheName.size(); i++) {
			// System.out.println("roop: new i= "+i);
			if (i < 0) {
				System.out.println("i is negative" + listCacheName.size());
			}
			String feed = listCacheName.get(i);
			System.out.println("listCacheName.clear(); "+i);
			Entry entry = cache.get(feed);
			
			if (entry != null) {
				System.out.println("roop new: new Parse in cache");
				// fetch the data from cache
				
				try {
					String data = new String(entry.data, "UTF-8");
					System.out.println("roop Deal data: " + data);
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
				System.out.println("roop new: new initialisation fetching"
						+ FEED_lat_long);
				JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
						FEED_lat_long, null,
						new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								VolleyLog.d(TAG,
										"Response: " + response.toString());
								System.out.println("roop new: response");
								if (response != null) {
									System.out
											.println("roop new: response not null");
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
								System.out.println("roop new: error:"
										+ error.getMessage());
								VolleyLog.d(TAG, "Error: " + error.getMessage());
							}
						});
				jsonReq.setRetryPolicy(new DefaultRetryPolicy(
		                MY_SOCKET_TIMEOUT_MS, 
		                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
		                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
				// Adding request to volley request queue
				AppController.getInstance().addToRequestQueue(jsonReq);
				break;
			}
		}
	}

	private void parseJsonFeed(JSONObject response, int flag) {
		try {

			System.out.println("roop new: in Parse");
			JSONArray feedArray = response.getJSONArray("categories");
			System.out.println("roop new: ParseFeed:" + feedArray.length());
			/* flag==1 means its loading more...so retaining previous tuples */
//			if (flag == 0) {
//				feedItems.clear();
//			}
			System.out.println("roop new: ParseFeed: length= "
					+ feedArray.length());
			feedItems.add(null);
			for (int i = 0; i < feedArray.length(); i++) {

				JSONObject feedObjGlobal = (JSONObject) feedArray.get(i);
				System.out.println("roop new: "+feedObjGlobal.getString("categoryType"));
				
				if (feedObjGlobal.getString("categoryType").equals("venues")) {
										
					JSONArray feedObjArray = feedObjGlobal.getJSONArray("venues");
					for (int k = 0; k < feedObjArray.length(); k++) {
												
						
						JSONObject feedObj = (JSONObject) feedObjArray.get(k);						
						HomeFeedItem item = new HomeFeedItem();
						item.setCategoryLabel(feedObjGlobal.getString("categoryLabel"));						
						item.setCategoryType(feedObjGlobal.getString("categoryType"));					
						item.setVisibilityTag(k);
						item.setCategory(feedObj.getString("categoryName"));
						item.setRating(feedObj.getDouble("rating"));									
						item.setPictureFB(feedObj.getString("venuePhoto"));	
						item.setTimeStamp(feedObj.getString("timestamp"));
						item.setFacebookFriend1(feedObj.getString("facebookFriend1"));
						item.setFacebookFriend2(feedObj.getString("facebookFriend2"));
						item.setFacebookFriend3(feedObj.getString("facebookFriend3"));										
						item.setDealFlag(feedObj.getBoolean("dealFlag"));
						item.setcanonicalUrl(feedObj.getString("canonicalUrl"));
						JSONArray dealArray = feedObj.getJSONArray("dealsArray");
						String array=dealArray.toString();
						item.setDealArray(array);
						JSONArray praseArray = feedObj.getJSONArray("phrases");
						if (feedObj.getString("formattedAddress").isEmpty() || feedObj.getString("formattedAddress").compareTo("null")==0) {
							item.setAddress(null);
						} else {
							item.setAddress(feedObj.getString("formattedAddress"));
						}
						if (feedObj.getString("phone").isEmpty() || feedObj.getString("phone").compareTo("null")==0 )
						{
							item.setPhoneNumber(null);
						} else {
							item.setPhoneNumber(feedObj.getString("phone"));
						}
						String phrases = "";
						for (int j = 0; j < praseArray.length(); j++) {
							JSONObject phraseObj = (JSONObject) praseArray
									.get(j);
							phrases += phraseObj.getString("phrase");
							if (j != praseArray.length() - 1)
								phrases += " | ";
						}						
						if(praseArray.length()==0)
							item.setPhrase(null);
						else
							item.setPhrase(phrases);						
						int Distance = feedObj.getInt("distance");
						String distance = Distance + " m";
						if (Distance > 1000) {
							int p = (Distance / 1000);
							int remainder = (Distance % 1000) / 100;
							distance = p + "." + remainder + " km";
						}
						//Added
						JSONArray suggestionsArray = feedObj.getJSONArray("suggestionsArray");
						String array2=suggestionsArray.toString();
						item.setSuggestionArray(array2);
						//Added
						System.out.println("Venue Suggestions1");
						item.setDistance(distance);
						latestTimeStamp = feedObj.getString("timestamp");
						// Main Image
						if (feedObj.getString("venuePhoto").isEmpty() || feedObj.getString("venuePhoto").compareTo("null")==0) {
							item.setPictureFB(null);
						} else {
							item.setPictureFB(feedObj.getString("venuePhoto"));
						}
						if (feedObj.getString("name").isEmpty() || feedObj.getString("name").compareTo("null")==0) {
							item.setName(null);
						} else {
							item.setName(feedObj.getString("name"));
						}
						 System.out.println("roop Home Deal: " + i);
						feedItems.add(item);
					}
				}
				else if (feedObjGlobal.getString("categoryType").equals("news")) {
					
					System.out.println("roop Home in: "+feedObjGlobal.getString("categoryType"));
					JSONArray feedObjArray = feedObjGlobal.getJSONArray("news");
					for (int k = 0; k < feedObjArray.length(); k++) {
						
						JSONObject feedObj = (JSONObject) feedObjArray.get(k);						
						HomeFeedItem item = new HomeFeedItem();
						System.out.println("roop Home in in: "+feedObjGlobal.getString("categoryType"));
						item.setCategoryLabel(feedObjGlobal.getString("categoryLabel"));						
						item.setCategoryType(feedObjGlobal.getString("categoryType"));					
						item.setVisibilityTag(k);
						//item.setCategoryID(feedObj.getInt("categoryID"));
						item.setTimeStamp(feedObj.getString("timestamp"));
						System.out.println("roop Home News: 1");
						if(feedObj.getString("category").isEmpty() || feedObj.getString("category").compareTo("null")==0 )
						{	
							item.setCategory(null);
						}
						else{
							item.setCategory(feedObj.getString("category"));
						}
						System.out.println("roop Home News: 2");
						//long now            = System.currentTimeMillis();
						//long minutesAgo     = now - (feedObj.getInt("freshness")*(60000));
						//String time=(String) DateUtils.getRelativeDateTimeString(getActivity(), minutesAgo, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
						//item.setFreshness(feedObj.getInt("freshness"));
						//System.out.println("roop Home News: 3");
						//String[] a=time.split(",");						
						item.setCreatedTime(feedObj.getString("freshness"));	
						System.out.println("roop Home News: 4");
						item.setSourceName(feedObj.getString("sourceName"));
						System.out.println("roop Home News: 5");
						item.setSourceImage(feedObj.getString("sourceImage"));
						System.out.println("roop Home News: 6");
						if (feedObj.getString("image").isEmpty()) {
							item.setMainImage(null);
						} else {
							item.setMainImage(feedObj.getString("image"));
						}
						System.out.println("roop Home News: 5");
						item.setName(feedObj.getString("title"));						
						item.setLink(feedObj.getString("link"));						
						if (feedObj.getString("description").isEmpty()) {
							item.setDescription(feedObj.getString("message"));
						} else {
							item.setDescription(feedObj.getString("description"));
						}
						//Added
						JSONArray suggestionsArray = feedObj.getJSONArray("suggestionsArray");
						String array=suggestionsArray.toString();
						item.setSuggestionArray(array);
						//Added
						System.out.println("Venue Suggestions1");
						item.setFacebookFriend1(feedObj.getString("facebookFriend1"));
						item.setFacebookFriend2(feedObj.getString("facebookFriend2"));
						item.setFacebookFriend3(feedObj.getString("facebookFriend3"));
						latestTimeStamp = feedObj.getString("timestamp");
						System.out.println("roop Home News: 8");
						feedItems.add(item);
					}										
				}
				else if (feedObjGlobal.getString("categoryType").equals("events")) {
					
					System.out.println("roop new: ITs EVENTS "+feedObjGlobal.getString("categoryType"));
					JSONArray feedObjArray = feedObjGlobal.getJSONArray("events");
					for (int k = 0; k < feedObjArray.length(); k++) {
						
						JSONObject feedObj = (JSONObject) feedObjArray.get(k);						
						HomeFeedItem item = new HomeFeedItem();
						
						item.setCategoryLabel(feedObjGlobal.getString("categoryLabel"));
						System.out.println("roop event new: 1 "+feedObjGlobal.getString("categoryType"));
						item.setCategoryType(feedObjGlobal.getString("categoryType"));			
						System.out.println("roop event new: 2 "+feedObjGlobal.getString("categoryType"));
						item.setVisibilityTag(k);
						
//						if(feedObj.getString("category").isEmpty())
//						{	
//							item.setCategory(null);
//						}
//						else
//							item.setCategory(feedObj.getString("category"));
						//item.setCategory(feedObj.getString("categoryType"));
						System.out.println("Home Events 1"+i);
						
						item.setImage(feedObj.getString("image"));
						System.out.println("roop event new: 3 "+feedObjGlobal.getString("categoryType"));
						item.setTimeStamp(feedObj.getString("timestamp"));
						System.out.println("roop event new: 4 "+feedObjGlobal.getString("categoryType"));
						System.out.println("Home Events 2"+i);				
						if(feedObj.getString("formattedAddress").isEmpty() || feedObj.getString("formattedAddress").compareTo("null")==0)
						{	
							item.setAddress(null);
						}
						else
							item.setAddress(feedObj.getString("formattedAddress"));
						System.out.println("roop event new: 5 "+feedObjGlobal.getString("categoryType"));
						if(feedObj.getString("organizerName").isEmpty() || feedObj.getString("organizerName").compareTo("null")==0)
						{	
							item.setOrganizerName(null);
						}
						else
							item.setOrganizerName(feedObj.getString("organizerName"));
						System.out.println("roop event new: 6 "+feedObjGlobal.getString("categoryType"));
						System.out.println("roop Event : ParseFeed:" + feedArray.length());
						System.out.println("Home Events 3"+i);
						System.out.println("roop event new: 7 "+feedObjGlobal.getString("categoryType"));
						SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
						Date parsed = null;
						try {
							parsed = sourceFormat.parse(feedObj.getString("startTime"));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // => Date is in UTC now
						SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyy HH:mm");
						destFormat.setTimeZone(tz);
						System.out.println("roop event new: 8 "+feedObjGlobal.getString("categoryType"));
						String result = destFormat.format(parsed);
						item.setStartTime(result);	
						System.out.println("Home Events 4"+i);
						System.out.println("Roop Event:"+result);
						Date parsed_end = null;
						try {
							parsed_end = sourceFormat.parse(feedObj.getString("endTime"));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // => Date is in UTC now
						
						SimpleDateFormat destFormat_end = new SimpleDateFormat("dd MMM yyy HH:mm");
						destFormat_end.setTimeZone(tz);
						String result_end = destFormat.format(parsed);
						item.setEndTime(result_end);				
						latestTimeStamp = feedObj.getString("timestamp");
						System.out.println("Home Events 6"+i);
						item.setLink(feedObj.getString("link"));
						item.setVenue(feedObj.getString("venueName"));
						item.setOrganizerName(feedObj.getString("organizerName"));
						
						//Added
						JSONArray suggestionsArray = feedObj.getJSONArray("suggestionsArray");
						String array=suggestionsArray.toString();
						item.setSuggestionArray(array);
						//Added
						
						System.out.println("Home Events 7"+i);
						// Main Image
						if (feedObj.getString("title").isEmpty()) {
							item.setName(null);
						} else {
							item.setName(feedObj.getString("title"));
						}
						if (feedObj.getString("description").isEmpty()) {
							item.setDescription(null);
						} else {
							item.setDescription(feedObj.getString("description"));
						}
						if (feedObj.getString("link").isEmpty()) {
							item.setLink(null);
						} else {
							item.setLink(feedObj.getString("link"));
						}
						System.out.println("Home Events 8"+i);
						feedItems.add(item);
					}										
				}
				System.out.println("roop Deal: time: " + latestTimeStamp
						+ "at i=15");
			}
			// notify data changes to list adapater
			listAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void refreshItems(int flag) {
		
		//AppController.getInstance().getRequestQueue().getCache().remove(FEED_lat_long);
		changeURL();
		Calendar rightNow = Calendar.getInstance();				
		currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		cache.clear();
		//FEED_lat_long = FEED+"userID="+userID+"&currentHour="+currentHour+"&eventsFlag="+events;
		System.out.println("The HomePAge Feed"+FEED_lat_long);
		Log.d("roop Deal", "roop: fetching");
		if (flag == 1)
			progressBar.setVisibility(View.VISIBLE);		
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				FEED_lat_long, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							//System.out.println("roop: ParseFeed: refresh");
							//listCacheName.clear();
							//listCacheName.add(FEED_lat_long);
							//parseJsonFeed(response, 0);
							progressBar.setVisibility(View.GONE);
							//onLoad();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressBar.setVisibility(View.GONE);
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						onLoad();
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
		// Adding request to volley request queue
		AppController.getInstance().addToRequestQueue(jsonReq);

	}

	private void loadMore() {

		// String feed = FEED + latestTimeStamp;
		String feed = FEED_lat_long;
		System.out.println("roopgundeep Deal: new" + feed);
		//listCacheName.add(feed);
		/*JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				listCacheName.get(listCacheName.size() - 1), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: load More");
							onLoad();
							parseJsonFeed(response, 1);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						onLoad();
					}
				});

		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
		// Adding request to volley request queue
		// Entry ent= ;
		AppController.getInstance().addToRequestQueue(jsonReq);
*/
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
				//loadMore();
				//listAdapter.notifyDataSetChanged();
				//onLoad();
			}
		}, 2000);

	}
	

}