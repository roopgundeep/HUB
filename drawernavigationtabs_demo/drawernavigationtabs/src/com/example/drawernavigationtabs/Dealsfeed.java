package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.adapter.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.RatingBar;
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
public class Dealsfeed extends Fragment implements IXListViewListener {

	private static final String TAG = Dealsfeed.class.getSimpleName();
	private ListView listView;
	private XListView mListView;
	private DealsFeedListAdapter listAdapter;
	private List<DealsFeedItem> feedItems;
	private String URL_FEED = "http://54.201.62.76/facebookNews.json";
	private String FEED = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=";
	private String FEED_0 = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=0";
	
	private int MY_SOCKET_TIMEOUT_MS=50000;
	
	private Handler mHandler;
	private Double Lat=null;
	private Double Long=null;
	private int start = 0;
	public Double latitude=null;
	public Double longitude=null;
	public Integer currentHour=null;
	public Integer currentDay=null;
	public String user_id=null;
	private String eventFlag;
	private static int refreshCnt = 0;
	private static int cacheCount = 0;
	private static String latestTimeStamp = "0";
	private String URL_initial="http://54.68.32.118:8082/dataVenues?";
	//private String FEED_lat_long = URL_initial+"userID="+user_id+"&timestamp_Venues="+latestTimeStamp+"&currentDay="+currentDay+
		//	"&currentHour="+currentHour+"&userLatitude="+latitude+"&userLongitude="+longitude;
	private String FEED_lat_long="http://54.68.32.118/tejas/whack/venues.json";
	private ArrayList<String> listCacheName = new ArrayList<String>();
	GPSTracker gps;
	public SqlHandler sqlHandler;
	private ProgressBar progressBar;
	public String first=null;
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
		setHasOptionsMenu(true);
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
		long dtMili = System.currentTimeMillis();
		Calendar rightNow = Calendar.getInstance();				
		currentHour= rightNow.get(Calendar.HOUR_OF_DAY);
		currentDay= rightNow.get(Calendar.DAY_OF_WEEK);		
		FEED_lat_long="http://54.68.32.118/tejas/whack/venues.json";
//		FEED_lat_long = URL_initial+"userID="+user_id+"&timestamp_Venues="+latestTimeStamp+"&currentDay="+currentDay+
//				"&currentHour="+currentHour+"&userLatitude="+latitude+"&userLongitude="+longitude;
//		System.out.println("Roop Deal Details Oncreate"+FEED_lat_long);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Cursor cursor2 = sqlHandler.selectQuery("SELECT * FROM EVENTFLAG");
		if (cursor2 != null && cursor2.getCount() > 0) {
			if (cursor2.moveToFirst()) {
				do {
					// System.out.println("roop Deal ID: "+cursor2.getString(cursor.getColumnIndex("value")));
					eventFlag = cursor2.getString(cursor2.getColumnIndex("value"));
				} while (cursor2.moveToNext());
			}

		}

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.mainfeed, container, false);
		// listView = (ListView) rootView.findViewById(android.R.id.list);
		mListView = (XListView) rootView.findViewById(R.id.xListView);		
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#259b24"), android.graphics.PorterDuff.Mode.MULTIPLY);
		//Drawable p = getResources().getDrawable(R.drawable.progress);
		//progressBar.setProgressDrawable(p);
		mListView.setPullLoadEnable(true);
		mListView.setDivider(null);
		feedItems = new ArrayList<DealsFeedItem>();

		listAdapter = new DealsFeedListAdapter(getActivity(), R.id.xListView,
				feedItems);
		mListView.setAdapter(listAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
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
				
				Intent intent = new Intent(getActivity(), DemoDetailPageDeals.class);
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
		// System.out.println("roop Deal first time Lat= "+Lat);
		if(Lat==null)
		{
			System.out.println("roop Deal first time Lat="+Lat);
			getGPS(1);			
		}
		return rootView;
	}
	

