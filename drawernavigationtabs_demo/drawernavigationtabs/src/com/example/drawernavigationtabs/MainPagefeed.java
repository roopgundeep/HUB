package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.adapter.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drawernavigationtabs.*;
import com.example.drawernavigationtabs.adapter.MainFeedListAdapter;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.NewsFeedItem;
import com.example.drawernavigationtabs.view.XListView;
import com.example.drawernavigationtabs.view.XListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
public class MainPagefeed extends Fragment implements IXListViewListener {
	
	private static final String TAG = MainPagefeed.class.getSimpleName();
	private ListView listView;
	private XListView mListView;
	private MainFeedListAdapter listAdapter;
	private List<NewsFeedItem> feedItems;
	private String URL_FEED = "http://54.201.62.76/facebookNews.json";
	private String FEED="http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=";
	private String FEED_0 = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=0";
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private static int cacheCount = 0;
	private static String latestTimeStamp="0";
	private ArrayList<String> listCacheName= new ArrayList<String>();;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.mainfeed, container, false);
		//listView = (ListView) rootView.findViewById(android.R.id.list);
		
		mListView = (XListView)rootView.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mListView.setDivider(null);
		feedItems = new ArrayList<NewsFeedItem>();
		
		listAdapter = new MainFeedListAdapter(getActivity(),R.id.xListView, feedItems);
		mListView.setAdapter(listAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parnet, android.view.View view,
		            int position, long id) {
		    	
		    	//title
		    	TextView t= (TextView) view.findViewById(R.id.title);
		    	String title=t.getText().toString();
		    	
	        	//timestamp
	        	TextView time=(TextView) view.findViewById(R.id.timestamp);
	        	String timestamp = time.getText().toString();
	        	//description
	        	TextView des=(TextView) view.findViewById(R.id.description);
	        	String description = des.getText().toString();
	        	//SourceName
	        	TextView source=(TextView) view.findViewById(R.id.sourceName);
	        	String sourceName = source.getText().toString();
	        	
	        	//Source Imagelink
	        	TextView sourceImageView=(TextView) view.findViewById(R.id.sourceImageLink);
	        	String sourceImageLink = sourceImageView.getText().toString();
	        	//Main ImageLink
	        	TextView mainImageView=(TextView) view.findViewById(R.id.mainImageLink);
	        	String mainImageLink = mainImageView.getText().toString();
	        	
	        	Intent intent = new Intent(getActivity(), NewsDetail.class);
	        	intent.putExtra("title",title);
	        	//intent.putExtra("imageMain",Imagebitmap);
	        	intent.putExtra("timestamp",timestamp);
	        	intent.putExtra("sourceName",sourceName);
	        	//intent.putExtra("sourceName",sourceBitmap);
	        	//intent.putExtra("imageDrawable",imageDrawable);
	        	intent.putExtra("description",description);
	        	intent.putExtra("mainImageLink",mainImageLink);
	        	intent.putExtra("sourceImageLink",sourceImageLink);
	        	//System.out.println(title+ " date"+ pubDate );
	        	startActivity(intent);
		        //Toast.makeText(getActivity(), title,Toast.LENGTH_SHORT).show();
		    }

		});		    
		//listView.setAdapter(listAdapter);
		initialization();
		return rootView;		
	}
	
	public void initialization(){
		
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		
		if(listCacheName.isEmpty()){
			listCacheName.add(FEED_0);
			//cache.clear();
		}
		System.out.println("roop: new size: "+listCacheName.size());

		for(int i=0;i<listCacheName.size();i++){
			
			//System.out.println("roop: new i= "+i);
			String feed=listCacheName.get(i);
			//System.out.println("roop: new"+feed);
			Entry entry = cache.get(feed);
			
			if (entry != null) {
				System.out.println("roop: new Parse in cache");
				// fetch the data from cache
				try {
					String data = new String(entry.data, "UTF-8");
				//	System.out.println("roop"+data);
					try {
						parseJsonFeed(new JSONObject(data),1);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}
			else {
				// making fresh volley request and getting json
				System.out.println("roop: new initialisation fetching");
				JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
						FEED_0, null, new Response.Listener<JSONObject>() {
							
							@Override
							public void onResponse(JSONObject response) {
								VolleyLog.d(TAG, "Response: " + response.toString());
								System.out.println("roop: response");
								if (response != null) {
									System.out.println("roop: response not null");
									feedItems.clear();
									listCacheName.clear();
									listCacheName.add(FEED_0);
									parseJsonFeed(response,0);
								}
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println("roop: error:"+error.getMessage());
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
	
	private void parseJsonFeed(JSONObject response,int flag) {
		try {
			System.out.println("roop: in Parse");
			JSONArray feedArray = response.getJSONArray("news");
			
			System.out.println("roop: ParseFeed:"+feedArray.length());
			
			/*flag==1 means its loading more...so retaining previous tuples*/
			if(flag==0){
				feedItems.clear();
			}				
			System.out.println("roop: ParseFeed:"+feedArray.length());
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
				latestTimeStamp=feedObj.getString("timestamp");
				// Main Image
				if(feedObj.getString("pictureFB").isEmpty()){
					item.setPictureFB(null);
				}
				else{
					item.setPictureFB(feedObj.getString("pictureFB"));
				}				
				if(feedObj.getString("name").isEmpty()){
					item.setName(null);
				}
				else{
					item.setName(feedObj.getString("name"));					
				}				
				if(feedObj.getString("description").isEmpty()){
					item.setDescription(null);
				}
				else{
					item.setDescription(feedObj.getString("description"));
				}
				if(feedObj.getString("message").isEmpty()){					
					item.setMessage(null);
				}
				else{
					item.setMessage(feedObj.getString("message"));
				}
				if(feedObj.getString("link").isEmpty()){					
					item.setLink(null);
				}
				else{
					item.setLink(feedObj.getString("link"));
				}
				
				System.out.println("roop: time: "+latestTimeStamp+"at i=15");
				feedItems.add(item);
			}
			// notify data changes to list adapater
			listAdapter.notifyDataSetChanged();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void refreshItems() {
	
		Log.d("roop","roop: fetching");
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				FEED_0, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: refresh");
							listCacheName.clear();
							listCacheName.add(FEED_0);
							parseJsonFeed(response,0);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});
	
		// Adding request to volley request queue
		AppController.getInstance().addToRequestQueue(jsonReq);
		
	}
	private void loadMore() {
		
		
		String feed=FEED+latestTimeStamp;
		System.out.println("roop: new"+feed);
		listCacheName.add(feed);
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				listCacheName.get(listCacheName.size()-1), null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: load More");
							parseJsonFeed(response,1);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});
	
		
		// Adding request to volley request queue
		//Entry ent= ;
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
			//	listAdapter = new MainFeedListAdapter(getActivity(),android.R.id.list, feedItems);
			//	mListView.setAdapter(listAdapter);
				/*mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.list_item, items);
				mListView.setAdapter(mAdapter);*/
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
				//geneItems();
				loadMore();
				listAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
		
	}
	
}