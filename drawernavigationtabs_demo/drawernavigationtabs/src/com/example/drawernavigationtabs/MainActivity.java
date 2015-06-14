package com.example.drawernavigationtabs;
 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.R.drawable;
import com.example.drawernavigationtabs.adapter.NavDrawerListAdapter;
import com.example.drawernavigationtabs.data.NavDrawerItem;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.example.drawernavigationtabs.foursquare.FoursquareLogin;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
 
public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	public SqlHandler sqlHandler;
	public DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	public ActionBarDrawerToggle mDrawerToggle;
	public int facebook_register=0;
	public int interests_register=0;
	public int foursquare_register=0;
	private static String APP_ID = "374944869307757"; // Replace with your App

	// Instance of Facebook Class
	@SuppressWarnings("deprecation")
	private Facebook facebook = new Facebook(APP_ID);
	@SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	Button btnFbLogin;
	
	
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerItmes;
    
 // slide menu items
 	private String[] navMenuTitles;
 	private TypedArray navMenuIcons;
 	
 	private ArrayList<NavDrawerItem> navDrawerItems;
 	private NavDrawerListAdapter adapter;
 	
 	@Override
 	protected void onResume() {
 		// TODO Auto-generated method stub
 		super.onResume();
 		adapter = new NavDrawerListAdapter(this, R.layout.drawer_list_items, navDrawerItems);
		mDrawerList.setAdapter(adapter);
 		
 	}
 	public void changeURL(){
		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM REGISTER");
		if (cursor != null && cursor.getCount() > 0) {			
			if (cursor.moveToFirst()) {
				do {
					System.out.println("roop FLAG DB: "
							+ cursor.getInt(cursor.getColumnIndex("facebook")));
					facebook_register=cursor.getInt(cursor.getColumnIndex("facebook"));
					interests_register=cursor.getInt(cursor.getColumnIndex("interests"));
					foursquare_register=cursor.getInt(cursor.getColumnIndex("foursquare"));
				} while (cursor.moveToNext());
			}
		}
	}
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.activity_main);       
		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		navDrawerItems.add(new NavDrawerItem("Your Name", R.drawable.user_profile, 1));
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1), 0));
		
		
		
		// Find People
		//navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
			//	.getResourceId(1, -1),0));
		// Photos
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
//				.getResourceId(2, -1),0));
//		// Communities, Will add a counter here
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
//				.getResourceId(3, -1), true, "22",0));
//		// Pages
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
//				.getResourceId(4, -1),0));
//		// What's hot, We will add a counter here
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
//				.getResourceId(5, -1), true, "50+",0));
		/*mDrawerItmes = getResources().getStringArray(R.array.drawer_titles);
        */
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,  GravityCompat.START);
        
        navMenuIcons.recycle();
        // Add items to the ListView
        adapter = new NavDrawerListAdapter(this, R.layout.drawer_list_items, navDrawerItems);
        
		mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
 
        // Enable ActionBar app icon to behave as action to toggle the NavigationDrawer
        getActionBar().show();       
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        //setHasOptionsMenu(false);
        
        //getActionBar().(R.drawable.homepage_background_top);
                    
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer_white,
                R.string.drawer_open,
                R.string.drawer_close
                ) {
            public void onDrawerClosed(View view) {
                
                
                android.support.v4.app.Fragment dealsFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
    					.findFragmentByTag(DealsTabbedFragment.TAG);
                android.support.v4.app.Fragment newsFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
    					.findFragmentByTag(NewsTabbedFragment.TAG);
                android.support.v4.app.Fragment eventsFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
    					.findFragmentByTag(EventsTabbedFragment.TAG);
                android.support.v4.app.Fragment dealFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
    					.findFragmentByTag(FoodList.TAG);
                if(dealsFragment!=null && dealsFragment.isVisible())
                {
                	getActionBar().setTitle("Dining");
                }
                else if(newsFragment!=null && newsFragment.isVisible()){
                	getActionBar().setTitle("Feeds");
                }
                else if(eventsFragment!=null && eventsFragment.isVisible()){
                	getActionBar().setTitle("Events");
                }
                else if(dealFragment!=null && dealFragment.isVisible()){
                	getActionBar().setTitle("Looking For..");
                }
                else{
    				// add your code here
                	getActionBar().setTitle(mTitle);
    			}
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
            }
             
            public void onDrawerOpened(View drawerView) {
            	 android.support.v4.app.Fragment homeFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
     					.findFragmentByTag(Homefeed.TAG);
                 android.support.v4.app.Fragment dealsFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
     					.findFragmentByTag(DealsTabbedFragment.TAG);
                 android.support.v4.app.Fragment newsFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
     					.findFragmentByTag(NewsTabbedFragment.TAG);
                 android.support.v4.app.Fragment eventsFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
     					.findFragmentByTag(EventsTabbedFragment.TAG);
                 android.support.v4.app.Fragment dealFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
     					.findFragmentByTag(FoodList.TAG);
                 if(dealsFragment!=null && dealsFragment.isVisible())
                 {
                 	getActionBar().setTitle("Dining");
                 }
                 else if(newsFragment!=null && newsFragment.isVisible()){
                 	getActionBar().setTitle("Feeds");
                 }
                 else if(eventsFragment!=null && eventsFragment.isVisible()){
                 	getActionBar().setTitle("Events");
                 }
                 else if(dealFragment!=null && dealFragment.isVisible()){
                 	getActionBar().setTitle("Looking For..");
                 }
                 else{
          				// add your code here
                      	getActionBar().setTitle(mTitle);
          			
                 }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
            }
        };
      
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        // Set the default content area to item 0
        // when the app opens for the first time
		sqlHandler = new SqlHandler(this);

		changeURL();
        navigateTo(0);
		if (savedInstanceState == null) {
			 if (facebook_register == 0 && interests_register == 0
			 		&& foursquare_register == 0) {
				 navigateTo(0);
			 	Intent i = new Intent(this, DemoTabbedFragmentActivity.class);
			 	startActivity(i);
			 	finish();
			 }
			 else
			 {
				navigateTo(0);
					
			 }
			 //else if (facebook_register == 1 && interests_register == 0
			// 		&& foursquare_register == 0) {
			// 	Intent i = new Intent(this, Interests.class);
			// 	startActivity(i);
			// 	finish();
			// } else if (facebook_register == 1 && interests_register == 1
			// 		&& foursquare_register == 0) {
			// 	Intent i = new Intent(this, FoursquareLogin.class);
			// 	startActivity(i);
			// 	finish();
			// } else {
			// 	navigateTo(0);
			// }
		}
