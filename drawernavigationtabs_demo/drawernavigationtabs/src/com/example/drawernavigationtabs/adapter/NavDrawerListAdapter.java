package com.example.drawernavigationtabs.adapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.StrictMode;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.drawernavigationtabs.NetworkImageView;
import com.example.drawernavigationtabs.FeedImageView;
import com.example.drawernavigationtabs.NetworkImageViewSquare;
import com.example.drawernavigationtabs.NewsfeedHome;
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.app.AppController;
import com.example.drawernavigationtabs.data.NewsFeedItem;
import com.example.drawernavigationtabs.data.NavDrawerItem;
import com.example.drawernavigationtabs.database.SqlHandler;

public class NavDrawerListAdapter extends ArrayAdapter<NavDrawerItem>{

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	LayoutInflater inflater;
	public SqlHandler sqlHandler;
	public ImageLoader imageLoader =  AppController.getInstance().getImageLoader();	
	public NavDrawerListAdapter(Context context, int textViewResourceId, ArrayList<NavDrawerItem> objects) {
		
		super(context, textViewResourceId, objects);	
		this.context = context;
		this.navDrawerItems=objects;
			
	}
	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public NavDrawerItem getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if (navDrawerItems.get(position).getProfile() == 0) {

				convertView = mInflater.inflate(R.layout.drawer_list_items,
						null);

				ImageView imgIcon = (ImageView) convertView
						.findViewById(R.id.icon);
				TextView txtTitle = (TextView) convertView
						.findViewById(R.id.title);
				TextView txtCount = (TextView) convertView
						.findViewById(R.id.counter);

				imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
				txtTitle.setText(navDrawerItems.get(position).getTitle());

				// displaying count
				// check whether it set visible or not
				if (navDrawerItems.get(position).getCounterVisibility()) {
					txtCount.setText(navDrawerItems.get(position).getCount());
				} else {
					// hide the counter view
					txtCount.setVisibility(View.GONE);
				}
				
			} else {
				sqlHandler = new SqlHandler(getContext());
				System.out.println("roop: Profile");
				convertView = mInflater.inflate(
						R.layout.profile_drawer_list_items, null);				
				NetworkImageView imgIcon = (NetworkImageView) convertView.findViewById(R.id.icon);
				Cursor c2 = sqlHandler.selectQuery("SELECT * FROM USERS");
				String name = "Your Name", facebookId = null;
				URL img_value = null;Bitmap bitmap = null;
				
				if (c2 != null && c2.getCount() > 0) { //change the sign to >
					
					if (c2.moveToFirst()) {
						do {
							name = c2.getString(c2.getColumnIndex("first_name"));
							facebookId = c2.getString(c2.getColumnIndex("facebookId"));
							
						} while (c2.moveToNext());
					}
					String img_url = "https://graph.facebook.com/"+facebookId+"/picture?type=normal&height=200&width=200";
					System.out.println("roop: Profile url: "+ img_url);
					imgIcon.setImageUrl(img_url, imageLoader);
					System.out.println("roop: Profile id"+ facebookId);
					
				}
				else{
					System.out.println("roop: Profile else");
					imgIcon.setImageUrl("http://www.sheridanstudentunion.com/files/2013/02/facebook-default-no-profile-pic-200x200.jpg", imageLoader);
				}
				convertView.setEnabled(false);
				convertView.setOnClickListener(null);
				
				TextView txtTitle = (TextView) convertView
						.findViewById(R.id.title);
				TextView txtCount = (TextView) convertView
						.findViewById(R.id.counter);
		
				txtTitle.setText(name);
				// displaying count
				// check whether it set visible or not
				if (navDrawerItems.get(0).getCounterVisibility()) {
					txtCount.setText(navDrawerItems.get(position).getCount());
				} else {
					// hide the counter view
					txtCount.setVisibility(View.GONE);
				}
			}
        }
   
        return convertView;
	}
}
