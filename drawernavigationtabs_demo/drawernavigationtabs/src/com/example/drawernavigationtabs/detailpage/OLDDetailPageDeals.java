package com.example.drawernavigationtabs.detailpage;

import java.sql.Date;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import android.text.format.DateFormat;
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

public class OLDDetailPageDeals extends FragmentActivity {

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
	public static ProgressBar progressBar;
	public static String FEED_lat_long="http://54.68.32.118/fakeRequest.json";
	public static Activity a;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_page_framelayout);
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
		getActionBar().show();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        Drawable back = getResources().getDrawable( R.drawable.actionbar_dealspage_background_top );
		getActionBar().setBackgroundDrawable(back);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
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
		.setName(SetName)
		.setCaption(SetDescription).build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
	}
	public static void likeCliked(){
		deals="1";
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
			View convertView = inflater.inflate(R.layout.deals_detail_parallax, container, false);
			
			FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);
			NetworkImageView fbFriendPic1 = (NetworkImageView) convertView.findViewById(R.id.friendPic1);
			NetworkImageView fbFriendPic2 = (NetworkImageView) convertView.findViewById(R.id.friendPic2);
			NetworkImageView fbFriendPic3 = (NetworkImageView) convertView.findViewById(R.id.friendPic3);
			progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
			progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#8bc34a"), android.graphics.PorterDuff.Mode.MULTIPLY);
			LinearLayout readMore=(LinearLayout) convertView.findViewById(R.id.readMore);
			LinearLayout tag_layout=(LinearLayout) convertView.findViewById(R.id.tag_layout);
			LinearLayout address_layout=(LinearLayout) convertView.findViewById(R.id.address_layout);
			
			LinearLayout phone_layout=(LinearLayout) convertView.findViewById(R.id.phone_layout);
			
			LinearLayout drive_layout=(LinearLayout) convertView.findViewById(R.id.drive_layout);
			
			TextView categoryView=(TextView) convertView.findViewById(R.id.feed_category);
			TextView titleView=(TextView) convertView.findViewById(R.id.title);
			TextView tags=(TextView) convertView.findViewById(R.id.tags);
			TextView address=(TextView) convertView.findViewById(R.id.address);
			TextView PhoneNumber =(TextView) convertView.findViewById(R.id.phone_number);
			TextView drive_dist =(TextView) convertView.findViewById(R.id.drive_dist);
			readMore.setVisibility(View.GONE);
//			readMore.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					
//					TextView p = (TextView)view.findViewById(R.id.link_url);
//					String p_url = (String) p.getText();
//					System.out.println("roopgundeep click: event More:url"+p_url);
//					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_url));
//					startActivity(browserIntent);
//					//progressBar.setVisibility(View.VISIBLE);
//					//BookTicket();
//					//GoToWebView(p_url,getActivity());
//				}
//			});
//			
			String VenueName= getActivity().getIntent().getStringExtra("VenueName");
			String distance= getActivity().getIntent().getStringExtra("distance");
			String feed_category= getActivity().getIntent().getStringExtra("feed_category");			
			String mainImageLink= getActivity().getIntent().getStringExtra("feedImage_url");
			
			String fbFriend1= getActivity().getIntent().getStringExtra("friendPic1_url");
			String fbFriend2= getActivity().getIntent().getStringExtra("friendPic2_url");
			String fbFriend3= getActivity().getIntent().getStringExtra("friendPic3_url");
			String dealArray= getActivity().getIntent().getStringExtra("dealArray");
			String addressContent= getActivity().getIntent().getStringExtra("address");
			String phone= getActivity().getIntent().getStringExtra("phone");
			String phrases= getActivity().getIntent().getStringExtra("phrases");
			//String cannonical_url= getActivity().getIntent().getStringExtra("");
			if(feed_category==null || feed_category.isEmpty() )
				categoryView.setVisibility(View.GONE);
			if(phrases==null || phrases.isEmpty())
				tag_layout.setVisibility(View.GONE);
			if(phone==null || phone.isEmpty()){
				phone_layout.setVisibility(View.GONE);
			}			
			if(addressContent==null || addressContent.isEmpty())
				address_layout.setVisibility(View.GONE);
			if(distance==null || distance.isEmpty())
				drive_layout.setVisibility(View.GONE);
			SetPicture=mainImageLink;
			SetName=VenueName;
			SetDescription=phrases;
			SetLink=getActivity().getIntent().getStringExtra("canonicalUrl");