/*	public void initialization(int flag) {
		
		if(flag==0)
			progressBar.setVisibility(View.VISIBLE);
		System.out.println("Roop Deal Details Initialisation"+FEED_lat_long);
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		//System.out.println("roop Deal: FEED_lat_long " + FEED_lat_long);
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
				//System.out.println("roop Deal: new Parse in cache");
				System.out.println("Roop Deal Details Entry Not null"+FEED_lat_long);
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
				System.out.println("Roop Deal Details Entry null fresh"+FEED_lat_long);
				FEED_lat_long = URL_initial+"userID="+user_id+"&timestamp_Venues="+latestTimeStamp+"&currentDay="+currentDay+
						"&currentHour="+currentHour+"&userLatitude="+latitude+"&userLongitude="+longitude;
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
									progressBar.setVisibility(View.GONE);
									listCacheName.add(FEED_lat_long);
									parseJsonFeed(response, 0);
								}
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println("Roop Deal Error In Initialization "+error.getMessage());
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
*/
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
				System.out.println("Deal Flag "+item.getDealFlag());
				item.setcanonicalUrl(feedObj.getString("canonicalUrl"));
				System.out.println("roop Deal: time: " + latestTimeStamp + "at 0 i="+i);
				if (feedObj.getString("formattedAddress").isEmpty() || feedObj.getString("formattedAddress").compareTo("null")==0) {
					item.setAddress(null);
				} else {
					item.setAddress(feedObj.getString("formattedAddress"));
				}
				if (feedObj.getString("phone").isEmpty() || feedObj.getString("phone").compareTo("null")==0) {
					item.setPhoneNumber(null);
				} else {
					item.setPhoneNumber(feedObj.getString("phone"));
				}
				JSONArray praseArray = feedObj.getJSONArray("phrases");
				String phrases="";
				for(int j=0;j<praseArray.length();j++)
				{
					JSONObject phraseObj = (JSONObject) praseArray.get(j);
					phrases +=phraseObj.getString("phrase");
					if(j!=praseArray.length()-1)
						phrases+=" | ";
				}
				System.out.println("roop Deal: time: " + latestTimeStamp + "at 1 i="+i);
				//System.out.println("roop Deal: time: " + phrases);
				if(praseArray.length()==0)
					item.setPhrase(null);
				else
					item.setPhrase(phrases);
				JSONArray dealArray = feedObj.getJSONArray("dealsArray");
				String array=dealArray.toString();
				item.setDealArray(array);
				System.out.println("roop Deal: time: " + latestTimeStamp + "at 2 i="+i);
				int Distance =feedObj.getInt("distance");
				String distance=Distance+" m";
				
				if(Distance>1000)
				{
					int p=(Distance/1000);
					int remainder=(Distance%1000)/100;
					distance=p+"."+remainder+" km";
				}
				if (feedObj.getInt("distance")==0 || feedObj.getString("distance").compareTo("null")==0) {
					item.setDistance(null);
				} else {
					item.setDistance(distance);
				}
				item.setDistance(distance);
				latestTimeStamp = feedObj.getString("timestamp");
				System.out.println("roop Deal: time: " + latestTimeStamp + "at 3 i="+i);
				// Main Image
				if (feedObj.getString("venuePhoto").isEmpty()  || feedObj.getString("venuePhoto").compareTo("null")==0) {
					item.setPictureFB(null);
				} else {
					item.setPictureFB(feedObj.getString("venuePhoto"));
				}
				if (feedObj.getString("name").isEmpty() || feedObj.getString("name").compareTo("null")==0) {
					item.setName(null);
				} else {
					item.setName(feedObj.getString("name"));
				}
				//Added
				JSONArray suggestionsArray = feedObj.getJSONArray("suggestionsArray");
				String array2=suggestionsArray.toString();
				item.setSuggestionArray(array2);
				//Added
				System.out.println("roop Deal: time: " + latestTimeStamp + "at i=15");
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
		if(flag==1)
				progressBar.setVisibility(View.VISIBLE);
		if(flag==0)
		{
			latitude=null;longitude=null;
		}
		long dtMili = System.currentTimeMillis();
		Calendar rightNow = Calendar.getInstance();				
		currentHour= rightNow.get(Calendar.HOUR_OF_DAY);
		currentDay= rightNow.get(Calendar.DAY_OF_WEEK);
		latestTimeStamp="0";
		FEED_lat_long="http://54.68.32.118/tejas/whack/venues.json";
		//FEED_lat_long = URL_initial+"userID="+user_id+"&timestamp_Venues="+latestTimeStamp+"&currentDay="+currentDay+
		//		"&currentHour="+currentHour+"&userLatitude="+latitude+"&userLongitude="+longitude;
		//AppController.getInstance().getRequestQueue().getCache().remove(FEED_lat_long);
		if(flag==0)
			System.out.println("Roop Deal Details Refresh without the GPS"+FEED_lat_long);
		if(flag==1){
			
			System.out.println("Roop Deal Details Refresh with the GPS"+FEED_lat_long);
		}
			
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, FEED_lat_long,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: refresh");
							listCacheName.clear();
							listCacheName.add(FEED_lat_long);
							System.out.println("Roop NewData Refresh:"+ response);
							parseJsonFeed(response, 0);
							progressBar.setVisibility(View.GONE);
							onLoad();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						progressBar.setVisibility(View.GONE);
						System.out.println("Roop Deal Error In Onrefreesh "+error.toString());
						onLoad();
						VolleyLog.d(TAG, "Error: " + error.getMessage());
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
		latitude=null;longitude=null;
		FEED_lat_long="http://54.68.32.118/tejas/whack/venues.json";
		//FEED_lat_long = URL_initial+"userID="+user_id+"&timestamp_Venues="+latestTimeStamp+"&currentDay="+currentDay+
			//	"&currentHour="+currentHour+"&userLatitude="+latitude+"&userLongitude="+longitude;
		
		String feed =FEED_lat_long;
		System.out.println("roopgundeep Deal: new" + feed);
		listCacheName.add(feed);
		System.out.println("Roop Deal Details LoadMore"+FEED_lat_long);
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				FEED_lat_long, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("Roop NewData loadMore:"+ response);
							parseJsonFeed(response, 1);
							onLoad();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("Roop Deal Error In LoadMore "+error.getMessage());
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						onLoad();
					}
				});	
		jsonReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
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
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			Lat=latitude;
			Long=longitude;
			Cache cache = AppController.getInstance().getRequestQueue().getCache();
			//cache.clear();
			//latitude=null;
			//longitude=null;
			System.out.println("roop GPS called first="+first);			
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