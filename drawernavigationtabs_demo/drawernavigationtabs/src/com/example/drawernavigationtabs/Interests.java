package com.example.drawernavigationtabs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drawernavigationtabs.adapter.GridViewAdapter;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.example.drawernavigationtabs.foursquare.FoursquareLogin;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Interests extends Activity {

	public final static String TAG = Interests.class.getSimpleName();
	public SqlHandler sqlHandler;
	ArrayList<String> interest = new ArrayList<String>(100);
	ArrayList<String> interestDB = new ArrayList<String>(100);
	public String userID=null;
	public String facebook_reg="0";
	public String interests_reg="0";
	public String foursquare_reg="0";
	public static ProgressBar progressBar;
	public static final String DATABASE_INTERESTS = "INTERESTS";
	public static final String SCRIPT_CREATE_INTERESTDATABASE="CREATE TABLE IF" +
	 		" NOT EXISTS INTERESTS (id integer primary key autoincrement, name);";
	public String LINK="http://54.68.32.118:8080/registerInterests";
	public Interests() {
		// TODO Auto-generated constructor stub
	}

	public static Interests newInstance() {
		return new Interests();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    
		MenuInflater inflater=getMenuInflater();
	    inflater.inflate(R.menu.interests_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().show();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		sqlHandler = new SqlHandler(this);		
		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM USERS");
		if(savedInstanceState == null) {        	        	
        	if (cursor!=null && cursor.getCount() > 0){
        		if (cursor.moveToFirst()) {
    				do{
    					System.out.println("roop Deal ID: "+cursor.getString(cursor.getColumnIndex("facebookId")));
    					userID=cursor.getString(cursor.getColumnIndex("facebookId"));
    				}while (cursor.moveToNext());
    			}
        		
        	}
		}
		 cursor = sqlHandler.selectQuery("SELECT * FROM REGISTER");
		if (cursor != null && cursor.getCount() > 0) {			
			if (cursor.moveToFirst()) {
				do {
					System.out.println("roop FLAG DB: "
							+ cursor.getInt(cursor.getColumnIndex("facebook")));
					facebook_reg=cursor.getString(cursor.getColumnIndex("facebook"));
					interests_reg=cursor.getString(cursor.getColumnIndex("interests"));
					foursquare_reg=cursor.getString(cursor.getColumnIndex("foursquare"));
				} while (cursor.moveToNext());
			}

		}
		setContentView(R.layout.interests);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#bdbdbd"),android.graphics.PorterDuff.Mode.MULTIPLY);
		Button category1 = (Button) findViewById(R.id.category1);
		Button category4 = (Button) findViewById(R.id.category4);
		Button category7 = (Button) findViewById(R.id.category7);
		Button category9 = (Button) findViewById(R.id.category9);
		
		// with no subcategories
		Button category2 = (Button) findViewById(R.id.category2);
		Button category3 = (Button) findViewById(R.id.category3);
		//Button category5 = (Button) findViewById(R.id.category5);
		Button category6 = (Button) findViewById(R.id.category6);
		Button category8 = (Button) findViewById(R.id.category8);

		Button subcategory11 = (Button) findViewById(R.id.subcategory11);
		Button subcategory12 = (Button) findViewById(R.id.subcategory12);
		Button subcategory13 = (Button) findViewById(R.id.subcategory13);
		Button subcategory14 = (Button) findViewById(R.id.subcategory14);

		Button subcategory41 = (Button) findViewById(R.id.subcategory41);
		Button subcategory42 = (Button) findViewById(R.id.subcategory42);
		Button subcategory43 = (Button) findViewById(R.id.subcategory43);
		Button subcategory44 = (Button) findViewById(R.id.subcategory44);
		Button subcategory45 = (Button) findViewById(R.id.subcategory45);
		Button subcategory46 = (Button) findViewById(R.id.subcategory46);
		Button subcategory47 = (Button) findViewById(R.id.subcategory47);
		Button subcategory48 = (Button) findViewById(R.id.subcategory48);
		Button subcategory49 = (Button) findViewById(R.id.subcategory49);
		Button subcategory410 = (Button) findViewById(R.id.subcategory410);

		Button subcategory71 = (Button) findViewById(R.id.subcategory71);
		Button subcategory72 = (Button) findViewById(R.id.subcategory72);

		Button subcategory91 = (Button) findViewById(R.id.subcategory91);
		Button subcategory92 = (Button) findViewById(R.id.subcategory92);
		Button subcategory93 = (Button) findViewById(R.id.subcategory93);
		Button subcategory94 = (Button) findViewById(R.id.subcategory94);
		Button subcategory95 = (Button) findViewById(R.id.subcategory95);

		category1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (findViewById(R.id.subcategory11).getVisibility() == View.GONE) {
					findViewById(R.id.subcategory11)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory12)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory13)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory14)
							.setVisibility(View.VISIBLE);
					Button p = (Button) findViewById(R.id.category1);
					String text = p.getText().toString();
					p.setBackgroundResource(R.drawable.red_oval_category_selected_button);
					interest.add(text);
					interestDB.add("Business");
				} else {

					Button p = (Button) findViewById(R.id.category1);
					String text = p.getText().toString();

					if (interest.contains(text)) {
						p.setBackgroundResource(R.drawable.red_oval_category_button);
						interest.remove(text);
						interestDB.remove("Business");
					} else {
						p.setBackgroundResource(R.drawable.red_oval_category_selected_button);
						interest.add(text);
						interestDB.add("Business");
					}

				}
			}
		});

		category4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (findViewById(R.id.subcategory41).getVisibility() == View.GONE) {
					findViewById(R.id.subcategory41)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory42)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory43)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory44)
					.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory45)
					.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory46)
					.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory47)
					.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory48)
					.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory49)
					.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory410)
					.setVisibility(View.VISIBLE);
					Button p = (Button) findViewById(R.id.category4);
					String text = p.getText().toString();
					p.setBackgroundResource(R.drawable.green_oval_category_selected_button);
					interest.add(text);
					interestDB.add(text);

				} else {

					Button p = (Button) findViewById(R.id.category4);
					String text = p.getText().toString();

					if (interest.contains(text)) {
						p.setBackgroundResource(R.drawable.green_oval_category_button);
						interest.remove(text);
						interestDB.remove(text);
					} else {
						p.setBackgroundResource(R.drawable.green_oval_category_selected_button);
						interest.add(text);
						interestDB.add(text);
					}

				}
			}
		});

		category7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (findViewById(R.id.subcategory71).getVisibility() == View.GONE) {
					findViewById(R.id.subcategory71)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory72)
							.setVisibility(View.VISIBLE);
					Button p = (Button) findViewById(R.id.category7);
					String text = p.getText().toString();
					p.setBackgroundResource(R.drawable.green_oval_category_selected_button);
					interest.add(text);
					interestDB.add(text);
				} else {
					Button p = (Button) findViewById(R.id.category7);
					String text = p.getText().toString();

					if (interest.contains(text)) {
						p.setBackgroundResource(R.drawable.green_oval_category_button);
						interest.remove(text);
						interestDB.remove(text);
					} else {
						p.setBackgroundResource(R.drawable.green_oval_category_selected_button);
						interest.add(text);
						interestDB.add(text);
					}
				}
			}
		});

		category9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (findViewById(R.id.subcategory91).getVisibility() == View.GONE) {
					findViewById(R.id.subcategory91)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory92)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory93)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory94)
							.setVisibility(View.VISIBLE);
					findViewById(R.id.subcategory95)
					.setVisibility(View.VISIBLE);
					Button p = (Button) findViewById(R.id.category9);
					String text = p.getText().toString();
					p.setBackgroundResource(R.drawable.blue_oval_category_selected_button);
					interest.add(text);
					interestDB.add(text);
				} else {
					Button p = (Button) findViewById(R.id.category9);
					String text = p.getText().toString();

					if (interest.contains(text)) {
						p.setBackgroundResource(R.drawable.blue_oval_category_button);
						interest.remove(text);
						interestDB.remove(text);
					} else {
						p.setBackgroundResource(R.drawable.blue_oval_category_selected_button);
						interest.add(text);
						interestDB.add(text);
					}
				}
			}
		});
		// With No subcategories
		category2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.category2);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.blue_oval_category_button);
					interest.remove(text);
					interestDB.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.blue_oval_category_selected_button);
					interest.add(text);
					interestDB.add(text);
				}

			}
		});

		category3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.category3);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.red_oval_category_button);
					interest.remove(text);
					interestDB.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.red_oval_category_selected_button);
					interest.add(text);
					interestDB.add(text);
				}

			}
		});
		/*category5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.category5);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.blue_oval_category_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.blue_oval_category_selected_button);
					interest.add(text);
				}

			}
		});*/
		category6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.category6);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.blue_oval_category_button);
					interest.remove(text);
					interestDB.remove("Entertainment");
				} else {
					p.setBackgroundResource(R.drawable.blue_oval_category_selected_button);
					interest.add(text);
					interestDB.add("Entertainment");
				}

			}
		});

		category8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.category8);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.red_oval_category_button);
					interest.remove(text);
					interestDB.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.red_oval_category_selected_button);
					interest.add(text);
					interestDB.add(text);
				}

			}
		});

		subcategory11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory11);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.red_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.red_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});

		subcategory12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory12);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.red_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.red_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});

		subcategory13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory13);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.red_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.red_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});

		subcategory14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory14);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.red_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.red_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});

		subcategory41.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory41);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory42.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory42);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});

		subcategory43.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory43);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		
		subcategory44.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory44);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory45.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory45);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory46.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory46);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory47.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory47);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory48.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory48);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory49.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory49);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory410.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory410);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		

		subcategory71.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory71);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});

		subcategory72.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory72);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.green_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});

		subcategory91.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory91);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});

		subcategory92.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory92);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory93.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory93);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory94.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory94);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
		subcategory95.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button p = (Button) findViewById(R.id.subcategory95);
				String text = p.getText().toString();
				if (interest.contains(text)) {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_button);
					interest.remove(text);
				} else {
					p.setBackgroundResource(R.drawable.blue_oval_subcategory_selected_button);
					interest.add(text);
				}

			}
		});
	

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_refresh:
			sendInterests();

			break;
		default:
			break;
		}
		return true;
	}

	private void sendInterests() {
		// TODO Auto-generated method stub
		String a = null;
		sqlHandler.executeQuery("DROP TABLE IF EXISTS " + DATABASE_INTERESTS);
		sqlHandler.executeQuery(SCRIPT_CREATE_INTERESTDATABASE);
		for (int i = 0; i < interestDB.size(); i++) {
			
			String name= '"'+interestDB.get(i)+'"';
			String query = "INSERT INTO INTERESTS(name) "
					+ "values ("
					+ name+  ")";
			System.out.println("roop DB "+query);
			System.out.println("Roop DB query query " + query);
			sqlHandler.executeQuery(query);
		}
		interests_reg="1";
		String query1 = "INSERT INTO REGISTER(facebook , interests , foursquare ) "
				+ "values ("
				+ facebook_reg+ ", "+ interests_reg+ ", "+ foursquare_reg+ ")";
		System.out.println("roop DB "+query1);
		System.out.println("Roop DB query query " + query1);
		sqlHandler.executeQuery(query1);
		JSONArray jsonArrayFriends = new JSONArray();
		for (int i = 0; i < interest.size(); i++) {

			String ids1 = interest.get(i).toString();
			JSONObject student2 = new JSONObject();
			try {
				student2.put("category", ids1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonArrayFriends.put(student2);
		}
		JSONObject FriendsJsonObj = new JSONObject();
		try {
			FriendsJsonObj.put("interests", jsonArrayFriends);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonString = FriendsJsonObj.toString();
		//Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
		connectWithHttpGet(jsonString);

		
		
	}
    private void connectWithHttpGet(String json) {

		class HttpPostAsyncTask extends AsyncTask<String, String, String>{
			
			@Override
			protected void onPreExecute() {
				progressBar.setVisibility(View.VISIBLE);
			}
			@Override
			protected String doInBackground(String... params) {

				String json = params[0];			
				System.out.println("Roopgundeep HTTP json[1] " + json);
				System.out.println("Roopgundeep HTTP clients " );
				try {
					
					//URI website = new URI(link);
					//String website = URLEncoder.encode(link, "utf-8");
					int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
					HttpClient client = new DefaultHttpClient(httpParams);
					HttpPost request = new HttpPost(LINK);
					String p="abcd";
					BasicNameValuePair regIDBasicNameValuePair = new BasicNameValuePair("interests", json);
					BasicNameValuePair userIDBasicNameValuePair = new BasicNameValuePair("userID", userID);
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					nameValuePairList.add(regIDBasicNameValuePair);
					nameValuePairList.add(userIDBasicNameValuePair);
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
					request.setEntity(urlEncodedFormEntity);
					HttpResponse response = client.execute(request);

				} catch (ClientProtocolException cpe) {
					System.out.println("Exception generates of httpResponse :" + cpe);
					System.out.println("Roopgundeep HTTP catch " );
					cpe.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("Second exception of httpResponse :" + ioe);
					System.out.println("Roopgundeep HTTP exp " );
					ioe.printStackTrace();
				} 
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				progressBar.setVisibility(View.GONE);
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				finish();
			}			
		}
		// Initialize the AsyncTask class
		HttpPostAsyncTask httpGetAsyncTask = new HttpPostAsyncTask(); 
		httpGetAsyncTask.execute(json); 
	}
}
