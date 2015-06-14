package com.example.drawernavigationtabs;
 
import com.example.drawernavigationtabs.GamesFragment;
import com.example.drawernavigationtabs.TopRatedFragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;

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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class DemoTabbedFragmentOld extends Fragment {
 
	public static final String TAG = DemoTabbedFragmentOld.class.getSimpleName();
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	SharedPreferences sh_Pref;
	public SqlHandler sqlHandler;
	// Your Facebook APP ID
	private static String APP_ID = "374944869307757"; // Replace with your App
														//ID
	public Button done;
	// Instance of Facebook Class
	@SuppressWarnings("deprecation")
	private Facebook facebook = new Facebook(APP_ID);
	@SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;

    public static DemoTabbedFragmentOld newInstance() {
        return new DemoTabbedFragmentOld();
    }
     
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
        sqlHandler = new SqlHandler(getActivity());        
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	super.onCreateView(inflater, container, savedInstanceState);
    	View v = inflater.inflate(R.layout.fragment_demo_tabbed, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());
        mViewPager = (ViewPager) v.findViewById(R.id.demoPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                // do transformation here
            	final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setRotationY(position * -30);
                }
        });
		Cursor cursor = sqlHandler.selectQuery("SELECT * FROM USERS");
		if (cursor != null && cursor.getCount() > 0) {
			v.findViewById(R.id.signUP).setVisibility(View.GONE);
		} 
		else {
			v.findViewById(R.id.signUP).setVisibility(View.VISIBLE);

			done = (Button) v.findViewById(R.id.signUP);

			done.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("Roopgundeep: Image Button" + "button Clicked");
					loginToFacebook();
				}
			});
		}
        return v;
    }

	public void loginToFacebook() {

		mPrefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		
		System.out.println("Roopgundeep: in login to facebook");
		if (access_token != null) {
			System.out.println("Roopgundeep: access_token not null");
			facebook.setAccessToken(access_token);		
			Log.d("FB Sessions", "" + "Roopgundeep "+facebook.isSessionValid());
		}

		if (expires != 0) {
			System.out.println("Roopgundeep: expires !=0");
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			System.out.println("Roopgundeep: is Session Valid");
			facebook.authorize(this, new String[] { "email", "publish_stream","user_likes","user_friends" }, 
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
					System.out.println("Roopgundeep: OnComplete");
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token",
							facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();
					System.out.println("Roopgundeep: get profile calling");
					getProfileInformation();
					System.out.println("Roopgundeep: get profile done");
					done.setVisibility(View.GONE);								
					onDetach();
					// Making Login button invisible
				}
				@Override
				public void onError(DialogError error) {
					// Function to handle error
					System.out.println("Roopgundeep: OnDialogError:"+error);
				}
				@Override
				public void onFacebookError(FacebookError fberror) {
					// Function to handle Facebook errors
					System.out.println("Roopgundeep: OnFbError: "+fberror);
				}
			});
		}
	}   
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
		
    	super.onActivityResult(requestCode, resultCode, data);
    	System.out.println("Roopgundeep: onActivityResult");
    	facebook.authorizeCallback(requestCode, resultCode, data);
    	getProfileInformation();
		System.out.println("Roopgundeep: get profile ActivityResult done");
		done.setVisibility(View.GONE);		
		//getSupportFragmentManager().removeOnBackStackChangedListener(null);
		onDetach();
    }

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();			
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
				
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);
					// getting name of the user
					String name = profile.getString("name");
		
					String email = profile.getString("email");
					String id = profile.getString("id");
					String first_name = profile.getString("first_name");
					String last_name = profile.getString("last_name");
					System.out.println("Roop Email: " + email + "-id: " + id
							+ "-first_name:" + first_name);
					name = '"' + name + '"';
					email = '"' + email + '"';
					first_name = '"' + first_name + '"';
					last_name = '"' + last_name + '"';
					id = '"' + id + '"';
					String query = "INSERT INTO USERS(name , emailId , facebookId, first_name, last_name ) "
							+ "values ("
							+ name+ ", "+ email+ ", "+ id +", "+ first_name+", "+ last_name+ ")";
					sqlHandler.executeQuery(query);
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
		mAsyncRunner.request("me/friends", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				final String json = response;
				System.out.println("roop: Json"+json);
				
				try {
					// Facebook Profile JSON data
					JSONObject FriendsJson = new JSONObject(json);
					// getting name of the user
					JSONArray friendsData = FriendsJson.getJSONArray("data");
					String ids[] , names[] = new String[friendsData.length()];
					for(int i = 0; i < friendsData .length(); i++){ 
					        String ids1 = friendsData .getJSONObject(i).getString("id");
					        String names1 = friendsData .getJSONObject(i).getString("name");
					        System.out.println("Roopgundeep name: "+names1+",id: "+ids1);
					        }
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
		mAsyncRunner.logout(getActivity(), new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				if (Boolean.parseBoolean(response) == true) {
					getActivity().runOnUiThread(new Runnable() {
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
//            Fragment fragment = new TabbedContentFragment();
//            Bundle args = new Bundle();
//            args.putInt(TabbedContentFragment.ARG_SECTION_NUMBER, position + 1);
//            fragment.setArguments(args);
//            return fragment;
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
}