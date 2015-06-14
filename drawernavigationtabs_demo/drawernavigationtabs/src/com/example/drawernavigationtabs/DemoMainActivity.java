package com.example.drawernavigationtabs;
 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.adapter.NavDrawerListAdapter;
import com.example.drawernavigationtabs.data.NavDrawerItem;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import android.os.Bundle;
import android.annotation.SuppressLint;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
 
public class DemoMainActivity extends FragmentActivity {

	private static final String TAG = DemoMainActivity.class.getSimpleName();

	public SqlHandler sqlHandler;
     
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_demo);
		sqlHandler = new SqlHandler(this);
		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM USERS");	
		if(savedInstanceState == null) {        	        	
        	if (cursor!=null && cursor.getCount() > 0){
        		Intent i = new Intent(this,MainActivity.class);
        		startActivity(i);
        	}
        	else{
        		System.out.println("Nothing in DB");
        		getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, DemoTabbedFragmentOld.newInstance(), DemoTabbedFragmentOld.TAG).commit();
        	}            
        }    
	}
}