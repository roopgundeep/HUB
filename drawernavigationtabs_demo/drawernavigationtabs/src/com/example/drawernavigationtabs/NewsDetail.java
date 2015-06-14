package com.example.drawernavigationtabs;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.drawernavigationtabs.app.AppController;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetail extends Activity {
	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private UiLifecycleHelper uiHelper;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		
		String title= getIntent().getStringExtra("title");
		String description= getIntent().getStringExtra("description");
		String timestamp= getIntent().getStringExtra("timestamp");
		String sourceName= getIntent().getStringExtra("sourceName");
		String mainImageLink= getIntent().getStringExtra("mainImageLink");
		String sourceImageLink= getIntent().getStringExtra("sourceImageLink");
		
		
		TextView sourceNameView = (TextView) findViewById(R.id.sourceName);
		TextView timestampView = (TextView) findViewById(R.id.timestamp);
		TextView titleView = (TextView) findViewById(R.id.title);
		FeedImageView feedImageView = (FeedImageView) findViewById(R.id.feedImage1);
		TextView descriptionView = (TextView) findViewById(R.id.description);
		NetworkImageView profilePic = (NetworkImageView) findViewById(R.id.profilePic);
		
		sourceNameView.setText(sourceName);
		timestampView.setText(timestamp);
		titleView.setText(title);
		descriptionView.setText(description);
		
		profilePic.setImageUrl(sourceImageLink, imageLoader);
		feedImageView.setImageUrl(mainImageLink, imageLoader);
		Button share = (Button) findViewById(R.id.share);
		
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				share(v);
			}
		});
		
	}
	public void share(View v){
		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
		.setLink("https://www.facebook.com/hub.theApp")
		.setPicture("https://www.facebook.com/hub.theApp/photos/a.468081896628577.1073741825.468081709961929/469469239823176/?type=1&theater")
		.setName("NA1ME").setCaption("Caption")
		.setDescription("Descript1ion").build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
	        finish();
	        overridePendingTransition (R.anim.open_main, R.anim.close_next);
	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;

	    }

	    return true;
	}
	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	@Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition (R.anim.open_main, R.anim.close_next);
    }
}