//		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM USERS");
//		if(savedInstanceState == null) {        	        	
//        	if (cursor!=null && cursor.getCount() > 0){
////        		if (cursor.moveToFirst()) {
////    				do{
////    					System.out.println("roop Deal ID: "+cursor.getString(cursor.getColumnIndex("facebookId")));
////    				}while (cursor.moveToNext());
////    			}
//        		
//        		navigateTo(0);
//        	}
//        	else{
//        		Intent i = new Intent(this,DemoTabbedFragmentActivity.class);
//        		startActivity(i);
//        		finish();
//        	}            
//        }     
    }
    /*
     * If you do not have any menus, you still need this function
     * in order to open or close the NavigationDrawer when the user
     * clicking the ActionBar app icon.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
      
        return super.onOptionsItemSelected(item);
    }
    
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }*/
    /*
     * When using the ActionBarDrawerToggle, you must call it during onPostCreate()
     * and onConfigurationChanged()
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public class DrawerItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            navigateTo(position);
        }
    }
    //these are various pages
    private void navigateTo(int position) {
         
        switch(position) {
        case 0:        
        	getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content_frame, Homefeed.newInstance(), Homefeed.TAG).commit();
            break;
        case 1:
        	getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content_frame, Homefeed.newInstance(), Homefeed.TAG).commit();
           
            break;
        case 2:
        	Intent i = new Intent(this, Interests_Home.class);
    		startActivity(i);            
            break;
        case 3:
        	/*getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content_frame,
                    Interests.newInstance(),
                    Interests.TAG).commit(); */   
        	break;
        case 4:
        	getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content_frame,
                    DealsTabbedFragment.newInstance(),
                    DealsTabbedFragment.TAG).commit();    
        	break;
        case 5:
        	getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content_frame, EventsTabbedFragment.newInstance(), EventsTabbedFragment.TAG).commit();
            break;
        case 6:        
            getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content_frame,
                    WebViewFragment.newInstance(),
                    WebViewFragment.TAG).commit();
            break;
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }
     
    @SuppressLint("NewApi")
	@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			android.support.v4.app.Fragment myFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
					.findFragmentByTag(Homefeed.TAG);
			android.support.v4.app.Fragment dealFragment = (android.support.v4.app.Fragment) getSupportFragmentManager()
					.findFragmentByTag(FoodList.TAG);
			if (myFragment!=null && myFragment.isVisible()) {
				// add your code here
			}
			else if(dealFragment!=null && dealFragment.isVisible()){
				
				getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, DealsTabbedFragment.newInstance(),
						DealsTabbedFragment.TAG).commit();
				return true;
			}
			else {
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.content_frame, Homefeed.newInstance(),
								Homefeed.TAG).commit();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}