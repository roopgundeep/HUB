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
import com.example.drawernavigationtabs.data.EventsFeedItem;
import com.example.drawernavigationtabs.detailpage.DemoDetailPageNews;
import com.example.drawernavigationtabs.view.XListView;
import com.example.drawernavigationtabs.view.XListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

@SuppressLint("NewApi")
public class FoodList extends Fragment implements IXListViewListener {

	public static final String TAG = FoodList.class.getSimpleName();
	private ListView listView;
	private XListView mListView;
	private FoodListAdapter listAdapter;
	private List<String> feedItems;
	private String URL_FEED = "http://54.201.62.76/facebookNews.json";
	private String FEED = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=";
	private String FEED_0 = "http://54.201.62.76:8080/data?userID=Tejas&node=Hyderabad&timestamp_General=0";
	private String FEED_lat_long = "http://54.201.62.76/homePage.json";
	private Handler mHandler;
	private Double Lat = null;
	private Double Long = null;
	private int start = 0;
	private static int refreshCnt = 0;
	private static int cacheCount = 0;
	private static String latestTimeStamp = "0";

	private ArrayList<String> listCacheName = new ArrayList<String>();
	GPSTracker gps;
	private ProgressBar progressBar;
	
	public static final void hello() {

	}
	public static FoodList newInstance() {
		return new FoodList();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setHasOptionsMenu(true);
	}
	@Override
    public void onDetach() {
    	// TODO Auto-generated method stub
    	super.onDetach();
    	Drawable back = getResources().getDrawable( R.drawable.actionbar_homepage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		getActivity().getActionBar().setTitle("Brthe");
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.mainfeed, container, false);
		// listView = (ListView) rootView.findViewById(android.R.id.list);
		Drawable back = getResources().getDrawable( R.drawable.actionbar_dealspage_background_top );
		getActivity().getActionBar().setBackgroundDrawable(back);
		mListView = (XListView) rootView.findViewById(R.id.xListView);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
		getActivity().getActionBar().setTitle("Looking For..");
		mListView.hidefooter();
		mListView.hideheader();
		
		mListView.setPullLoadEnable(false);
		mListView.setDivider(null);
		feedItems = new ArrayList<String>();

		listAdapter = new FoodListAdapter(getActivity(), R.id.xListView,
				feedItems);
		mListView.setAdapter(listAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parnet,
					android.view.View view, int position, long id) {
				TextView p = (TextView) view.findViewById(R.id.food_item);
				System.out.println("Search Query Roop food item:"+p.getText());
				Intent i = new Intent(getActivity(),SearchDealsfeed.class);
				i.putExtra("link",p.getText().toString());
				startActivity(i);
			}
		});
		initialization(0);
		return rootView;
	}

	public void initialization(int flag) {
		
		String[] food_categories = getResources().getStringArray(R.array.food_categories);
		for (int i = 0; i < food_categories.length; i++) {

			feedItems.add(food_categories[i]);
			
		}
		// notify data changes to list adapater
		listAdapter.notifyDataSetChanged();
	}


	private void refreshItems(int flag) {

		Log.d("roop Deal", "roop: fetching");
		if (flag == 1)
			progressBar.setVisibility(View.VISIBLE);
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				FEED_lat_long, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						VolleyLog.d(TAG, "Response: " + response.toString());
						if (response != null) {
							System.out.println("roop: ParseFeed: refresh");
							listCacheName.clear();
							listCacheName.add(FEED_lat_long);
							//parseJsonFeed(response, 0);
							progressBar.setVisibility(View.GONE);

						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressBar.setVisibility(View.GONE);
						VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});
		// Adding request to volley request queue
		AppController.getInstance().addToRequestQueue(jsonReq);
	}

	private void loadMore() {

		// String feed = FEED + latestTimeStamp;
		String feed = FEED_lat_long;
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
							//parseJsonFeed(response, 1);
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