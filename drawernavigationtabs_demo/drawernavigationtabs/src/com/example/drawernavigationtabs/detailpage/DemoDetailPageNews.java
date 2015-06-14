package com.example.drawernavigationtabs.detailpage;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

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
import android.text.format.DateUtils;
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

public class DemoDetailPageNews extends FragmentActivity {

	private UiLifecycleHelper uiHelper;
	public static String SetLink;
	public static String SetPicture;
	public static String SetDescription;
	public static String SetName;
	public static String SetCaption;
	public static SqlHandler sqlHandler;
	public static View a;
	public static View av;
	public static ScrollView scrollView;
	public static ProgressBar progressBar;
	public static String FEED_lat_long="http://54.68.32.118/fakeRequest.json";
	public String deals="0";
	public String news="1";
	public String events="0";
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
		
		setContentView(R.layout.detail_page_framelayout);
		getActionBar().show();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        Drawable back = getResources().getDrawable( R.drawable.actionbar_newspage_background_top);
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
	public void likeCliked(){
		news="1";
		String query = "INSERT INTO FLAG(news , deals , events ) "
				+ "values ("
				+ news+ ", "+ deals+ ", "+ events+ ")";
		System.out.println("roop DB "+query);
		System.out.println("Roop DB query query " + query);
		sqlHandler.executeQuery(query);
		a.findViewById(R.id.recommendation).setVisibility(View.VISIBLE);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.share:
			shareCliked();
			break;
		case R.id.like:			
			item.setIcon(R.drawable.liked);			
			likeCliked();
			BookTicket();
			break;
		case android.R.id.home:
	        finish();
	       
	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;	
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
			View convertView = inflater.inflate(R.layout.news_detail_parallax, container, false);
			
			a=convertView;
			FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);
			TextView descriptionView=(TextView) convertView.findViewById(R.id.description);
			NetworkImageView fbFriendPic1 = (NetworkImageView) convertView.findViewById(R.id.friendPic1);
			NetworkImageView fbFriendPic2 = (NetworkImageView) convertView.findViewById(R.id.friendPic2);
			NetworkImageView fbFriendPic3 = (NetworkImageView) convertView.findViewById(R.id.friendPic3);
			NetworkImageViewSquare sourcePic = (NetworkImageViewSquare) convertView.findViewById(R.id.sourcePic);
			TextView categoryView=(TextView) convertView.findViewById(R.id.feed_category);
			TextView titleView=(TextView) convertView.findViewById(R.id.title);
			TextView timestampView=(TextView) convertView.findViewById(R.id.timestamp);
			TextView link_url=(TextView) convertView.findViewById(R.id.link_url);
			LinearLayout readMore=(LinearLayout) convertView.findViewById(R.id.readMore);
			progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
			progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#bdbdbd"), android.graphics.PorterDuff.Mode.MULTIPLY);
			String title= getActivity().getIntent().getStringExtra("title");
			String description= getActivity().getIntent().getStringExtra("description");
			String timestamp= getActivity().getIntent().getStringExtra("timestamp");
			String sourceName= getActivity().getIntent().getStringExtra("sourceName");
			String mainImageLink= getActivity().getIntent().getStringExtra("mainImageLink");
			String sourceImageLink= getActivity().getIntent().getStringExtra("sourceImageLink");
			String category= getActivity().getIntent().getStringExtra("category");
			String link= getActivity().getIntent().getStringExtra("link");
			String fbFriend1= getActivity().getIntent().getStringExtra("fbFriend1");
			String fbFriend2= getActivity().getIntent().getStringExtra("fbFriend2");
			String fbFriend3= getActivity().getIntent().getStringExtra("fbFriend3");
			if(category==null || category.isEmpty())
				categoryView.setVisibility(View.GONE);
			SetName=title;
			SetLink=link;
			SetPicture=mainImageLink;
			SetCaption=category;
			SetDescription=description;
			System.out.println("Dummy news fb1"+fbFriend1+"fb2="+fbFriend2+"fb3="+fbFriend3);
			feedImageView.setImageUrl(mainImageLink, imageLoader);
			sourcePic.setImageUrl(sourceImageLink, imageLoader);
			if(fbFriend1.compareTo("null")==0 || fbFriend1==null)
				fbFriendPic1.setVisibility(View.GONE);
			else
				fbFriendPic1.setImageUrl(fbFriend1, imageLoader);
			if(fbFriend2.compareTo("null")==0 ||fbFriend2==null)
				fbFriendPic2.setVisibility(View.GONE);
			else
				fbFriendPic2.setImageUrl(fbFriend2, imageLoader);
			if(fbFriend3.compareTo("null")==0 || fbFriend3==null)
				fbFriendPic3.setVisibility(View.GONE);
			else
				fbFriendPic3.setImageUrl(fbFriend3, imageLoader);
			