//			public static String SetLink;
//			public static String SetPicture;
//			public static String SetDescription;
//			public static String SetName;
//			public static String SetCaption;
			try {
				JSONArray array = new JSONArray (dealArray);
				System.out.println("Roop Deal Detail len="+array.length());
				for(int i=0;i<array.length();i++)
				{
					JSONObject jObject = array.getJSONObject(i);

					String link = jObject.getString("link");
					String title = jObject.getString("title");
					String price = jObject.getString("price");
					String discount = jObject.getString("savingPercent");
					String sourceImage = jObject.getString("sourceLogo");
					String count = jObject.getString("soldAmount");
			        if(i==0)
			        {
			        	LinearLayout offer_layout1 = (LinearLayout) convertView.findViewById(R.id.offer_layout1);
			        	NetworkImageViewSquare offer_image = (NetworkImageViewSquare)convertView.findViewById(R.id.offer_image1);
			        	TextView offer_title= (TextView)convertView.findViewById(R.id.offer_title1);
			        	System.out.println("Roop Deal Detail i="+i);
			        	TextView saving= (TextView)convertView.findViewById(R.id.saving1);
			        	System.out.println("Roop Deal Detail i="+i);
			        	TextView discountView= (TextView)convertView.findViewById(R.id.discount1);
			        	TextView countView= (TextView)convertView.findViewById(R.id.count1);
			        	TextView linkView= (TextView)convertView.findViewById(R.id.link1);
			        	System.out.println("Roop Deal Detail i="+i);
			        	linkView.setText(link);
			        	offer_layout1.setVisibility(View.VISIBLE);
			        	offer_image.setImageUrl(sourceImage, imageLoader);
			        	offer_title.setText(title);
			        	saving.setText(price);
			        	discountView.setText(discount);
			        	countView.setText("Sold: "+count);
			        	
			        	offer_layout1.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								System.out.println("roopgundeep click: news More:");
								TextView p = (TextView)view.findViewById(R.id.link1);
								String p_url = (String) p.getText();
								Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_url));
								startActivity(browserIntent);
								//GoToWebView(p_url,getActivity());
							}
						});
			        	
			        }
			        else if(i==1)
			        {
			        	LinearLayout offer_layout2 = (LinearLayout) convertView.findViewById(R.id.offer_layout2);
			        	NetworkImageViewSquare offer_image = (NetworkImageViewSquare)convertView.findViewById(R.id.offer_image2);
			        	TextView offer_title= (TextView)convertView.findViewById(R.id.offer_title2);
			        	TextView saving= (TextView)convertView.findViewById(R.id.saving2);
			        	TextView discountView= (TextView)convertView.findViewById(R.id.discount2);
			        	TextView countView= (TextView)convertView.findViewById(R.id.count2);
			        	TextView linkView= (TextView)convertView.findViewById(R.id.link2);
			        	linkView.setText(link);
			        	offer_layout2.setVisibility(View.VISIBLE);
			        	offer_image.setImageUrl(sourceImage, imageLoader);
			        	offer_title.setText(title);
			        	saving.setText(price);
			        	discountView.setText(discount);
			        	countView.setText("Sold: "+count);
			        	offer_layout2.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								System.out.println("roopgundeep click: news More:");
								TextView p = (TextView)view.findViewById(R.id.link2);
								String p_url = (String) p.getText();
								Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_url));
								startActivity(browserIntent);
								//GoToWebView(p_url,getActivity());
							}
						});
			        	
			        }
			        else if(i==2)
			        {
			        	LinearLayout offer_layout3 = (LinearLayout) convertView.findViewById(R.id.offer_layout3);
			        	NetworkImageViewSquare offer_image = (NetworkImageViewSquare)convertView.findViewById(R.id.offer_image3);
			        	TextView offer_title= (TextView)convertView.findViewById(R.id.offer_title3);
			        	TextView saving= (TextView)convertView.findViewById(R.id.saving3);
			        	TextView discountView= (TextView)convertView.findViewById(R.id.discount3);
			        	TextView countView= (TextView)convertView.findViewById(R.id.count3);
			        	TextView linkView= (TextView)convertView.findViewById(R.id.link3);
			        	linkView.setText(link);
			        	offer_layout3.setVisibility(View.VISIBLE);
			        	offer_image.setImageUrl(sourceImage, imageLoader);
			        	offer_title.setText(title);
			        	saving.setText(price);
			        	discountView.setText(discount);
			        	countView.setText("Sold: "+count);
			        	offer_layout3.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								System.out.println("roopgundeep click: news More:");
								TextView p = (TextView)view.findViewById(R.id.link3);
								String p_url = (String) p.getText();
								Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_url));
								startActivity(browserIntent);
								//GoToWebView(p_url,getActivity());
							}
						});
			        	
			        }
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			feedImageView.setImageUrl(mainImageLink, imageLoader);
			if(fbFriend1==null)
				fbFriendPic1.setVisibility(View.GONE);
			else
				fbFriendPic1.setImageUrl(fbFriend1, imageLoader);
			if(fbFriend2==null)
				fbFriendPic2.setVisibility(View.GONE);
			else
				fbFriendPic2.setImageUrl(fbFriend2, imageLoader);
			if(fbFriend3==null)
				fbFriendPic3.setVisibility(View.GONE);
			else
				fbFriendPic3.setImageUrl(fbFriend3, imageLoader);
			
			categoryView.setText(feed_category);
			drive_dist.setText(distance);
			titleView.setText(VenueName);
			address.setText(addressContent);
			PhoneNumber.setText(phone);
			tags.setText(phrases);
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
	                               "Table Booked Successfully", Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressBar.setVisibility(View.GONE);
						System.out.println("roopgundeep Events error: new" );
						Toast.makeText(a, 
	                               "Table Booked Successfully", Toast.LENGTH_LONG).show();
						//VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});
		AppController.getInstance().addToRequestQueue(jsonReq);
	}
}
