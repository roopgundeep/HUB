package com.example.drawernavigationtabs.adapter;

import com.example.drawernavigationtabs.DealsTabbedFragment;
import com.example.drawernavigationtabs.EventsTabbedFragment;
import com.example.drawernavigationtabs.FacebookShare;
import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.NewsTabbedFragment;
import com.example.drawernavigationtabs.NewsfeedHome;
import com.example.drawernavigationtabs.NetworkImageViewSquare;
import com.example.drawernavigationtabs.NewsDetail;
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.EventsFeedItem;
import com.example.drawernavigationtabs.data.HomeFeedItem;
import com.example.drawernavigationtabs.data.NewsFeedItem;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.example.drawernavigationtabs.detailpage.DemoDetailPageDeals;
import com.example.drawernavigationtabs.detailpage.DemoDetailPageEvents;
import com.example.drawernavigationtabs.detailpage.DemoDetailPageNews;
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
import android.support.v4.app.FragmentActivity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

public class HomeFeedListAdapter extends ArrayAdapter<HomeFeedItem> {

	Context context;
	LayoutInflater inflater;
	View convertViewer;
	List<HomeFeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private UiLifecycleHelper uiHelper;

	public HomeFeedListAdapter(Context context, int textViewResourceId,
			List<HomeFeedItem> objects) {

		super(context, textViewResourceId, objects);
		this.context = context;
		this.feedItems = objects;
		// uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public HomeFeedItem getItem(int location) {
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
		if (position == 0) {
			convertView = inflater.inflate(R.layout.button, null);
			convertViewer = convertView;
			Button newsButton = (Button) convertView
					.findViewById(R.id.newsbutton);
			newsButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: news More:");
					GoToNews();
				}
			});
			Button dealsButton = (Button) convertView
					.findViewById(R.id.dealsbutton);
			dealsButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: news More:");
					GoToDeals();
				}
			});
			Button eventsbutton = (Button) convertView
					.findViewById(R.id.eventsbutton);
			eventsbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: news More:");
					GoToEvents();
				}
			});
			return convertView;
		}
		HomeFeedItem item = feedItems.get(position);
		//System.out.println("Testing roop "+item.getCategoryType()+" name: "+item.getName());
		if (item!=null && item.getCategoryType().equals("venues")) {
			//System.out.println("Testing roop inside"+item.getCategoryType());
			System.out.println("Testing roop inside"+item.getCategoryType()+" name: "+item.getName());
			convertView = inflater.inflate(R.layout.home_deals_feed_item, null);
			convertViewer = convertView;
			if (imageLoader == null)
				imageLoader = AppController.getInstance().getImageLoader();
			
			RelativeLayout categoryLabelLayout = (RelativeLayout) convertView
					.findViewById(R.id.categoryfeed);
			LinearLayout dealLayout = (LinearLayout) convertView
					.findViewById(R.id.dealLayout);
			TextView categoryLabel = (TextView) convertView
					.findViewById(R.id.category_label);
			if (item.getVisibilityTag() == 0) {
				categoryLabelLayout.setVisibility(View.VISIBLE);
			}
			categoryLabel.setText(item.getCategoryLabel());
			TextView suggestion = (TextView) convertView.findViewById(R.id.suggestion);
			suggestion.setText(item.getSuggestionArray());
			
			TextView title = (TextView) convertView
					.findViewById(R.id.VenueName);
			FeedImageView feedImageView = (FeedImageView) convertView
					.findViewById(R.id.dealsFeedImage);
			TextView distance = (TextView) convertView
					.findViewById(R.id.distance);
			TextView feed_category = (TextView) convertView
					.findViewById(R.id.feed_category);
			TextView phrases = (TextView) convertView
					.findViewById(R.id.phrases);
			TextView canonicalUrl = (TextView) convertView
					.findViewById(R.id.canonicalUrl);
			canonicalUrl.setText(item.getcanonicalUrl());
			RatingBar rating = (RatingBar) convertView
					.findViewById(R.id.rating);

			NetworkImageView fbFriendPic1 = (NetworkImageView) convertView
					.findViewById(R.id.friendPic1);
			NetworkImageView fbFriendPic2 = (NetworkImageView) convertView
					.findViewById(R.id.friendPic2);
			NetworkImageView fbFriendPic3 = (NetworkImageView) convertView
					.findViewById(R.id.friendPic3);
			TextView dealArray = (TextView) convertView.findViewById(R.id.dealArray);
			TextView friendPic1_url = (TextView) convertView.findViewById(R.id.friendPic1_url);
			TextView friendPic2_url = (TextView) convertView.findViewById(R.id.friendPic2_url);
			TextView friendPic3_url = (TextView) convertView.findViewById(R.id.friendPic3_url);
			TextView feedImage_url = (TextView) convertView.findViewById(R.id.feedImage_url);
			TextView address = (TextView) convertView.findViewById(R.id.address);
			TextView phone = (TextView) convertView.findViewById(R.id.phone);
			ImageView dealFlag= (ImageView) convertView.findViewById(R.id.dealTag);

			Button MoreButton = (Button) convertView
					.findViewById(R.id.moreButton);
			MoreButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: news More:");
					GoToDeals();
				}
			});
			dealLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					
					GoToDealsDetail(view);
				}
			});
			System.out.println("roop Home position" + item.getCategoryLabel());
			System.out.println("roop: SourceName:" + item.getSourceName());

			// Converting timestamp into x ago format
			/*
			 * CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
			 * Long.parseLong(item.getTimeStamp()), System.currentTimeMillis(),
			 * DateUtils.SECOND_IN_MILLIS);
			 */
			//System.out.println("roop: SourceName:" + item.getCreatedTime());
			System.out.println("Testing 1");
			// Checking for null feed title
			if (item.getName() != null) {

				title.setText(item.getName());
				title.setVisibility(View.VISIBLE);

			} else {
				title.setText("TITLE COMES HERE");
				title.setVisibility(View.VISIBLE);
			}
			System.out.println("Testing 2");

			String dummy_url="https://graph.faebook.com/100002707028842/picture?type=normal&height=60&width=60";
			String first="https://graph.facebook.com/";
			String last="/picture?type=normal&height=60&width=60";
			System.out.println("Testing 3");

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
			if(item.getDealFlag()==false)
				dealFlag.setVisibility(View.GONE);
			System.out.println("Testing 4");
			feed_category.setText(item.getCategory());
			distance.setText(item.getDistance());
			phrases.setText(item.getPhrase());
			rating.setRating(item.getRating().floatValue());
			feedImage_url.setText(null);
			System.out.println("Testing 5");
			System.out.println("Roop DealArray in detail"+item.getDealArray());
			dealArray.setText(item.getDealArray());
			address.setText(item.getAddress());
			phone.setText(item.getPhoneNumber());
			System.out.println("Testing 6");

			// fbFriendPic3.setImageUrl(item.getSourceImage(), imageLoader);
			// likedData.setText(" also liked this");
			// profilePic.draw(canvas);
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
			System.out.println("Testing 7");
		} else if (item!=null && item.getCategoryType().equals("news")) {

			convertView = inflater.inflate(R.layout.home_news_feed_item, null);
			convertViewer = convertView;
			if (imageLoader == null)
				imageLoader = AppController.getInstance().getImageLoader();
			RelativeLayout categoryLabelLayout = (RelativeLayout) convertView
					.findViewById(R.id.categoryfeed);
			TextView categoryLabel = (TextView) convertView
					.findViewById(R.id.category_label);
			if (item.getVisibilityTag() == 0) {
				categoryLabelLayout.setVisibility(View.VISIBLE);
			}
			categoryLabel.setText(item.getCategoryLabel());
			TextView suggestion = (TextView) convertView.findViewById(R.id.suggestion);
			suggestion.setText(item.getSuggestionArray());
			
			TextView SourceName = (TextView) convertView
					.findViewById(R.id.sourceName);
			TextView timestamp = (TextView) convertView
					.findViewById(R.id.timestamp);

			TextView DummySourceName = (TextView) convertView
					.findViewById(R.id.DummysourceName);
			TextView DummyTimestamp = (TextView) convertView
					.findViewById(R.id.Dummytimestamp);
			TextView feed_category = (TextView) convertView
					.findViewById(R.id.feed_category);
			TextView title = (TextView) convertView.findViewById(R.id.title);
			TextView sourceImageView = (TextView) convertView
					.findViewById(R.id.sourceImageLink);
			TextView mainImageView = (TextView) convertView
					.findViewById(R.id.mainImageLink);

			FeedImageView feedImageView = (FeedImageView) convertView
					.findViewById(R.id.feedImage1);
			TextView description = (TextView) convertView
					.findViewById(R.id.description);
			TextView link = (TextView) convertView
					.findViewById(R.id.link);
			NetworkImageViewSquare profilePic = (NetworkImageViewSquare) convertView
					.findViewById(R.id.profilePic);

			NetworkImageView fbFriendPic1 = (NetworkImageView) convertView
					.findViewById(R.id.friendPic1);
			NetworkImageView fbFriendPic2 = (NetworkImageView) convertView
					.findViewById(R.id.friendPic2);
			NetworkImageView fbFriendPic3 = (NetworkImageView) convertView
					.findViewById(R.id.friendPic3);
			// TextView likedData = (TextView)
			// convertView.findViewById(R.id.likedData);
			// ImageView shareButton = (ImageView)
			// convertView.findViewById(R.id.shareDiv);
			TextView fbFriendPicLink1 = (TextView) convertView
					.findViewById(R.id.friendPiclink1);
			TextView fbFriendPicLink2 = (TextView) convertView
					.findViewById(R.id.friendPiclink2);
			TextView fbFriendPicLink3 = (TextView) convertView
					.findViewById(R.id.friendPiclink3);

			LinearLayout selectedLayout = (LinearLayout) convertView
					.findViewById(R.id.selectedLayout);
			feed_category.setText(item.getCategory());


			selectedLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: layout:");
					GoToNewsActivity(view);
				}
			});
			Button MoreButton = (Button) convertView
					.findViewById(R.id.moreButton);
			MoreButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: news More:");
					GoToNews();
				}
			});
			/*
			 * shareButton.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { share(); } });
			 */
			System.out.println("roop: SourceName:" + item.getSourceName());
			SourceName.setText(item.getSourceName());
			DummySourceName.setText(item.getSourceName());
			// Converting timestamp into x ago format
			/*
			 * CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
			 * Long.parseLong(item.getTimeStamp()), System.currentTimeMillis(),
			 * DateUtils.SECOND_IN_MILLIS);
			 */
			System.out.println("Testing 8");

			System.out.println("roop: SourceName:" + item.getCreatedTime());
			timestamp.setText(item.getCreatedTime());
			link.setText(item.getLink());
			DummyTimestamp.setText(item.getCreatedTime());
			// Checking for null feed title
			if (item.getName() != null) {

				title.setText(item.getName());
				title.setVisibility(View.VISIBLE);
				// title.setText(Html.fromHtml("<a href=\"" + item.getLink() +
				// "\">"
				// + item.getUrl() + "</a> "));
				// Making title clickable
				// title.setMovementMethod(LinkMovementMethod.getInstance());
				// title.setVisibility(View.VISIBLE);
			} else {
				title.setText("TITLE COMES HERE");
				title.setVisibility(View.VISIBLE);
				// title is null, remove from the view
				// title.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(item.getMessage())) {
				// description.setText(item.getMessage());
			}// check for description
			else if (!TextUtils.isEmpty(item.getDescription())) {
				// status is empty, remove from view
				// description.setText(item.getDescription());
			}// default text
			else {
				// description.setText("Description comes here");
				// description.setVisibility(View.GONE);
			}
			// user profile pic
			System.out.println("Testing 9");

			profilePic
					.setImageUrl(
							item.getSourceImage(),
							imageLoader);
			
			// fbFriendPic3.setImageUrl(item.getSourceImage(), imageLoader);
			// likedData.setText(" also liked this");
			// profilePic.draw(canvas);

			String dummy_url="https://graph.facebook.com/100002707028842/picture?type=normal&height=60&width=60";
			String first="https://graph.facebook.com/";
			String last="/picture?type=normal&height=60&width=60";
			
			if(item.getFacebookFriend1()=="null"){
				
				
				fbFriendPicLink1.setText(null);
			}
			else{
				//fbFriendPic1.setVisibility(View.VISIBLE);
				fbFriendPic1.setImageUrl(first+item.getFacebookFriend1()+last, imageLoader);
				fbFriendPicLink1.setText(first+item.getFacebookFriend1()+last);
			}
			if(item.getFacebookFriend2()=="null")
			{
				
				fbFriendPicLink2.setText(null);
			}
			else
			{
				fbFriendPic2.setVisibility(View.VISIBLE);
				fbFriendPic2.setImageUrl(first+item.getFacebookFriend2()+last, imageLoader);
				fbFriendPicLink2.setText(first+item.getFacebookFriend2()+last);
			}
			if(item.getFacebookFriend3()=="null")
			{
				
				fbFriendPicLink3.setText(null);
			}
			else
			{
				fbFriendPic3.setVisibility(View.VISIBLE);
				fbFriendPic3.setImageUrl(first+item.getFacebookFriend3()+last, imageLoader);
				fbFriendPicLink3.setText(first+item.getFacebookFriend3()+last);
			}
			sourceImageView
					.setText(item.getSourceImage());
			mainImageView.setText(item.getMainImage());
			// Feed image
			description.setText(item.getDescription());
			if (item.getMainImage() != null) {

				feedImageView.setImageUrl(item.getMainImage(), imageLoader);

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
				if (!TextUtils.isEmpty(item.getDescription())) {
					description.setText(item.getDescription());
					description.setVisibility(View.VISIBLE);
				}// check for description
				else if (!TextUtils.isEmpty(item.getMessage())) {
					// status is empty, remove from view
					description.setText(item.getMessage());
					description.setVisibility(View.VISIBLE);
				} else {
					description.setText("Description comes here");
					description.setVisibility(View.VISIBLE);
					// description.setVisibility(View.GONE);
				}
			}
		} else if (item!=null && item.getCategoryType().equals("events")) {

			convertView = inflater
					.inflate(R.layout.home_events_feed_item, null);
			convertViewer = convertView;
			if (imageLoader == null)
				imageLoader = AppController.getInstance().getImageLoader();
			RelativeLayout categoryLabelLayout = (RelativeLayout) convertView
					.findViewById(R.id.categoryfeed);
			LinearLayout selLayout = (LinearLayout) convertView
					.findViewById(R.id.selLayout);
			TextView categoryLabel = (TextView) convertView
					.findViewById(R.id.category_label);
			if (item.getVisibilityTag() == 0) {
				categoryLabelLayout.setVisibility(View.VISIBLE);
			}
			Button MoreButton = (Button) convertView
					.findViewById(R.id.moreButton);
			MoreButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: news More:");
					GoToEvents();
				}
			});
			selLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("roopgundeep click: news More:");
					GoToEventsActivity(view);
				}
			});
			categoryLabel.setText(item.getCategoryLabel());
			TextView title = (TextView) convertView.findViewById(R.id.title);
			TextView sourceImageView = (TextView) convertView
					.findViewById(R.id.sourceImageLink);
			TextView mainImageView = (TextView) convertView
					.findViewById(R.id.mainImageLink);

			FeedImageView feedImageView = (FeedImageView) convertView
					.findViewById(R.id.feedImage1);
			TextView description = (TextView) convertView
					.findViewById(R.id.description);
			TextView startDate = (TextView) convertView
					.findViewById(R.id.startDate);

			TextView venueName = (TextView) convertView
					.findViewById(R.id.venue);

			TextView feed_category = (TextView) convertView
					.findViewById(R.id.feed_category);

			TextView address = (TextView) convertView.findViewById(R.id.address);
			TextView organiserName = (TextView) convertView.findViewById(R.id.organiserName);
			TextView link = (TextView) convertView.findViewById(R.id.link);
			TextView endDate = (TextView) convertView.findViewById(R.id.endDate);
			TextView suggestion = (TextView) convertView.findViewById(R.id.suggestion);
			suggestion.setText(item.getSuggestionArray());
			address.setText(item.getAddress());
			endDate.setText(item.getEndTime());
			organiserName.setText(item.getOrganizerName());
			link.setText(item.getLink());
			
			
			System.out.println("roop: SourceName:" + item.getSourceName());

			// Converting timestamp into x ago format
			/*
			 * CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
			 * Long.parseLong(item.getTimeStamp()), System.currentTimeMillis(),
			 * DateUtils.SECOND_IN_MILLIS);
			 */
			// Checking for null feed title
			if (item.getName() != null) {

				title.setText(item.getName());
				title.setVisibility(View.VISIBLE);
				// title.setText(Html.fromHtml("<a href=\"" + item.getLink() +
				// "\">"
				// + item.getUrl() + "</a> "));
				// Making title clickable
				// title.setMovementMethod(LinkMovementMethod.getInstance());
				// title.setVisibility(View.VISIBLE);
			} else {
				title.setText("TITLE COMES HERE");
				title.setVisibility(View.VISIBLE);
				// title is null, remove from the view
				// title.setVisibility(View.GONE);
			}

			// sourcePic.setImageUrl("http://cdn.marketplaceimages.windowsphone.com/v8/images/9c74bc35-373c-4107-b388-a996258a0aeb?imageType=ws_icon_small",
			// imageLoader);
			startDate.setText(item.getStartTime());
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
			venueName.setText(item.getVenue());
			feed_category.setText(item.getCategory());
			description.setText(item.getDescription());
		}
		return convertView;
	}
	private void GoToEventsActivity(View view){
		
		String ids1 = "0";
		ids1 = '"' + ids1 + '"';
		String query1 = "INSERT INTO EVENTFLAG( value ) " + "values ("
				+ ids1 + ")";
		System.out.println("roop DB " + query1);
		System.out.println("Roop DB query fb query " + query1);
		SqlHandler sqlHandler = new SqlHandler(getContext());
		sqlHandler.executeQuery(query1);
		TextView mainImageLinkView=(TextView)view.findViewById(R.id.mainImageLink);
		String mainImageLink = mainImageLinkView.getText().toString();
		
		TextView descriptionView=(TextView)view.findViewById(R.id.description);
		String description = descriptionView.getText().toString();
		
		TextView linkView=(TextView)view.findViewById(R.id.link);
		String link = linkView.getText().toString();
		
		TextView addressView=(TextView)view.findViewById(R.id.address);
		String address = addressView.getText().toString();
		
		TextView organiserNameView=(TextView)view.findViewById(R.id.organiserName);
		String organiserName = organiserNameView.getText().toString();
		
		TextView endDateView=(TextView)view.findViewById(R.id.endDate);
		String endDate = endDateView.getText().toString();
		
		TextView feed_categoryView=(TextView)view.findViewById(R.id.feed_category);
		String feed_category = feed_categoryView.getText().toString();
		
		TextView titleView=(TextView)view.findViewById(R.id.title);
		String title = titleView.getText().toString();
		
		TextView venueView=(TextView)view.findViewById(R.id.venue);
		String venue = venueView.getText().toString();
		
		TextView startDateView=(TextView)view.findViewById(R.id.startDate);
		String startDate = startDateView.getText().toString();
		
		TextView suggestionView = (TextView) view.findViewById(R.id.suggestion);
		String suggestion = suggestionView.getText().toString();
		
		Intent intent = new Intent(getContext(), DemoDetailPageEvents.class);
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
		getContext().startActivity(intent);
	}
	private void GoToNewsActivity(View view) {
		// TODO Auto-generated method stub

		TextView sourceImageView=(TextView)view.findViewById(R.id.sourceImageLink);
		String sourceImageLink = sourceImageView.getText().toString(); 
		//Main ImageLink
		TextView mainImageView=(TextView)view.findViewById(R.id.mainImageLink);
		String mainImageLink = mainImageView.getText().toString();
		TextView feed_category=(TextView)view.findViewById(R.id.feed_category);
		String category = feed_category.getText().toString();
		
		TextView TitleView=(TextView)view.findViewById(R.id.title);
		String title = TitleView.getText().toString();
		
		TextView DescriptionView=(TextView)view.findViewById(R.id.description);
		String description = DescriptionView.getText().toString();
		
		TextView TimestampView=(TextView)view.findViewById(R.id.timestamp);
		String timestamp = TimestampView.getText().toString();
		
		TextView fbFriendView1=(TextView)view.findViewById(R.id.friendPiclink1);
		String fbFriend1 = fbFriendView1.getText().toString();
		
		TextView fbFriendView2=(TextView)view.findViewById(R.id.friendPiclink2);
		String fbFriend2 = fbFriendView2.getText().toString();
		
		TextView fbFriendView3=(TextView)view.findViewById(R.id.friendPiclink3);
		String fbFriend3 = fbFriendView3.getText().toString();
		
		TextView linkView=(TextView)view.findViewById(R.id.link);
		String link = linkView.getText().toString();
		TextView suggestionView = (TextView) view.findViewById(R.id.suggestion);
		String suggestion = suggestionView.getText().toString();
		
		System.out.println("Roop Onclick: "+title);
		System.out.println("Roop Onclick: "+fbFriend1);
		System.out.println("Roop Onclick: "+fbFriend2);
		System.out.println("Roop Onclick: "+timestamp);
		System.out.println("Roop Onclick: "+category);
		System.out.println("Roop Onclick: "+mainImageLink);
		
		Intent intent = new Intent(getContext(), DemoDetailPageNews.class);
		// * intent.putExtra("title",title);
		intent.putExtra("sourceImageLink",sourceImageLink);
		intent.putExtra("mainImageLink",mainImageLink);
		intent.putExtra("category",category);
		intent.putExtra("title",title);
		intent.putExtra("description",description);
		intent.putExtra("timestamp",timestamp);
		intent.putExtra("fbFriend1",fbFriend1);
		intent.putExtra("fbFriend2",fbFriend2);
		intent.putExtra("fbFriend3",fbFriend3);
		intent.putExtra("link",link);
		intent.putExtra("suggestion",suggestion);
		getContext().startActivity(intent);
		/*
		 * ((Activity) getContext()).overridePendingTransition(R.anim.open_next,
		 * R.anim.close_main);
		 */
		// ((Activity) getContext()).overridePendingTransition(
		// R.anim.slide_in_up, R.anim.slide_out_up );
		// Toast.makeText(getActivity(), title,Toast.LENGTH_SHORT).show();
	}

	void share() {
		Intent intent = new Intent(getContext(), FacebookShare.class);
		getContext().startActivity(intent);
	}

	void GoToNews() {

		((FragmentActivity) getContext())
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, NewsTabbedFragment.newInstance(),
						NewsTabbedFragment.TAG).commit();
	}

	void GoToDeals() {
		((FragmentActivity) getContext())
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, DealsTabbedFragment.newInstance(),
						DealsTabbedFragment.TAG).commit();
	}
	void GoToEvents() {
		((FragmentActivity) getContext())
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, EventsTabbedFragment.newInstance(),
						EventsTabbedFragment.TAG).commit();
	}
	void GoToDealsDetail(View view)
	{
		TextView feedImage_urlView=(TextView)view.findViewById(R.id.feedImage_url);
		String feedImage_url = feedImage_urlView.getText().toString();
		
		TextView distanceView=(TextView)view.findViewById(R.id.distance);
		String distance = distanceView.getText().toString();
		
		TextView VenueNameView=(TextView)view.findViewById(R.id.VenueName);
		String VenueName = VenueNameView.getText().toString();
		
		TextView feed_categoryView=(TextView)view.findViewById(R.id.feed_category);
		String feed_category = feed_categoryView.getText().toString();
		
		TextView phrasesView=(TextView)view.findViewById(R.id.phrases);
		String phrases = phrasesView.getText().toString();
		
		RatingBar ratingView=(RatingBar)view.findViewById(R.id.rating);
		float rating = ratingView.getRating();
		
		TextView friendPic1_urlView=(TextView)view.findViewById(R.id.friendPic1_url);
		String friendPic1_url = friendPic1_urlView.getText().toString();
		
		TextView friendPic2_urlView=(TextView)view.findViewById(R.id.friendPic2_url);
		String friendPic2_url = friendPic2_urlView.getText().toString();
		
		TextView friendPic3_urlView=(TextView)view.findViewById(R.id.friendPic3_url);
		String friendPic3_url = friendPic3_urlView.getText().toString();
		
		TextView dealArrayView =(TextView)view.findViewById(R.id.dealArray);
		String dealArray = dealArrayView.getText().toString();
		
		TextView addressView =(TextView)view.findViewById(R.id.address);
		String address = addressView.getText().toString();
		
		TextView phoneView =(TextView)view.findViewById(R.id.phone);
		String phone = phoneView.getText().toString();
		
		TextView canonicalUrlView = (TextView) view.findViewById(R.id.canonicalUrl);				
		String canonicalUrl = canonicalUrlView.getText().toString();
		
		TextView suggestionView = (TextView) view.findViewById(R.id.suggestion);
		String suggestion = suggestionView.getText().toString();
		
		Intent intent = new Intent(getContext(), DemoDetailPageDeals.class);
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
		//System.out.println("hello I am clicked"+ dealArray);
		getContext().startActivity(intent);
	}
}
