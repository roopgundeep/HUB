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
import com.example.drawernavigationtabs.data.NewsFeedItem;
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
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class NewsfeedBusiness extends Fragment implements IXListViewListener {

	private static final String TAG = NewsfeedBusiness.class.getSimpleName();
	private ListView listView;
	private XListView mListView;
	private NewsFeedListAdapter listAdapter;
	private List<NewsFeedItem> feedItems;
	private String URL_FEED = "http://54.201.62.76/facebookNews.json";
	
	private String FEED = "http://54.68.32.118:8080/data?";
	private String user_id="1439486379672711";
	private static String latestTimeStamp = "0";
	private static String timeStamp = "&timestamp_Business=";
	//private String FEED_0= FEED+"userID="+user_id+timeStamp+latestTimeStamp;
	private String FEED_0= "http://54.68.32.118/business.json";
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private static int cacheCount = 0;
	
	private int MY_SOCKET_TIMEOUT_MS=50000;
	private ProgressBar progressBar;
	
	private ArrayList<String> listCacheName = new ArrayList<String>();

	public static final void hello() {

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SqlHandler sqlHandler = new SqlHandler(getActivity());
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
		 latestTimeStamp="0";
		 FEED_0= "http://54.68.32.118/business.json";	
		 //FEED_0= FEED+"userID="+user_id+timeStamp+latestTimeStamp;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.mainfeed, container, false);
		// listView = (ListView) rootView.findViewById(android.R.id.list);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#bdbdbd"), android.graphics.PorterDuff.Mode.MULTIPLY);
		
		mListView = (XListView) rootView.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mListView.setDivider(null);
		feedItems = new ArrayList<NewsFeedItem>();
		listAdapter = new NewsFeedListAdapter(getActivity(), R.id.xListView,
				feedItems);
		mListView.setAdapter(listAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parnet,
					android.view.View view, int position, long id) {
				
				System.out.println("Roop Onclick: 1");
				//Source Imagelink 
				TextView sourceImageView=(TextView)view.findViewById(R.id.sourceImageLink);
				String sourceImageLink = sourceImageView.getText().toString(); 
				//Main ImageLink
				TextView mainImageView=(TextView)view.findViewById(R.id.mainImageLink);
				String mainImageLink = mainImageView.getText().toString();
				TextView feed_category=(TextView)view.findViewById(R.id.feed_category);
				String category = feed_category.getText().toString();
				
				TextView TitleView=(TextView)view.findViewById(R.id.title);
				String title = TitleView.getText().toString();
				
				TextView DescriptionView=(TextView)view.findViewById(R.id.description);
				String description = DescriptionView.getText().toString();
				
				TextView TimestampView=(TextView)view.findViewById(R.id.timestamp);
				String timestamp = TimestampView.getText().toString();
				
				TextView fbFriendView1=(TextView)view.findViewById(R.id.friendPiclink1);
				String fbFriend1 = fbFriendView1.getText().toString();
				
				TextView fbFriendView2=(TextView)view.findViewById(R.id.friendPiclink2);
				String fbFriend2 = fbFriendView2.getText().toString();
				
				TextView fbFriendView3=(TextView)view.findViewById(R.id.friendPiclink3);
				String fbFriend3 = fbFriendView3.getText().toString();
				
				TextView linkView=(TextView)view.findViewById(R.id.link);
				String link = linkView.getText().toString();
				
				System.out.println("Roop Onclick: "+title);
				System.out.println("Roop Onclick: "+fbFriend1);
				System.out.println("Roop Onclick: "+fbFriend2);
				System.out.println("Roop Onclick: "+timestamp);
				System.out.println("Roop Onclick: "+category);
				System.out.println("Roop Onclick: "+mainImageLink);
				
				Intent intent = new Intent(getActivity(), DemoDetailPageNews.class);
				// * intent.putExtra("title",title);
				intent.putExtra("sourceImageLink",sourceImageLink);
				intent.putExtra("mainImageLink",mainImageLink);
				intent.putExtra("category",category);
				intent.putExtra("title",title);
				intent.putExtra("description",description);
				intent.putExtra("timestamp",timestamp);
				intent.putExtra("fbFriend1",fbFriend1);
				intent.putExtra("fbFriend2",fbFriend2);
				intent.putExtra("fbFriend3",fbFriend3);
				intent.putExtra("link",link);
				TextView suggestionView = (TextView) view.findViewById(R.id.suggestion);
				String suggestion = suggestionView.getText().toString();
				intent.putExtra("suggestion",suggestion);
				startActivity(intent);
			}

		});
		// listView.setAdapter(listAdapter);
		
		initialization();
		return rootView;
	}

	public void initialization() {

		Cache cache = AppController.getInstance().getRequestQueue().getCache();

		if (listCacheName.isEmpty()) {
			listCacheName.add(FEED_0);
			// cache.clear();
		}
		
		System.out.println("roop: new size: " + listCacheName.size());
		int i=listCacheName.indexOf(FEED_0);
		for (; i < listCacheName.size(); i++) {

			// System.out.println("roop: new i= "+i);
			String feed = listCacheName.get(i);
			System.out.println("roop List: new "+feed);
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
				progressBar.setVisibility(View.VISIBLE);
				System.out.println("roop News Detail : new initialisation fetching "+ FEED_0);
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
									progressBar.setVisibility(View.GONE);
								}
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println("roop: error:"
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

	private void parseJsonFeed(JSONObject response, int flag) {
		try {
			System.out.println("roop: in Parse");
			JSONArray feedArray = response.getJSONArray("news");

			System.out.println("roop: ParseFeed:" + feedArray.length());

			/* flag==1 means its loading more...so retaining previous tuples */
			if (flag == 0) {
				feedItems.clear();
			}
			System.out.println("roop: ParseFeed:" + feedArray.length());
			//int i=listCacheName.indexOf(FEED_0);
			for (int i=0; i < feedArray.length(); i++) {

				JSONObject feedObj = (JSONObject) feedArray.get(i);

				NewsFeedItem item = new NewsFeedItem();
				item.setTimeStamp(feedObj.getString("timestamp"));
				if(feedObj.getString("subCategory").isEmpty())
				{	
					item.setCategory(null);
				}
				else{
					item.setCategory(feedObj.getString("subCategory"));
				}
				
				//long now            = System.currentTimeMillis();
				//long minutesAgo     = now - (feedObj.getInt("freshness")*(60000));
				//String time=(String) DateUtils.getRelativeDateTimeString(getActivity(), minutesAgo, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
				//item.setFreshness(feedObj.getInt("freshness"));
				//String[] a=time.split(",");				
				item.setCreatedTime(feedObj.getString("freshness"));				
				item.setSourceName(feedObj.getString("sourceName"));
				item.setSourceImage(feedObj.getString("sourceImage"));
				if (feedObj.getString("image").isEmpty()) {
					item.setMainImage(null);
				} else {
					item.setMainImage(feedObj.getString("image"));
				}
				//Added
				JSONArray suggestionsArray = feedObj.getJSONArray("suggestionsArray");
				String array2=suggestionsArray.toString();
				item.setSuggestionArray(array2);
				//Added
				item.setName(feedObj.getString("title"));				
				item.setLink(feedObj.getString("link"));
				if (feedObj.getString("description").isEmpty()) {
					item.setDescription(feedObj.getString("message"));
				} else {
					item.setDescription(feedObj.getString("description"));
				}
				
				item.setFacebookFriend1(feedObj.getString("facebookFriend1"));
				item.setFacebookFriend2(feedObj.getString("facebookFriend2"));
				item.setFacebookFriend3(feedObj.getString("facebookFriend3"));
				latestTimeStamp = feedObj.getString("timestamp");
				System.out.println("roop Home in in: 3 ");
				feedItems.add(item);
			}
			// notify data changes to list adapater
			listAdapter.notifyDataSetChanged();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void refreshItems() {

		Log.d("roop", "roop: fetching");
		latestTimeStamp="0";
		FEED_0= "http://54.68.32.118/business.json";
		//FEED_0= FEED+"userID="+user_id+timeStamp+latestTimeStamp;
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, FEED_0,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: refresh");
							listCacheName.clear();
							listCacheName.add(FEED_0);
							parseJsonFeed(response, 0);
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
		AppController.getInstance().addToRequestQueue(jsonReq);

	}

	private void loadMore() {

		FEED_0= "http://54.68.32.118/business.json";
		//FEED_0= FEED+"userID="+user_id+timeStamp+latestTimeStamp;	
		String feed = FEED_0;
		System.out.println("roop: new" + feed);		
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
				refreshItems();
				// mAdapter.notifyDataSetChanged();
				// listAdapter = new
				// FeedListAdapter(getActivity(),android.R.id.list, feedItems);
				// mListView.setAdapter(listAdapter);
				/*
				 * mAdapter = new ArrayAdapter<String>(XListViewActivity.this,
				 * R.layout.list_item, items); mListView.setAdapter(mAdapter);
				 */
			//	onLoad();
			}
		}, 10000);

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
				//onLoad();
			}
		}, 2000);

	}

}