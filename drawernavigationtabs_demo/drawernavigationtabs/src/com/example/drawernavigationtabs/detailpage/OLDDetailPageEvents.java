package com.example.drawernavigationtabs.detailpage;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.NetworkImageViewSquare;
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.WebviewActivity;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import uk.co.chrisjenx.paralloid.Parallaxor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class OLDDetailPageEvents extends FragmentActivity {

	private UiLifecycleHelper uiHelper;
	public static String SetLink;
	public static String SetPicture;
	public static String SetDescription;
	public static String SetName;
	public static String SetCaption;
	public static SqlHandler sqlHandler;
	public static String deals="0";
	public static String news="1";
	public static String events="0";
	public static String FEED_lat_long="http://54.68.32.118/fakeRequest.json";
	public static ProgressBar progressBar;
	public static Activity a;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sqlHandler = new SqlHandler(this);
		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM FLAG");
		if (cursor != null && cursor.getCount() > 0) {			
			if (cursor.moveToFirst()) {
				do {
					System.out.println("roop FLAG DB: "
							+ cursor.getInt(cursor.getColumnIndex("news")));
					news=cursor.getString(cursor.getColumnIndex("news"));
					deals=cursor.getString(cursor.getColumnIndex("deals"));
					events=cursor.getString(cursor.getColumnIndex("events"));
				} while (cursor.moveToNext());
			}

		}
		a=this;
		setContentView(R.layout.detail_page_framelayout);
		getActionBar().show();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        Drawable back = getResources().getDrawable( R.drawable.actionbar_eventspage_background_top );
		getActionBar().setBackgroundDrawable(back);
        uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	
	}
	public void shareCliked(){

		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
		.setLink(SetLink)
		.setPicture(SetPicture)
		.setName(SetName).setCaption(SetCaption)
		.setDescription(SetDescription).build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
	}
	public static void likeCliked(){
		events="1";
		String query = "INSERT INTO FLAG(news , deals , events ) "
				+ "values ("
				+ news+ ", "+ deals+ ", "+ events+ ")";
		System.out.println("roop DB "+query);
		System.out.println("Roop DB query query " + query);
		sqlHandler.executeQuery(query);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.share:
			shareCliked();
			break;
		case R.id.like:
			item.setIcon(R.drawable.liked);
			//likeCliked();
			break;
		case android.R.id.home:
	        finish();	
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sharelike_menu, menu);
		return true;
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View convertView = inflater.inflate(R.layout.events_detail_parallax, container, false);
			
			FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);
			TextView descriptionView=(TextView) convertView.findViewById(R.id.description);
			progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
			progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#e72e34"), android.graphics.PorterDuff.Mode.MULTIPLY);
			TextView categoryView=(TextView) convertView.findViewById(R.id.feed_category);
			 
			TextView titleView=(TextView) convertView.findViewById(R.id.title);
			TextView sourceNameView=(TextView) convertView.findViewById(R.id.sourceName);
			TextView addressView=(TextView) convertView.findViewById(R.id.address);
			TextView timeView=(TextView) convertView.findViewById(R.id.Time);
			TextView link_url=(TextView) convertView.findViewById(R.id.link_url);
			LinearLayout readMore=(LinearLayout) convertView.findViewById(R.id.readMore);
			
			
			String mainImageLink= getActivity().getIntent().getStringExtra("mainImageLink");
			String title= getActivity().getIntent().getStringExtra("title");
			String description= getActivity().getIntent().getStringExtra("description");
			String link= getActivity().getIntent().getStringExtra("link");
			String address= getActivity().getIntent().getStringExtra("address");
			String organiserName= getActivity().getIntent().getStringExtra("organiserName");
			String endDate= getActivity().getIntent().getStringExtra("endDate");
			String feed_category= getActivity().getIntent().getStringExtra("feed_category");
			String venue= getActivity().getIntent().getStringExtra("venue");
			String startDate= getActivity().getIntent().getStringExtra("startDate");
			if(feed_category==null)
				categoryView.setVisibility(View.GONE);
			SetPicture=mainImageLink;
			SetLink=link;
			SetName=title;
			SetDescription=description;
			SetCaption=startDate+"-"+endDate;
			
			
			feedImageView.setImageUrl(mainImageLink, imageLoader);
			timeView.setText(startDate+" - "+endDate);
			addressView.setText(venue+", "+address);
			System.out.println("Roop Event Org "+organiserName);
			if(organiserName==null || organiserName.isEmpty())
				sourceNameView.setVisibility(View.GONE);
			if((venue.isEmpty() ||venue==null) && (address ==null && address.isEmpty()))
				addressView.setVisibility(View.GONE);
			sourceNameView.setText("By "+organiserName);
			titleView.setText(title);
			categoryView.setText(feed_category);
			link_url.setText(link);			
			descriptionView.setText(description);
			readMore.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					
					TextView p = (TextView)view.findViewById(R.id.link_url);
					String p_url = (String) p.getText();
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_url));
					startActivity(browserIntent);
					//System.out.println("roopgundeep click: event More:url"+p_url);
					//progressBar.setVisibility(View.VISIBLE);
					//BookTicket();
					//GoToWebView(p_url,getActivity());
				}
			});
			RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.image_view_container_RL);
			ScrollView scrollView = (ScrollView) convertView.findViewById(R.id.description_view_PSV);
			if(scrollView instanceof Parallaxor) {
				((Parallaxor) scrollView).parallaxViewBy(rl, 0.5f);
			}
			return convertView;
		}
	}
	public static void GoToWebView(String url,Activity a)
	{
		Intent intent = new Intent(a, WebviewActivity.class);
		intent.putExtra("url", url);
		a.startActivity(intent);
	}
	private static void BookTicket() {
		likeCliked();
		// String feed = FEED + latestTimeStamp;
		String feed = FEED_lat_long;
		System.out.println("roopgundeep Events: new" + feed);
		
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
				feed, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						
						if (response != null) {
							System.out.println("roop: ParseFeed: load More");
							//parseJsonFeed(response, 1);
							System.out.println("roopgundeep Events: new"+response );
							progressBar.setVisibility(View.GONE);
							
						}
						progressBar.setVisibility(View.GONE);
						Toast.makeText(a, 
	                               "Ticket Booked Successfully", Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressBar.setVisibility(View.GONE);
						System.out.println("roopgundeep Events error: new" );
						Toast.makeText(a, 
	                               "Ticket Booked Successfully", Toast.LENGTH_LONG).show();
						//VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});
		AppController.getInstance().addToRequestQueue(jsonReq);
	}
}
