package com.example.drawernavigationtabs.adapter;

import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.NewsfeedHome;

import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.NewsFeedItem;

import java.util.List;

import android.app.Activity;
import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;


public class MainFeedListAdapter extends ArrayAdapter<NewsFeedItem> {	
	
	Context context;
	LayoutInflater inflater;
	List<NewsFeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public MainFeedListAdapter(Context context, int textViewResourceId, List<NewsFeedItem> objects) {
		
		super(context, textViewResourceId, objects);
		this.context = context;
		this.feedItems = objects;		
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
		if (convertView == null)
			convertView = inflater.inflate(R.layout.feed_item_main, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		
		
		TextView SourceName = (TextView) convertView.findViewById(R.id.sourceName);
		TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);

		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView sourceImageView = (TextView) convertView.findViewById(R.id.sourceImageLink);
		TextView mainImageView = (TextView) convertView.findViewById(R.id.mainImageLink);
		
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);
		TextView description = (TextView) convertView.findViewById(R.id.description);
		NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic);
		
		
		NewsFeedItem item = feedItems.get(position);
		
		System.out.println("roop: SourceName:"+item.getSourceName());
		SourceName.setText(item.getSourceName());

		// Converting timestamp into x ago format
		/*CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);*/
		System.out.println("roop: SourceName:"+item.getCreatedTime());
		timestamp.setText(item.getCreatedTime());

		

		// Checking for null feed title
		if (item.getName() != null) {
			
			title.setText(item.getName());
			title.setVisibility(View.VISIBLE);
			//title.setText(Html.fromHtml("<a href=\"" + item.getLink() + "\">"
	//				+ item.getUrl() + "</a> "));

			// Making title clickable
			//title.setMovementMethod(LinkMovementMethod.getInstance());
			//title.setVisibility(View.VISIBLE);
		} else {
			title.setText("TITLE COMES HERE");
			title.setVisibility(View.VISIBLE);
			// title is null, remove from the view
			//title.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(item.getMessage())) {			
			description.setText(item.getMessage());			
		}//check for description 
		else if(!TextUtils.isEmpty(item.getDescription())){
			// status is empty, remove from view
			description.setText(item.getDescription());					
		}//default text
		else{
			description.setText("Description comes here");
			//description.setVisibility(View.GONE);
		}
		// user profile pic
		profilePic.setImageUrl(item.getSourceImage(), imageLoader);
		
		

		//profilePic.draw(canvas);
		
		sourceImageView.setText(item.getSourceImage());
		mainImageView.setText(item.getPictureFB());
		// Feed image
		if (item.getPictureFB() != null) {
			
			feedImageView.setImageUrl(item.getPictureFB(), imageLoader);
			
			
			/*ImageView imgIcon = (ImageView) convertView
					.findViewById(R.id.icon);
			
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image);
			//imgIcon.buildDrawingCache();
			//Bitmap bitmap = imgIcon.getDrawingCache();
			System.out.println("roop:bitmap"+bitmap);
			Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
			convertView.setEnabled(false);
			convertView.setOnClickListener(null);
			BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
			Paint paint = new Paint();
			paint.setShader(shader);

			Canvas c = new Canvas(circleBitmap);
			c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
			
			imgIcon.setImageBitmap(circleBitmap);
			*/
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
		} else {
			feedImageView.setVisibility(View.GONE);			
			// Check for message
			if (!TextUtils.isEmpty(item.getMessage())) {			
				description.setText(item.getMessage());
				description.setVisibility(View.VISIBLE);
			}//check for description 
			else if(!TextUtils.isEmpty(item.getDescription())){
				// status is empty, remove from view
				description.setText(item.getDescription());
				description.setVisibility(View.VISIBLE);				
			}//default text
			else{
				description.setText("Description comes here");
				description.setVisibility(View.VISIBLE);
				//description.setVisibility(View.GONE);
			}
		}
		description.setVisibility(View.VISIBLE);
		return convertView;
	}

}
