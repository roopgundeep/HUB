package com.example.drawernavigationtabs.detailpage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.FriendsList;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.NetworkImageViewSquare;
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.SearchDealsfeed;
import com.example.drawernavigationtabs.WebviewActivity;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.NewsFeedItem;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import uk.co.chrisjenx.paralloid.Parallaxor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.text.GetChars;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DemoDetailPageEvents extends FragmentActivity {

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
	public static View av;
	public static ScrollView scrollView;
	public static String select="";
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String eventFlag = "0";
		Cursor cursor2 = sqlHandler.selectQuery("SELECT * FROM EVENTFLAG");
		if (cursor2 != null && cursor2.getCount() > 0) {
			if (cursor2.moveToFirst()) {
				do {
					// System.out.println("roop Deal ID: "+cursor2.getString(cursor.getColumnIndex("value")));
					eventFlag = cursor2.getString(cursor2.getColumnIndex("value"));
				} while (cursor2.moveToNext());
			}

		}
		if(eventFlag.compareTo("1")==0)
		{
			progressBar.setVisibility(View.VISIBLE);
			BookTicket();
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
							if(suggestion_categoryType.equals("news"))
								type.setText("Walmart"); //default it was categoryType
							else
								type.setText(suggestion_categoryType);
							rec_layout1.setVisibility(View.VISIBLE);
							
							rec_layout1.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View view) {
									System.out.println("roopgundeep click: news More:");
									TextView dataView = (TextView)view.findViewById(R.id.rec_data1);
									String data=dataView.getText().toString();
									
									TextView typeView = (TextView)view.findViewById(R.id.rec_type1);
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
							//default it was categoryType
							if(suggestion_categoryType.equals("news"))
								type.setText("Walmart"); //default it was categoryType
							else
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
							type.setText("Walmart"); //default it was categoryType
							
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
			av=convertView;
			readMore.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					//progressBar.setVisibility(View.VISIBLE);
					//POPUP();
					av.findViewById(R.id.recommendation).setVisibility(View.VISIBLE);
					progressBar.setVisibility(View.VISIBLE);
					String ids1 = "1";
					ids1 = '"' + ids1 + '"';
					String query1 = "INSERT INTO EVENTFLAG( value ) " + "values ("
							+ ids1 + ")";
					System.out.println("roop DB " + query1);
					System.out.println("Roop DB query fb query " + query1);
					sqlHandler.executeQuery(query1);
					BookTicket();
					
					//Intent i = new Intent(getActivity(),FriendsList.class);
					//startActivity(i);
					//av.findViewById(R.id.recommendation).setVisibility(View.VISIBLE);
					//System.out.println("roopgundeep click: event More:url"+p_url);
					//
					//GoToWebView(p_url,getActivity());
				}
			});
			RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.image_view_container_RL);
			 scrollView = (ScrollView) convertView.findViewById(R.id.description_view_PSV);
			if(scrollView instanceof Parallaxor) {
				((Parallaxor) scrollView).parallaxViewBy(rl, 0.5f);
			}
			return convertView;
		}
		public  void GoToActivity(String data,String type,Activity a) throws JSONException
		{
			if(type.compareTo("news")==0 || type.compareTo("Walmart")==0)
			{
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
			Intent intent = new Intent(activity, DemoDetailPageNews.class);
			// * intent.putExtra("title",title);
			System.out.println("sourceImageLink"+sourceImageLink);
			intent.putExtra("sourceImageLink",sourceImageLink);
			intent.putExtra("mainImageLink",mainImageLink);
			intent.putExtra("category",category);
			intent.putExtra("title",title);
			intent.putExtra("description",description);
			intent.putExtra("timestamp",timestamp);
			intent.putExtra("fbFriend1",fbFriend1);
			intent.putExtra("fbFriend2",fbFriend2);
			intent.putExtra("fbFriend3",fbFriend3);
			intent.putExtra("suggestion",suggestion);
			
			intent.putExtra("link",link);
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
				parsed = sourceFormat.parse(feedObj.getString("startTime"));
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
				parsed_end = sourceFormat.parse(feedObj.getString("endTime"));
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
						scrollView.fullScroll(View.FOCUS_DOWN);
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
						scrollView.fullScroll(View.FOCUS_DOWN);
						//VolleyLog.d(TAG, "Error: " + error.getMessage());
					}
				});
		AppController.getInstance().addToRequestQueue(jsonReq);
	}
	
	public static void POPUP() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(a);

		// AlertDialog alertDialog = new
		// AlertDialog.Builder(MainActivity.this).create();

		// Setting Dialog Title
		//alertDialog.setTitle("Buy Tickets");
		//LayoutInflater inflater = a.getLayoutInflater();
		//View view=inflater.inflate(R.layout.pop_titlebar, null);
		//alertDialog.setCustomTitle(view);
		
		//Resources resources = alertDialog.getContext().getResources();
		 //int alertTitleId = resources.getIdentifier("alertTitle", "id", "android");
		//TextView alertTitle = (TextView)   alertDialog.getWindow().getDecorView().findViewById(alertTitleId);
        //alertTitle.setTextColor(color);
		// Setting Dialog Message
		// alertDialog.setMessage("Enter Password");
		LinearLayout lila = new LinearLayout(a);		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	            RelativeLayout.LayoutParams.FILL_PARENT, 
	            RelativeLayout.LayoutParams.WRAP_CONTENT);
		lila.setGravity(Gravity.CENTER);
		lila.setPadding(60, 10, 10, 10);
		final Context current_context = a;
		final TextView ed = new TextView(a);
		ed.setPadding(0, 0, 60, 0);
		ed.setTextSize(20);
		final Spinner spinner = new Spinner(a);
		
		// Creating an adapter for type of project with values in the spinner
		// (dropdown)
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(a,
				R.array.type_array, R.layout.spinner_layout);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// An item was selected. You can retrieve the selected item
				// using
				select = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				select = "";
				// Another interface callback
			}
		});
		ed.setText("Number of Tickets");
			
		lila.addView(ed);
		lila.addView(spinner);
		alertDialog.setView(lila);
		//final EditText input = new EditText(a);
		//alertDialog.setView(input);

		// Setting Icon to Dialog
		// alertDialog.setIcon(R.drawable.key);

		// Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Book",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        // Write your code here to execute after dialog
                        //Toast.makeText(getApplicationContext(),"Password Matched", Toast.LENGTH_SHORT).show();
                        //Intent myIntent1 = new Intent(view.getContext(), Show.class);
                        //startActivityForResult(myIntent1, 0);
                    	BookTicket();                    	
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                    	progressBar.setVisibility(View.GONE);
                        //dialog.cancel();
                    }
                });

        alertDialog.show();
		
	}
	
	
}
