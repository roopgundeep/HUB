package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.adapter.*;

import java.io.UnsupportedEncodingException;

import java.text.DateFormat;
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
import com.example.drawernavigationtabs.data.EventsFeedItem;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.example.drawernavigationtabs.detailpage.DemoDetailPageEvents;
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
public class Eventsfeed extends Fragment implements IXListViewListener {

	private static final String TAG = Eventsfeed.class.getSimpleName();
	private ListView listView;
	private XListView mListView;
	private EventsFeedListAdapter listAdapter;
	private List<EventsFeedItem> feedItems;
	private String user_id=null;
	private static String latestTimeStamp = "0";
	private String URL_FEED = "http://54.201.62.76/facebookNews.json";
	private String FEED = "http://54.68.32.118:8081/dataEvents?";
	//private String FEED_lat_long = FEED+"userID="+user_id+"&timestamp_Events="+latestTimeStamp;
	private String FEED_lat_long ="http://54.68.32.118/tejas/whack/events.json";
	private Handler mHandler;
	private Double Lat=null;
	private Double Long=null;
	private int start = 0;
	private static int refreshCnt = 0;
	private static int cacheCount = 0;
	public SqlHandler sqlHandler;
	public Calendar cal;
	public TimeZone tz;
	private ArrayList<String> listCacheName = new ArrayList<String>();
	GPSTracker gps;

	private int MY_SOCKET_TIMEOUT_MS=50000;
	private ProgressBar progressBar;
	public static final void hello() {

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//System.out.println("roop GPS called first"+item);
		switch (item.getItemId()) {
		case R.id.gps:
			//System.out.println("roop GPS called first"+item);
			//getGPS(1);			
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cal = Calendar.getInstance();
		tz = cal.getTimeZone();
		//setHasOptionsMenu(true);
		sqlHandler = new SqlHandler(getActivity());
		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM USERS");
		if(savedInstanceState == null) {        	        	
        	if (cursor!=null && cursor.getCount() > 0){
        		if (cursor.moveToFirst()) {
    				do{
    					System.out.println("roop Deal ID: "+cursor.getString(cursor.getColumnIndex("facebookId")));
    					user_id=cursor.getString(cursor.getColumnIndex("facebookId"));
    				}while (cursor.moveToNext());
    			}
        		
        	}
		}
		FEED_lat_long ="http://54.68.32.118/tejas/whack/events.json";
		//FEED_lat_long = FEED+"userID="+user_id+"&timestamp_Events="+latestTimeStamp;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.mainfeed, container, false);
		// listView = (ListView) rootView.findViewById(android.R.id.list);

		mListView = (XListView) rootView.findViewById(R.id.xListView);		
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#bdbdbd"), android.graphics.PorterDuff.Mode.MULTIPLY);
		
		//Drawable p = getResources().getDrawable(R.drawable.progress);
		//progressBar.setProgressDrawable(p);
		mListView.setPullLoadEnable(true);
		mListView.setDivider(null);
		feedItems = new ArrayList<EventsFeedItem>();

		listAdapter = new EventsFeedListAdapter(getActivity(), R.id.xListView,
				feedItems);
		mListView.setAdapter(listAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parnet,
					android.view.View view, int position, long id) {
				
				String ids2 = "0";
				ids2 = '"' + ids2 + '"';
				String query2 = "INSERT INTO EVENTFLAG( value ) " + "values ("
						+ ids2 + ")";
				System.out.println("roop DB " + query2);
				System.out.println("Roop DB query fb query " + query2);
				sqlHandler.executeQuery(query2);
				
				TextView mainImageLinkView=(TextView)view.findViewById(R.id.mainImageLink);
				String mainImageLink = mainImageLinkView.getText().toString();
				
				TextView descriptionView=(TextView)view.findViewById(R.id.description);
				String description = descriptionView.getText().toString();
				
				TextView linkView=(TextView)view.findViewById(R.id.link);
				String link = linkView.getText().toString();
				
				TextView addressView=(TextView)view.findViewById(R.id.address);
				String address = addressView.getText().toString();
				
				TextView organiserNameView=(TextView)view.findViewById(R.id.organiserName);
				String organiserName = organiserNameView.getText().toString();
				
				TextView endDateView=(TextView)view.findViewById(R.id.endDate);
				String endDate = endDateView.getText().toString();
				
				TextView feed_categoryView=(TextView)view.findViewById(R.id.feed_category);
				String feed_category = feed_categoryView.getText().toString();
				
				TextView titleView=(TextView)view.findViewById(R.id.title);
				String title = titleView.getText().toString();
				
				TextView venueView=(TextView)view.findViewById(R.id.venue);
				String venue = venueView.getText().toString();
				
				TextView startDateView=(TextView)view.findViewById(R.id.startDate);
				String startDate = startDateView.getText().toString();
				
				TextView suggestionView = (TextView) view.findViewById(R.id.suggestion);
				String suggestion = suggestionView.getText().toString();
				
				Intent intent = new Intent(getActivity(), DemoDetailPageEvents.class);
				intent.putExtra("mainImageLink",mainImageLink);
				intent.putExtra("description",description);
				intent.putExtra("link",link);
				intent.putExtra("address",address);
				intent.putExtra("organiserName",organiserName);
				intent.putExtra("endDate",endDate);
				intent.putExtra("feed_category",feed_category);
				intent.putExtra("venue",venue);
				intent.putExtra("startDate",startDate);
				intent.putExtra("title",title);
				intent.putExtra("suggestion",suggestion);
				startActivity(intent);

			}

		});