			categoryView.setText(category);
			titleView.setText(title);
			link_url.setText(link);
			timestampView.setText(timestamp);
			descriptionView.setText(description);
			readMore.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: news More:");
					TextView p = (TextView)view.findViewById(R.id.link_url);
					String p_url = (String) p.getText();
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_url));
					startActivity(browserIntent);
					
					//GoToWebView(p_url,getActivity());
				}
			});
			String suggestion= getActivity().getIntent().getStringExtra("suggestion");
			if(!suggestion.isEmpty() || suggestion!=null)
			{
				try {
					JSONArray array = new JSONArray (suggestion);
					System.out.println("Array length"+array.length() +"  Suggestion= "+suggestion);
					for(int i=0;i<array.length();i++)
					{
						JSONObject jObject = array.getJSONObject(i);						
						String suggestion_categoryType = jObject.getString("categoryType");						
						String suggestion_title = jObject.getString("title");					
						String suggestion_image = jObject.getString("image");
						String time = jObject.getString("time");
						JSONObject detailsArray = jObject.getJSONObject("data");
						String details=detailsArray.toString();
						
						if(i==0)
						{
							NetworkImageViewSquare rec_image = (NetworkImageViewSquare)convertView.findViewById(R.id.rec_image1);
							TextView title_view= (TextView)convertView.findViewById(R.id.rec_title1);
							TextView rec_time= (TextView)convertView.findViewById(R.id.rec_time1);
							TextView data= (TextView)convertView.findViewById(R.id.rec_data1);
							TextView type= (TextView)convertView.findViewById(R.id.rec_type1);
							RelativeLayout rec_layout1 = (RelativeLayout) convertView.findViewById(R.id.rec_layout1);
							
							rec_image.setImageUrl(suggestion_image, imageLoader);
							title_view.setText(suggestion_title);
							rec_time.setText(time);
							data.setText(details);
							type.setText(suggestion_categoryType); //default it was categoryType
							rec_layout1.setVisibility(View.VISIBLE);
							rec_layout1.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View view) {
									System.out.println("roopgundeep click: news More0 :");
									TextView dataView = (TextView)view.findViewById(R.id.rec_data1);
									String data=dataView.getText().toString();
									System.out.println("roopgundeep click: news More1 :");
									TextView typeView = (TextView)view.findViewById(R.id.rec_type1);
									String type=typeView.getText().toString();
									System.out.println("roopgundeep click: news More2: ");
									try {
										GoToActivity(data,type,getActivity());
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//GoToWebView(p_url,getActivity());
								}
							});
						}
						if(i==1)
						{
							NetworkImageViewSquare rec_image = (NetworkImageViewSquare)convertView.findViewById(R.id.rec_image2);
							TextView title_view= (TextView)convertView.findViewById(R.id.rec_title2);
							TextView rec_time= (TextView)convertView.findViewById(R.id.rec_time2);
							TextView data= (TextView)convertView.findViewById(R.id.rec_data2);
							TextView type= (TextView)convertView.findViewById(R.id.rec_type2);
							RelativeLayout rec_layout2 = (RelativeLayout) convertView.findViewById(R.id.rec_layout2);
							
							rec_image.setImageUrl(suggestion_image, imageLoader);
							title_view.setText(suggestion_title);
							rec_time.setText(time);
							data.setText(details);
							type.setText(suggestion_categoryType);
							rec_layout2.setVisibility(View.VISIBLE);
							
							rec_layout2.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View view) {
									System.out.println("roopgundeep click: news More:");
									TextView dataView = (TextView)view.findViewById(R.id.rec_data2);
									String data=dataView.getText().toString();
									
									TextView typeView = (TextView)view.findViewById(R.id.rec_type2);
									String type=typeView.getText().toString();
									try {
										GoToActivity(data,type,getActivity());
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//GoToWebView(p_url,getActivity());
								}
							});
						}
						
						if(i==2)
						{
							System.out.println("For i=2");
							NetworkImageViewSquare rec_image = (NetworkImageViewSquare)convertView.findViewById(R.id.rec_image3);
							TextView title_view= (TextView)convertView.findViewById(R.id.rec_title3);
							TextView rec_time= (TextView)convertView.findViewById(R.id.rec_time3);
							TextView data= (TextView)convertView.findViewById(R.id.rec_data3);
							TextView type= (TextView)convertView.findViewById(R.id.rec_type3);
							RelativeLayout rec_layout3 = (RelativeLayout) convertView.findViewById(R.id.rec_layout3);
							rec_layout3.setVisibility(View.VISIBLE);
							rec_image.setImageUrl(suggestion_image, imageLoader);
							title_view.setText(suggestion_title);
							rec_time.setText(time);
							data.setText(details);
							type.setText(suggestion_categoryType);
							
							rec_layout3.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View view) {
									System.out.println("roopgundeep click: news More:");
									TextView dataView = (TextView)view.findViewById(R.id.rec_data3);
									String data=dataView.getText().toString();
									
									TextView typeView = (TextView)view.findViewById(R.id.rec_type3);
									String type=typeView.getText().toString();
									System.out.println("hello type"+type);
									try {
										GoToActivity(data,type,getActivity());
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//GoToWebView(p_url,getActivity());
								}
							});
						}
						
					}	
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.image_view_container_RL);
			scrollView = (ScrollView) convertView.findViewById(R.id.description_view_PSV);
			if(scrollView instanceof Parallaxor) {
				((Parallaxor) scrollView).parallaxViewBy(rl, 0.5f);
			}
			return convertView;
		}
		public  void GoToActivity(String data,String type,Activity a) throws JSONException
		{
			System.out.println("roopgundeep click: news More" + type);
			if(type.compareTo("news")==0)
			{
				System.out.println("roopgundeep click: news More typoe is news: " + type);
				NewsActivity(data,a);
			}
			else if(type.compareTo("events")==0)
			{
				System.out.println("hello type type ="+type);
				EventsActivity(data,a);
			}
			else if(type.compareTo("venues")==0)
			{
				VenuesActivity(data,a);
			}
		}
		public void NewsActivity(String data,Activity activity) throws JSONException
		{
			//JSONObject feedObj = (JSONObject) feedArray.get(i);
			JSONObject feedObj = new JSONObject(data);		
			
			String fbFriend1 = feedObj.getString("facebookFriend1");
			String fbFriend2 = feedObj.getString("facebookFriend2");
			String fbFriend3 = feedObj.getString("facebookFriend3");
			String description = feedObj.getString("description");
			String category = feedObj.getString("category");
			String link = feedObj.getString("link");
			String mainImageLink = feedObj.getString("image");
			String title = feedObj.getString("title");
			String sourceImageLink = feedObj.getString("sourceImage");
			
			//long now = System.currentTimeMillis();
			//long minutesAgo     = now - (feedObj.getInt("freshness")*(60000));
			//String time=(String) DateUtils.getRelativeDateTimeString(activity, minutesAgo, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
			
			//String[] a=time.split(",");	
			String timestamp = feedObj.getString("freshness");
			String suggestion= feedObj.getString("suggestionsArray");
			System.out.println("roopgundeep click: news More typoe in news1");
			Intent intent = new Intent(activity, DemoDetailPageNews.class);
			// * intent.putExtra("title",title);
			System.out.println("sourceImageLink"+sourceImageLink);
			System.out.println("roopgundeep click: news More typoe in news2");
			intent.putExtra("sourceImageLink",sourceImageLink);
			intent.putExtra("mainImageLink",mainImageLink);
			System.out.println("roopgundeep click: news More typoe in news3");
			intent.putExtra("category",category);
			intent.putExtra("title",title);
			System.out.println("roopgundeep click: news More typoe in news4");
			intent.putExtra("description",description);
			intent.putExtra("timestamp",timestamp);
			intent.putExtra("fbFriend1",fbFriend1);
			System.out.println("roopgundeep click: news More typoe in news5");
			intent.putExtra("fbFriend2",fbFriend2);
			intent.putExtra("fbFriend3",fbFriend3);
			intent.putExtra("suggestion",suggestion);
			System.out.println("roopgundeep click: news More typoe in news6");
			intent.putExtra("link",link);
			System.out.println("roopgundeep click: news More typoe in news7");
			activity.startActivity(intent);
			activity.finish();
		}
		public void EventsActivity(String data,Activity activity) throws JSONException
		{
			String ids1 = "0";
			ids1 = '"' + ids1 + '"';
			String query1 = "INSERT INTO EVENTFLAG( value ) " + "values ("
					+ ids1 + ")";
			System.out.println("roop DB " + query1);
			System.out.println("Roop DB query fb query " + query1);
			sqlHandler.executeQuery(query1);
			//JSONObject feedObj = (JSONObject) feedArray.get(i);
			JSONObject feedObj = new JSONObject(data);		
			String feed_category=feedObj.getString("category");
			System.out.println("hello type type =1");
			String address=feedObj.getString("formattedAddress");
			System.out.println("hello type type =2");
			SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date parsed = null;
			try {
				parsed = (Date) sourceFormat.parse(feedObj.getString("startTime"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // => Date is in UTC now
			Calendar cal = Calendar.getInstance();
			TimeZone tz = cal.getTimeZone();
			SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyy HH:mm");
			destFormat.setTimeZone(tz);
			String result = destFormat.format(parsed);
			String startDate=result;
			System.out.println("hello type type =3");
			Date parsed_end = null;
			try {
				parsed_end = (Date) sourceFormat.parse(feedObj.getString("endTime"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // => Date is in UTC now
			System.out.println("hello type type =4");
			SimpleDateFormat destFormat_end = new SimpleDateFormat("dd MMM yyy HH:mm");
			destFormat_end.setTimeZone(tz);
			String result_end = destFormat.format(parsed);
			String endDate= result_end;
			String link=feedObj.getString("link");
			System.out.println("hello type type =5");
			String venue=feedObj.getString("venueName");
			String organiserName=feedObj.getString("organizerName");
			String title= feedObj.getString("title");
			System.out.println("hello type type =6");
			String description= feedObj.getString("description");
			String mainImageLink = feedObj.getString("image");
			String suggestion= feedObj.getString("suggestionsArray");
			System.out.println("hello type type =7");
			
			Intent intent = new Intent(activity, DemoDetailPageEvents.class);
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
			activity.startActivity(intent);
			activity.finish();
		}
		public void VenuesActivity(String data,Activity activity) throws JSONException
		{
			//JSONObject feedObj = (JSONObject) feedArray.get(i);
			JSONObject feedObj = new JSONObject(data);		
			String feed_category=feedObj.getString("categoryName");
			String feedImage_url=feedObj.getString("venuePhoto");
			String friendPic1_url=feedObj.getString("facebookFriend1");
			String friendPic2_url=feedObj.getString("facebookFriend2");
			String friendPic3_url=feedObj.getString("facebookFriend3");
			String canonicalUrl=feedObj.getString("canonicalUrl");
			String address=feedObj.getString("formattedAddress");
			String phone=feedObj.getString("phone");
			JSONArray praseArray = feedObj.getJSONArray("phrases");
			String phrases="";
			for(int j=0;j<praseArray.length();j++)
			{
				JSONObject phraseObj = (JSONObject) praseArray.get(j);
				phrases +=phraseObj.getString("phrase");
				if(j!=praseArray.length()-1)
					phrases+=" | ";
			}
			JSONArray dealArrayOB = feedObj.getJSONArray("dealsArray");
			String dealArray=dealArrayOB.toString();
			String rating=feedObj.getString("rating");
			String VenueName=feedObj.getString("name");
			int Distance =feedObj.getInt("distance");
			String distance=Distance+" m";
			String suggestion= feedObj.getString("suggestionsArray");
			if(Distance>1000)
			{
				int p=(Distance/1000);
				int remainder=(Distance%1000)/100;
				distance=p+"."+remainder+" km";
			}
			Intent intent = new Intent(activity, DemoDetailPageDeals.class);
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
			activity.startActivity(intent);
			activity.finish();
		}
	}
	public static void GoToWebView(String url,Activity a)
	{
		Intent intent = new Intent(a, WebviewActivity.class);
		intent.putExtra("url", url);
		a.startActivity(intent);
	}
	private static void BookTicket() {
		
		
		// String feed = FEED + latestTimeStamp;
		String feed = FEED_lat_long;
		System.out.println("roopgundeep Events: new" + feed);
		progressBar.setVisibility(View.VISIBLE);
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
						scrollView.fullScroll(View.FOCUS_DOWN);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						progressBar.setVisibility(View.GONE);
						System.out.println("roopgundeep Events error: new" );
						scrollView.fullScroll(View.FOCUS_DOWN);
						//VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});
		AppController.getInstance().addToRequestQueue(jsonReq);
	}
}
