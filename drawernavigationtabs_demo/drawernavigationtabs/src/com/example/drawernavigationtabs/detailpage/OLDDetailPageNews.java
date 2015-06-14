package com.example.drawernavigationtabs.detailpage;

import com.android.volley.toolbox.ImageLoader;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class OLDDetailPageNews extends FragmentActivity {

	private UiLifecycleHelper uiHelper;
	public static String SetLink;
	public static String SetPicture;
	public static String SetDescription;
	public static String SetName;
	public static String SetCaption;
	public SqlHandler sqlHandler;
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
			feedImageView.setImageUrl(mainImageLink, imageLoader);
			sourcePic.setImageUrl(sourceImageLink, imageLoader);
			fbFriendPic1.setImageUrl(fbFriend1, imageLoader);
			fbFriendPic2.setImageUrl(fbFriend2, imageLoader);
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
}
