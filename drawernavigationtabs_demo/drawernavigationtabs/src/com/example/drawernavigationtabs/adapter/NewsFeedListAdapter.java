package com.example.drawernavigationtabs.adapter;

import com.example.drawernavigationtabs.FacebookShare;
import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.NewsfeedHome;
import com.example.drawernavigationtabs.NetworkImageViewSquare;
import com.example.drawernavigationtabs.NewsDetail;
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.NewsFeedItem;
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
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;


public class NewsFeedListAdapter extends ArrayAdapter<NewsFeedItem> {	
	
	Context context;
	LayoutInflater inflater;
	View convertViewer;
	List<NewsFeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private UiLifecycleHelper uiHelper;
	
	public NewsFeedListAdapter(Context context, int textViewResourceId, List<NewsFeedItem> objects) {

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
	public NewsFeedItem getItem(int location) {
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
		
		convertView = inflater.inflate(R.layout.news_feed_item, null);
		
		convertViewer=convertView;
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView SourceName = (TextView) convertView.findViewById(R.id.sourceName);
		TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
		TextView feed_category = (TextView) convertView.findViewById(R.id.feed_category);
		TextView DummySourceName = (TextView) convertView.findViewById(R.id.DummysourceName);
		TextView DummyTimestamp = (TextView) convertView.findViewById(R.id.Dummytimestamp);
		TextView link = (TextView) convertView.findViewById(R.id.link);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView sourceImageView = (TextView) convertView.findViewById(R.id.sourceImageLink);
		TextView mainImageView = (TextView) convertView.findViewById(R.id.mainImageLink);
		
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);
		TextView description = (TextView) convertView.findViewById(R.id.description);
		NetworkImageViewSquare profilePic = (NetworkImageViewSquare) convertView.findViewById(R.id.profilePic);

		NetworkImageView fbFriendPic1 = (NetworkImageView) convertView.findViewById(R.id.friendPic1);
		NetworkImageView fbFriendPic2 = (NetworkImageView) convertView.findViewById(R.id.friendPic2);
		NetworkImageView fbFriendPic3 = (NetworkImageView) convertView.findViewById(R.id.friendPic3);
		//TextView likedData = (TextView) convertView.findViewById(R.id.likedData);
		//ImageView shareButton = (ImageView) convertView.findViewById(R.id.shareDiv);
		TextView friendPic1_url = (TextView) convertView.findViewById(R.id.friendPiclink1);
		TextView friendPic2_url = (TextView) convertView.findViewById(R.id.friendPiclink2);
		TextView friendPic3_url = (TextView) convertView.findViewById(R.id.friendPiclink3);
		
		NewsFeedItem item = feedItems.get(position);
		TextView suggestion = (TextView) convertView.findViewById(R.id.suggestion);
		suggestion.setText(item.getSuggestionArray());
		// Converting timestamp into x ago format
		/*CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);*/
		System.out.println("roop: SourceName:"+item.getCreatedTime());
		
		String dummy_url="https://graph.facebook.com/100002707028842/picture?type=normal&height=60&width=60";
		String first="https://graph.facebook.com/";
		String last="/picture?type=normal&height=60&width=60";
		
		
		if(item.getFacebookFriend1()=="null"){
			
			
			friendPic1_url.setText(null);
		}
		else{
			fbFriendPic1.setVisibility(View.VISIBLE);
			fbFriendPic1.setImageUrl(first+item.getFacebookFriend1()+last, imageLoader);
			friendPic1_url.setText(first+item.getFacebookFriend1()+last);
		}
		if(item.getFacebookFriend2()=="null")
		{
			
			friendPic2_url.setText(null);
		}
		else
		{
			fbFriendPic2.setVisibility(View.VISIBLE);
			fbFriendPic2.setImageUrl(first+item.getFacebookFriend2()+last, imageLoader);
			friendPic2_url.setText(first+item.getFacebookFriend2()+last);
		}
		if(item.getFacebookFriend3()=="null")
		{
			
			friendPic3_url.setText(null);
		}
		else
		{
			fbFriendPic3.setVisibility(View.VISIBLE);
			fbFriendPic3.setImageUrl(first+item.getFacebookFriend3()+last, imageLoader);
			friendPic3_url.setText(first+item.getFacebookFriend3()+last);
		}
		if(item.getCategory()==null)
		{
			feed_category.setVisibility(View.GONE);
		}
		feed_category.setText(item.getCategory());
		DummyTimestamp.setText(item.getCreatedTime());
		profilePic.setImageUrl(item.getSourceImage(), imageLoader);
		timestamp.setText(item.getCreatedTime());
		SourceName.setText(item.getSourceName());
		description.setText(item.getDescription());
		
		title.setText(item.getName());		
		sourceImageView.setText(item.getSourceImage());
		mainImageView.setText(item.getMainImage());
		link.setText(item.getLink());
		
		// Feed image
		if (item.getMainImage() != null) {
		
			feedImageView.setImageUrl(item.getMainImage()	, imageLoader);
		
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
			description.setVisibility(View.GONE);
		} 
		else {
			feedImageView.setVisibility(View.GONE);	
			description.setVisibility(View.VISIBLE);

		}
		//description.setVisibility(View.VISIBLE);
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
    	//getContext().startActivity(intent);
    	//((Activity) getContext()).overridePendingTransition (R.anim.open_next, R.anim.close_main);
    	//((Activity) getContext()).overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        //Toast.makeText(getActivity(), title,Toast.LENGTH_SHORT).show();
	}
	void share(){
		Intent intent = new Intent(getContext() , FacebookShare.class);
		getContext().startActivity(intent);
	}
}