		// listView.setAdapter(listAdapter);
		/*if(Lat==null)
		{
			System.out.println("roop Deal first time Lat="+Lat);
			getGPS(0);
			initialization(1);
		}
		else*/	
		initialization(0);
		return rootView;
	}
	

	public void initialization(int flag) {
		
		
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		System.out.println("roop Deal: FEED_lat_long " + FEED_lat_long);
		if (listCacheName.isEmpty()) {
			listCacheName.add(FEED_lat_long);
			// cache.clear();
		}
		System.out.println("roop Deal: new size: " + listCacheName.size());
		FEED_lat_long ="http://54.68.32.118/tejas/whack/events.json";
		//FEED_lat_long = FEED+"userID="+user_id+"&timestamp_Events="+latestTimeStamp;
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
								System.out.println("roop Deal: response");
								if (response != null) {
									System.out
											.println("roop Deal: response not null");
									feedItems.clear();
									listCacheName.clear();
									//progressBar.setVisibility(View.GONE);
									listCacheName.add(FEED_lat_long);
									parseJsonFeed(response, 0);
									progressBar.setVisibility(View.GONE);
								}
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println("roop Deal: error:"
										+ error.getMessage());
								VolleyLog.d(TAG, "Error: " + error.getMessage());
								progressBar.setVisibility(View.GONE);
							}
						});
				// Adding request to volley request queue
				jsonReq.setRetryPolicy(new DefaultRetryPolicy(
		                MY_SOCKET_TIMEOUT_MS, 
		                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
		                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
				AppController.getInstance().addToRequestQueue(jsonReq);
				break;
			}
		}
	}

	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */

	@SuppressLint("SimpleDateFormat")
	private void parseJsonFeed(JSONObject response, int flag) {
		try {
			
			System.out.println("roop Deal: in Parse");
			JSONArray feedArray = response.getJSONArray("events");
			System.out.println("roop Deal: ParseFeed:" + feedArray.length());
			/* flag==1 means its loading more...so retaining previous tuples */
			if (flag == 0) {
				feedItems.clear();
			}
			System.out.println("roop Event : ParseFeed:" + feedArray.length());
			for (int i = 0; i < feedArray.length(); i++) {

				JSONObject feedObj = (JSONObject) feedArray.get(i);

				EventsFeedItem item = new EventsFeedItem();
//				if(feedObj.getString("category").isEmpty())
//				{	
//					item.setCategory(null);
//				}
//				else
//					item.setCategory(feedObj.getString("category"));				
				item.setImage(feedObj.getString("image"));				
				item.setTimeStamp(feedObj.getString("timestamp"));			
								
				
				System.out.println("roop Event : ParseFeed:" + feedArray.length());
				if(feedObj.getString("formattedAddress").isEmpty())
				{	
					item.setAddress(null);
				}
				else
					item.setAddress(feedObj.getString("formattedAddress"));
				
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

				String result = destFormat.format(parsed);
				item.setStartTime(result);	
				
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
				
				item.setLink(feedObj.getString("link"));
				
				if(feedObj.getString("venueName").isEmpty())
				{	
					item.setVenue(feedObj.getString("venueName"));
				}
				else
					item.setVenue(feedObj.getString("venueName"));
				
				if(feedObj.getString("organizerName").isEmpty())
				{	
					item.setOrganizerName(null);
				}
				else
					item.setOrganizerName(feedObj.getString("organizerName"));

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
				//Added
				JSONArray suggestionsArray = feedObj.getJSONArray("suggestionsArray");
				String array2=suggestionsArray.toString();
				item.setSuggestionArray(array2);
				//Added
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
		FEED_lat_long ="http://54.68.32.118/tejas/whack/events.json";
		//FEED_lat_long = FEED+"userID="+user_id+"&timestamp_Events="+latestTimeStamp;
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, FEED_lat_long,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: refresh");
							listCacheName.clear();
							listCacheName.add(FEED_lat_long);							
							parseJsonFeed(response, 0);
							progressBar.setVisibility(View.GONE);
							onLoad();
							
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

		//String feed = FEED + latestTimeStamp;
		FEED_lat_long ="http://54.68.32.118/tejas/whack/events.json";
		//FEED_lat_long = FEED+"userID="+user_id+"&timestamp_Events="+latestTimeStamp;
		String feed =FEED_lat_long;
		System.out.println("roopgundeep Deal: new" + feed);
		listCacheName.add(feed);
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				listCacheName.get(listCacheName.size() - 1), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: load More");
							parseJsonFeed(response, 1);
							onLoad();
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
				//onLoad();
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
				listAdapter.notifyDataSetChanged();
				//onLoad();
			}
		}, 2000);

	}

	private void getGPS(int first) {
		gps = new GPSTracker(getActivity());
		// TODO Auto-generated method stub
		if (gps.canGetLocation()) {
			progressBar.setVisibility(View.VISIBLE);
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			Lat=latitude;
			Long=longitude;
			//FEED_lat_long +="?latitude="+latitude+"&longitude="+longitude;
			// \n is for new line
//			Toast.makeText(
//					getActivity(),
//					"Your Location is - \nLat: " + latitude + "\nLong: "
//							+ longitude, Toast.LENGTH_LONG).show();
			System.out.println("roop GPS called first="+first);
			if(first!=0)
				refreshItems(1);
			
		} else {
			System.out.println("roop cannot GPS called first="+first);
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

	}

}