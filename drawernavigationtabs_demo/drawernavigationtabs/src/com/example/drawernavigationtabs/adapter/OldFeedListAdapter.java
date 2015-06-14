package com.example.drawernavigationtabs.adapter;

import com.example.drawernavigationtabs.FacebookShare;
import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.NewsfeedHome;
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


public class OldFeedListAdapter extends ArrayAdapter<NewsFeedItem> {	
	
	Context context;
	LayoutInflater inflater;
	View convertViewer;
	List<NewsFeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private UiLifecycleHelper uiHelper;
	
	public OldFeedListAdapter(Context context, int textViewResourceId, List<NewsFeedItem> objects) {

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
		
		TextView DummySourceName = (TextView) convertView.findViewById(R.id.DummysourceName);
		TextView DummyTimestamp = (TextView) convertView.findViewById(R.id.Dummytimestamp);
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView sourceImageView = (TextView) convertView.findViewById(R.id.sourceImageLink);
		TextView mainImageView = (TextView) convertView.findViewById(R.id.mainImageLink);
		
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);
		TextView description = (TextView) convertView.findViewById(R.id.description);
		NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic);
		
		
		NetworkImageView fbFriendPic1 = (NetworkImageView) convertView.findViewById(R.id.friendPic1);
		NetworkImageView fbFriendPic2 = (NetworkImageView) convertView.findViewById(R.id.friendPic2);
		NetworkImageView fbFriendPic3 = (NetworkImageView) convertView.findViewById(R.id.friendPic3);
		TextView likedData = (TextView) convertView.findViewById(R.id.likedData);
		ImageView shareButton = (ImageView) convertView.findViewById(R.id.shareDiv);
		
		LinearLayout selectedLayout= (LinearLayout) convertView.findViewById(R.id.selectedLayout);
		
		selectedLayout.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
            	System.out.println("roopgundeep click: layout:");
            	GoToActivity(view);
            }
        });
		
		shareButton.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
            	share();
            }
        });
		NewsFeedItem item = feedItems.get(position);
		
		System.out.println("roop: SourceName:"+item.getSourceName());
		SourceName.setText(item.getSourceName());
		DummySourceName.setText(item.getSourceName());
		// Converting timestamp into x ago format
		/*CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);*/
		System.out.println("roop: SourceName:"+item.getCreatedTime());
		timestamp.setText(item.getCreatedTime());
		DummyTimestamp.setText(item.getCreatedTime());
		

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
		
		
		fbFriendPic1.setImageUrl(item.getSourceImage(), imageLoader);
		fbFriendPic2.setImageUrl(item.getSourceImage(), imageLoader);
		fbFriendPic3.setVisibility(View.GONE);
		//fbFriendPic3.setImageUrl(item.getSourceImage(), imageLoader);
		likedData.setText(" also liked this");
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
