package com.example.drawernavigationtabs.adapter;

import com.example.drawernavigationtabs.FacebookShare;
import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.NewsfeedHome;
import com.example.drawernavigationtabs.NetworkImageViewSquare;
import com.example.drawernavigationtabs.NewsDetail;
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.EventsFeedItem;
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


public class EventsFeedListAdapter extends ArrayAdapter<EventsFeedItem> {	
	
	Context context;
	LayoutInflater inflater;
	View convertViewer;
	List<EventsFeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private UiLifecycleHelper uiHelper;
	
	public EventsFeedListAdapter(Context context, int textViewResourceId, List<EventsFeedItem> objects) {

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
	public EventsFeedItem getItem(int location) {
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
		
			convertView = inflater.inflate(R.layout.events_feed_item, null);
	
		convertViewer=convertView;
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView sourceImageView = (TextView) convertView.findViewById(R.id.sourceImageLink);
		TextView mainImageView = (TextView) convertView.findViewById(R.id.mainImageLink);
		
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);
		TextView description = (TextView) convertView.findViewById(R.id.description);
		TextView startDate = (TextView) convertView.findViewById(R.id.startDate);
		TextView endDate = (TextView) convertView.findViewById(R.id.endDate);
		TextView venueName = (TextView) convertView.findViewById(R.id.venue);

		TextView address = (TextView) convertView.findViewById(R.id.address);
		TextView organiserName = (TextView) convertView.findViewById(R.id.organiserName);
		TextView link = (TextView) convertView.findViewById(R.id.link);
		
		TextView feed_category = (TextView) convertView.findViewById(R.id.feed_category);
		TextView suggestion = (TextView) convertView.findViewById(R.id.suggestion);

		EventsFeedItem item = feedItems.get(position);
		System.out.println("roop: SourceName:"+item.getSourceName());
		
		title.setText(item.getName());
		
		startDate.setText(item.getStartTime());
		endDate.setText(item.getEndTime());
		sourceImageView.setText(item.getSourceImage());
		mainImageView.setText(item.getImage());
		feedImageView.setImageUrl(item.getImage(), imageLoader);
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
		if(item.getCategory()==null)
			feed_category.setVisibility(View.GONE);
		venueName.setText(item.getVenue());
		feed_category.setText(item.getCategory());
		description.setText(item.getDescription());
		address.setText(item.getAddress());
		organiserName.setText(item.getOrganizerName());
		suggestion.setText(item.getSuggestionArray());
		link.setText(item.getLink());
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
