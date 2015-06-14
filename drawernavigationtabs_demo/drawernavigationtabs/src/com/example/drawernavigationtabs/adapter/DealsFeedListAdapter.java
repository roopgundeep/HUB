package com.example.drawernavigationtabs.adapter;

import com.example.drawernavigationtabs.FacebookShare;
import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.NewsfeedHome;
import com.example.drawernavigationtabs.NetworkImageViewSquare;
import com.example.drawernavigationtabs.NewsDetail;
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.DealsFeedItem;
import com.facebook.UiLifecycleHelper;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;


public class DealsFeedListAdapter extends ArrayAdapter<DealsFeedItem> {	
	
	Context context;
	LayoutInflater inflater;
	View convertViewer;
	List<DealsFeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private UiLifecycleHelper uiHelper;
	
	public DealsFeedListAdapter(Context context, int textViewResourceId, List<DealsFeedItem> objects) {

		super(context, textViewResourceId, objects);
		this.context = context;
		this.feedItems = objects;		
		//uiHelper.onCreate(savedInstanceState);
	}	

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public DealsFeedItem getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		convertView = inflater.inflate(R.layout.deals_feed_item, null);
		
		convertViewer=convertView;
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView title = (TextView) convertView.findViewById(R.id.VenueName);		
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.dealsFeedImage);
		TextView distance = (TextView) convertView.findViewById(R.id.distance);
		TextView feed_category = (TextView) convertView.findViewById(R.id.feed_category);
		TextView phrases = (TextView) convertView.findViewById(R.id.phrases);
		RatingBar rating = (RatingBar) convertView.findViewById(R.id.rating);
		ImageView dealFlag= (ImageView) convertView.findViewById(R.id.dealTag);
		
		NetworkImageView fbFriendPic1 = (NetworkImageView) convertView.findViewById(R.id.friendPic1);
		NetworkImageView fbFriendPic2 = (NetworkImageView) convertView.findViewById(R.id.friendPic2);
		NetworkImageView fbFriendPic3 = (NetworkImageView) convertView.findViewById(R.id.friendPic3);
		TextView dealArray = (TextView) convertView.findViewById(R.id.dealArray);
		TextView friendPic1_url = (TextView) convertView.findViewById(R.id.friendPic1_url);
		TextView friendPic2_url = (TextView) convertView.findViewById(R.id.friendPic2_url);
		TextView friendPic3_url = (TextView) convertView.findViewById(R.id.friendPic3_url);
		TextView feedImage_url = (TextView) convertView.findViewById(R.id.feedImage_url);
		TextView address = (TextView) convertView.findViewById(R.id.address);
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		//TextView likedData = (TextView) convertView.findViewById(R.id.likedData);
		//ImageView shareButton = (ImageView) convertView.findViewById(R.id.shareDiv);

		
		
		DealsFeedItem item = feedItems.get(position);
		TextView suggestion = (TextView) convertView.findViewById(R.id.suggestion);
		suggestion.setText(item.getSuggestionArray());
		
		TextView canonicalUrl = (TextView) convertView
				.findViewById(R.id.canonicalUrl);
		canonicalUrl.setText(item.getcanonicalUrl());
		System.out.println("roop: SourceName:"+item.getSourceName());
	
		// Converting timestamp into x ago format
		/*CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);*/
		System.out.println("roop: SourceName:"+item.getCreatedTime());
		
		// Checking for null feed title
		if (item.getName() != null) {
			
			title.setText(item.getName());
			title.setVisibility(View.VISIBLE);
		
		} else {
			title.setText("TITLE COMES HERE");
			title.setVisibility(View.VISIBLE);		
		}
		String dummy_url="https://graph.facebook.com/100002707028842/picture?type=normal&height=60&width=60";
		String first="https://graph.facebook.com/";
		String last="/picture?type=normal&height=60&width=60";
		
		if(item.getFacebookFriend1()=="null"){
			
			fbFriendPic1.setVisibility(View.GONE);
			friendPic1_url.setText(null);
		}
		else{
			fbFriendPic1.setImageUrl(first+item.getFacebookFriend1()+last, imageLoader);
			friendPic1_url.setText(first+item.getFacebookFriend1()+last);
		}
		if(item.getFacebookFriend2()=="null")
		{
			fbFriendPic2.setVisibility(View.GONE);
			friendPic2_url.setText(null);
		}
		else
		{
			fbFriendPic2.setImageUrl(first+item.getFacebookFriend2()+last, imageLoader);
			friendPic2_url.setText(first+item.getFacebookFriend2()+last);
		}
		if(item.getFacebookFriend3()=="null")
		{
			fbFriendPic3.setVisibility(View.GONE);
			friendPic3_url.setText(null);
		}
		else
		{
			fbFriendPic3.setImageUrl(first+item.getFacebookFriend3()+last, imageLoader);
			friendPic3_url.setText(first+item.getFacebookFriend3()+last);
		}
		
		if(item.getDealFlag()==true)
			dealFlag.setVisibility(View.VISIBLE);
		feed_category.setText(item.getCategory());		
		distance.setText(item.getDistance());
		phrases.setText(item.getPhrase());
		rating.setRating(item.getRating().floatValue());
		dealArray.setText(item.getDealArray());
		address.setText(item.getAddress());
		phone.setText(item.getPhoneNumber());
		feedImage_url.setText(null);
		// Feed image
		if (item.getPictureFB() != null) {
			
			feedImageView.setImageUrl(item.getPictureFB(), imageLoader);
			feedImage_url.setText(item.getPictureFB());
			feedImageView.setVisibility(View.VISIBLE);
			feedImageView
					.setResponseObserver(new FeedImageView.ResponseObserver() {
						@Override
						public void onError() {
						}
						@Override
						public void onSuccess() {
						}
					});
		} 
		return convertView;
	}
	
	private void GoToActivity(View convertView) {
		// TODO Auto-generated method stub
		//title
    	TextView t= (TextView) convertView.findViewById(R.id.title);
    	String title=t.getText().toString();
    	
    	//timestamp
    	TextView time=(TextView) convertView.findViewById(R.id.Dummytimestamp);
    	String timestamp = time.getText().toString();
    	//description
    	TextView des=(TextView) convertView.findViewById(R.id.description);
    	String description = des.getText().toString();
    	//SourceName
    	TextView source=(TextView) convertView.findViewById(R.id.DummysourceName);
    	String sourceName = source.getText().toString();
    	
    	//Source Imagelink
    	TextView sourceImageView=(TextView) convertView.findViewById(R.id.sourceImageLink);
    	String sourceImageLink = sourceImageView.getText().toString();
    	//Main ImageLink
    	TextView mainImageView=(TextView) convertView.findViewById(R.id.mainImageLink);
    	String mainImageLink = mainImageView.getText().toString();
    	
    	Intent intent = new Intent(getContext() , NewsDetail.class);
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
    	getContext().startActivity(intent);
    	((Activity) getContext()).overridePendingTransition (R.anim.open_next, R.anim.close_main);
    	//((Activity) getContext()).overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        //Toast.makeText(getActivity(), title,Toast.LENGTH_SHORT).show();
	}
	void share(){
		Intent intent = new Intent(getContext() , FacebookShare.class);
		getContext().startActivity(intent);
	}
}
