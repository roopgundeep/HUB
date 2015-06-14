package com.example.drawernavigationtabs;
 
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.drawernavigationtabs.GamesFragment;
import com.example.drawernavigationtabs.TopRatedFragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.database.SqlHandler;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
 
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class DemoTabbedFragmentActivity extends FragmentActivity {
 
	public static final String TAG = DemoTabbedFragmentActivity.class.getSimpleName();
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	SharedPreferences sh_Pref;
	public SqlHandler sqlHandler;
	// Your Facebook APP ID
	private static String APP_ID = "375851572539374"; // Replace with your App 
	public String GlobalId;
	public static LinearLayout selector;//ID
	public Button done;
	public String facebook_reg="0";
	public String interests_reg="0";
	public String foursquare_reg="0";
	private ProgressBar progressBar;
	// Instance of Facebook Class
	@SuppressWarnings("deprecation")
	private Facebook facebook = new Facebook(APP_ID);
	@SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;	
	public static String LINK = "http://54.68.32.118:8080/registerFacebook";
	public static String NODE="San Francisco";
    public static DemoTabbedFragmentActivity newInstance() {
        return new DemoTabbedFragmentActivity();
    }
     
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
        sqlHandler = new SqlHandler(this);
        Cursor cursor = sqlHandler.selectQuery("SELECT * FROM REGISTER");
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
        setContentView(R.layout.fragment_demo_tabbed);
        mViewPager = (ViewPager) findViewById(R.id.demoPager);
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                // do transformation here
            	final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setRotationY(position * -30);
                }
        });
		progressBar = (ProgressBar)findViewById(R.id.progressbar);
		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#bdbdbd"), android.graphics.PorterDuff.Mode.MULTIPLY);
		selector= (LinearLayout) findViewById(R.id.selector);
		
		cursor = sqlHandler.selectQuery("SELECT * FROM USERS");
		if (cursor != null && cursor.getCount() > 0) {
			findViewById(R.id.signUP).setVisibility(View.GONE);
		} 
		else {
			findViewById(R.id.signUP).setVisibility(View.VISIBLE);

			done = (Button) findViewById(R.id.signUP);

			done.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("Demo Roopgundeep: Image Button" + "button Clicked");
					loginToFacebook();
				}
			});
		}
    }
    
   
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        
//    	super.onCreateView(inflater, container, savedInstanceState);
//    	View v = inflater.inflate(R.layout.fragment_demo_tabbed, container, false);
//        mSectionsPagerAdapter = new SectionsPagerAdapter(
//                getChildFragmentManager());
//        mViewPager = (ViewPager) v.findViewById(R.id.demoPager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//                // do transformation here
//            	final float normalizedposition = Math.abs(Math.abs(position) - 1);
//                page.setRotationY(position * -30);
//                }
//        });
//		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM USERS");
//		if (cursor != null && cursor.getCount() > 0) {
//			v.findViewById(R.id.signUP).setVisibility(View.GONE);
//		} 
//		else {
//			v.findViewById(R.id.signUP).setVisibility(View.VISIBLE);
//
//			done = (Button) v.findViewById(R.id.signUP);
//
//			done.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					System.out.println("Roopgundeep: Image Button" + "button Clicked");
//					loginToFacebook();
//				}
//			});
//		}
//        return v;
//    }

	public void loginToFacebook() {

		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		
		System.out.println("Demo Roopgundeep: in login to facebook");
		if (access_token != null) {
			System.out.println("Demo Roopgundeep: access_token not null");
			facebook.setAccessToken(access_token);		
			Log.d("FB Sessions", "" + "Roopgundeep "+facebook.isSessionValid());
		}

		if (expires != 0) {
			System.out.println("Roopgundeep: expires !=0");
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			System.out.println("Roopgundeep: is Session Valid");
			facebook.authorize(this, new String[] { "email","user_likes","user_friends" }, 
					new DialogListener() {	
				@Override
				public void onCancel() {
					System.out.println("Roopgundeep: OnCancel");
					// Function to handle cancel event
				}
				@Override
				public void onComplete(Bundle values) {
					// Function to handle complete event
					// Edit Preferences and update facebook acess_token
					System.out.println("Demo Roopgundeep: OnComplete");
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token",
							facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();
					System.out.println("Demo Roopgundeep: get profile calling");
					progressBar.setVisibility(View.VISIBLE);
					getProfileInformation();
					System.out.println("Demo Roopgundeep: get profile done");
					done.setVisibility(View.GONE);
					selector.setVisibility(View.GONE);
					// Making Login button invisible
				}
				@Override
				public void onError(DialogError error) {
					// Function to handle error
					System.out.println("Demo Roopgundeep: OnDialogError:"+error);
				}
				@Override
				public void onFacebookError(FacebookError fberror) {
					// Function to handle Facebook errors
					System.out.println("Demo Roopgundeep: OnFbError: "+fberror);
				}
			});
		}
	}   
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
		
    	super.onActivityResult(requestCode, resultCode, data);
    	System.out.println(" Demo Roopgundeep: onActivityResult");
    	facebook.authorizeCallback(requestCode, resultCode, data);
    	//getProfileInformation();
		System.out.println(" Demo Roopgundeep: get profile ActivityResult done");
		/*Intent i= new Intent(getApplicationContext(),MainActivity.class);
		startActivity(i);
		finish();*/
    }

	
    /**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	@SuppressWarnings("deprecation")
	public void getProfileInformation() {
		System.out.println("getting profile data");
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				final String json = response;
				System.out.println("roop: Json"+json);
				String idd;
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);
					// getting name of the user
					String name = profile.getString("name");
		
					String email = profile.getString("email");
					final String id = profile.getString("id");
					GlobalId=id;
					idd=id;
					String first_name = profile.getString("first_name");
					String last_name = profile.getString("last_name");
					System.out.println("Roop Email: " + email + "-id: " + id
							+ "-first_name:" + first_name);
					name = '"' + name + '"';
					email = '"' + email + '"';
					first_name = '"' + first_name + '"';
					last_name = '"' + last_name + '"';
					idd = '"' + id + '"';
					
					String query = "INSERT INTO USERS(name , emailId , facebookId, first_name, last_name ) "
							+ "values ("
							+ name+ ", "+ email+ ", "+ idd +", "+ first_name+", "+ last_name+ ")";
					System.out.println("Roop DB query query user " + query);
					sqlHandler.executeQuery(query);
					facebook_reg="1";
					String query1 = "INSERT INTO REGISTER(facebook , interests , foursquare ) "
							+ "values ("
							+ facebook_reg+ ", "+ interests_reg+ ", "+ foursquare_reg+ ")";
					System.out.println("roop DB "+query1);
					System.out.println("Roop DB query query " + query1);
					sqlHandler.executeQuery(query1);
				
				mAsyncRunner.request("me/friends", new RequestListener() {
					@Override
					public void onComplete(String response, Object state) {
						Log.d("Profile", response);
						final String json = response;
						System.out.println("Roopgundeep ID: "+GlobalId);
						try {
							// Facebook Profile JSON data
							JSONObject FriendsJson = new JSONObject(json);
							// getting name of the user
							JSONArray friendsData = FriendsJson.getJSONArray("data");
							System.out.println("Roopgundeep Dataname: "+friendsData);
							String ids[] , names[] = new String[friendsData.length()];
							JSONArray jsonArrayFriends = new JSONArray();
							for(int i = 0; i < friendsData .length(); i++){
									
							        String ids1 = friendsData .getJSONObject(i).getString("id");
							        String names1 = friendsData .getJSONObject(i).getString("name");
							        ids1 = '"' + ids1 + '"';
							        names1 = '"' + names1 + '"';
							        String query1 = "INSERT INTO FBFRIENDS(facebookId , name ) "
											+ "values ("
											+ ids1+  ", "+ names1+ ")";
									System.out.println("roop DB "+query1);
									System.out.println("Roop DB query fb query " + query1);
									sqlHandler.executeQuery(query1);
							        JSONObject student2 = new JSONObject();
							        student2.put("id", ids1);
							        jsonArrayFriends.put(student2);
							        //String names1 = friendsData .getJSONObject(i).getString("name");
							        System.out.println("Roopgundeep name: id: "+ids1);
							}
							JSONObject FriendsJsonObj = new JSONObject();
							FriendsJsonObj.put("friends", jsonArrayFriends);
							String jsonString = FriendsJsonObj.toString();
							//jsonString='"'+jsonString+'"';
							System.out.println("roop facebook: response not null link: "+jsonString);				
							String link="http://54.68.32.118:8080/dataFriends?userID="+id+"&friends="+jsonString;
							//String query = URLEncoder.encode(link, "utf-8");					
							System.out.println("roop facebook: response not null link: "+link);					
							//connectWithHttpGet(link,jsonString,id);
							//Go to activity
							Intent i= new Intent(getApplicationContext(), MainActivity.class);
							startActivity(i);
							finish();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onIOException(IOException e, Object state) {
					}

					@Override
					public void onFileNotFoundException(FileNotFoundException e,
							Object state) {
					}

					@Override
					public void onMalformedURLException(MalformedURLException e,
							Object state) {
					}

					@Override
					public void onFacebookError(FacebookError e, Object state) {
					}
				});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	
	}

    /**
	 * Function to Logout user from Facebook
	 * */
	@SuppressWarnings("deprecation")
	public void logoutFromFacebook() {
		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				if (Boolean.parseBoolean(response) == true) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// make Login button visible
						}

					});
				}
			}
			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}

			@Override
			public void onIOException(IOException e, Object state) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
				// TODO Auto-generated method stub
			}
		});
	}
    
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
 
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @SuppressLint("NewApi")
		@Override
        public Fragment getItem(int position) {
        	
        	switch (position) {
    		case 0:
    			// Top Rated fragment activity
    			return new Demo1();
    		case 1:
    			// Games fragment activity
    			return new Demo2();
    		case 2:
    			// Movies fragment activity
    			return new Demo3();
    		case 3:
    			// Movies fragment activity
    			return new Demo2();
    		}
    		return null;
        }
 
        @Override
        public int getCount() {
            return 3;
        }
 
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
            case 0:
                return getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return getString(R.string.title_section3).toUpperCase(l);
            case 3:
                return getString(R.string.title_section4).toUpperCase(l);
            case 4:
                return getString(R.string.title_section5).toUpperCase(l);    
            }
            return null;
        }
    }
 
    public static class TabbedContentFragment extends Fragment {
 
        public static final String ARG_SECTION_NUMBER = "section_number";
 
        public TabbedContentFragment() {
        } 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_content,
                    container, false);
            TextView dummyTextView = (TextView) rootView
                    .findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(
                    ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
    public void myCallBack(int requestCode, int resultCode, Intent data) {
        facebook.authorizeCallback(requestCode, resultCode, data);

        // other code from your onComplete() function ...
    }
    private void connectWithHttpGet(String link, String json,String id) {

		class HttpPostAsyncTask extends AsyncTask<String, String, String>{
			
			
			

			@Override
			protected void onPreExecute() {
				
			}
			
			@Override
			protected String doInBackground(String... params) {

				//String link = params[0];
				String json = params[1];
				String id = params[2];
				System.out.println("Roopgundeep HTTP json[1] " + json);

				//HttpClient httpClient = new DefaultHttpClient();
				
				//HttpGet httpGet = new HttpGet("http://54.201.62.76:8090/data?id=" + paramID + "&regID=" + paramRegID + "&eventsTimestamp=" + paramEventsTimestamp + "&newsTimestamp=" + paramNewsTimestamp + "&dealsTimestamp=" + paramDealsTimestamp);
				
				
				System.out.println("Roopgundeep HTTP clients " );
				try {
					
					//URI website = new URI(link);
					//String website = URLEncoder.encode(LINK, "utf-8");
					int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
					HttpClient client = new DefaultHttpClient(httpParams);
					System.out.println("Roopgundeep HTTP clients link: "+LINK );
					HttpPost request = new HttpPost(LINK);
					System.out.println("Roopgundeep HTTP clients link: "+LINK );
					String p="abcd";
					BasicNameValuePair regIDBasicNameValuePair = new BasicNameValuePair("friends", json);
					BasicNameValuePair userIDBasicNameValuePair = new BasicNameValuePair("userID", id);
					BasicNameValuePair nodeBasicNameValuePair = new BasicNameValuePair("node", NODE);
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					nameValuePairList.add(regIDBasicNameValuePair);
					nameValuePairList.add(userIDBasicNameValuePair);
					nameValuePairList.add(nodeBasicNameValuePair);
					
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
					request.setEntity(urlEncodedFormEntity);
					HttpResponse response = client.execute(request);
					
//					HttpGet httpGet = new HttpGet(website);
//					System.out.println("Roopgundeep HTTP clients link: "+link );
//					System.out.println("Roopgundeep HTTP clients "+website );
//					HttpResponse httpResponse = httpClient.execute(httpGet);
//					System.out.println("Roopgundeep HTTP response " + httpResponse);
//					System.out.println("httpResponse");

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
			
			}			
		}
		// Initialize the AsyncTask class
		HttpPostAsyncTask httpGetAsyncTask = new HttpPostAsyncTask(); 
		httpGetAsyncTask.execute(link,json,id); 
	}
}