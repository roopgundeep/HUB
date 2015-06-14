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
import com.example.drawernavigationtabs.data.NewsFeedItem;
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

public class FoodListAdapter extends ArrayAdapter<String> {

	Context context;
	LayoutInflater inflater;
	View convertViewer;
	List<String> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private UiLifecycleHelper uiHelper;

	public FoodListAdapter(Context context, int textViewResourceId,
			List<String> objects) {

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
	public String getItem(int location) {
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

		String item = feedItems.get(position);
		convertView = inflater.inflate(R.layout.food_list, null);

		convertViewer = convertView;
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView feed_category = (TextView) convertView
				.findViewById(R.id.food_item);
		feed_category.setText(item);
		
		return convertView;
	}

	private void GoToActivity(View view) {

		TextView sourceImageView = (TextView) view
				.findViewById(R.id.sourceImageLink);
		String sourceImageLink = sourceImageView.getText().toString();
		// Main ImageLink
		TextView mainImageView = (TextView) view
				.findViewById(R.id.mainImageLink);
		String mainImageLink = mainImageView.getText().toString();
		//
		TextView feed_category = (TextView) view
				.findViewById(R.id.feed_category);
		String category = feed_category.getText().toString();

		TextView TitleView = (TextView) view.findViewById(R.id.title);
		String title = TitleView.getText().toString();

		TextView DescriptionView = (TextView) view
				.findViewById(R.id.description);
		String description = DescriptionView.getText().toString();

		TextView TimestampView = (TextView) view.findViewById(R.id.timestamp);
		String timestamp = TimestampView.getText().toString();

		TextView fbFriendView1 = (TextView) view
				.findViewById(R.id.friendPiclink1);
		String fbFriend1 = fbFriendView1.getText().toString();

		TextView fbFriendView2 = (TextView) view
				.findViewById(R.id.friendPiclink2);
		String fbFriend2 = fbFriendView2.getText().toString();

		TextView fbFriendView3 = (TextView) view
				.findViewById(R.id.friendPiclink3);
		String fbFriend3 = fbFriendView1.getText().toString();

		System.out.println("Roop Onclick: " + title);
		System.out.println("Roop Onclick: " + fbFriend1);
		System.out.println("Roop Onclick: " + fbFriend2);
		System.out.println("Roop Onclick: " + timestamp);
		System.out.println("Roop Onclick: " + category);
		System.out.println("Roop Onclick: " + mainImageLink);

		Intent intent = new Intent(getContext(), DemoDetailPageNews.class);
		// * intent.putExtra("title",title);
		intent.putExtra("sourceImageLink", sourceImageLink);
		intent.putExtra("mainImageLink", mainImageLink);
		intent.putExtra("category", category);
		intent.putExtra("title", title);
		intent.putExtra("description", description);
		intent.putExtra("timestamp", timestamp);
		intent.putExtra("fbFriend1", fbFriend1);
		intent.putExtra("fbFriend2", fbFriend2);
		intent.putExtra("fbFriend3", fbFriend3);
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
				.replace(R.id.content_frame,
						EventsTabbedFragment.newInstance(),
						EventsTabbedFragment.TAG).commit();
	}
}
