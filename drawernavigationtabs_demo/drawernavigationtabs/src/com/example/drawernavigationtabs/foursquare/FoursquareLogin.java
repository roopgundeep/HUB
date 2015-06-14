package com.example.drawernavigationtabs.foursquare;

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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foursquare.android.nativeoauth.FoursquareCancelException;
import com.foursquare.android.nativeoauth.FoursquareDenyException;
import com.foursquare.android.nativeoauth.FoursquareInvalidRequestException;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.FoursquareOAuthException;
import com.foursquare.android.nativeoauth.FoursquareUnsupportedVersionException;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;
import com.example.drawernavigationtabs.DemoTabbedFragmentActivity;
import com.example.drawernavigationtabs.MainActivity;
import com.example.drawernavigationtabs.R;
import com.example.drawernavigationtabs.database.SqlHandler;

/**
 * A sample activity demonstrating usage of the Foursquare auth library.
 * 
 * @date 2013-06-01
 */
public class FoursquareLogin extends FragmentActivity {
    
    private static final int REQUEST_CODE_FSQ_CONNECT = 200;
    private static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;
    public SqlHandler sqlHandler;
    public String userID;
    public String facebook_reg="0";
	public String interests_reg="0";
	public String foursquare_reg="0";
    /**
     * Obtain your client id and secret from: 
     * https://foursquare.com/developers/apps
     */
    private static final String CLIENT_ID = "GRCXDIXVLVXO3CXUQIXGO5UTHA3CBR445TPQGCVNKJSMZVOE";
    private static final String CLIENT_SECRET = "NNHQ4A3F3SPVLFRU0BLKJZ3IHTVPQZEJGN4XRFQP4T1J1ZPB";
    public static String LINK="http://54.68.32.118:8080/registerFoursquare";
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlHandler = new SqlHandler(this);	
        //getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
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
        setContentView(R.layout.foursquare_login);
        ensureUi();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_FSQ_CONNECT:
                onCompleteConnect(resultCode, data);
                break;
                
            case REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
                onCompleteTokenExchange(resultCode, data);
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
    
    /**
     * Update the UI. If we already fetched a token, we'll just show a success
     * message.
     */
    private void ensureUi() {
        boolean isAuthorized = !TextUtils.isEmpty(ExampleTokenStore.get().getToken());
        
        TextView tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvMessage.setVisibility(isAuthorized ? View.VISIBLE : View.GONE);
        
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setVisibility(isAuthorized ? View.GONE : View.VISIBLE);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the native auth flow.
                Intent intent = FoursquareOAuth.getConnectIntent(FoursquareLogin.this, CLIENT_ID);
                
                // If the device does not have the Foursquare app installed, we'd
                // get an intent back that would open the Play Store for download.
                // Otherwise we start the auth flow.
                if (FoursquareOAuth.isPlayStoreIntent(intent)) {
                    toastMessage(FoursquareLogin.this, getString(R.string.app_not_installed_message));
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);
                }
            }
        });
    }
    
    private void onCompleteConnect(int resultCode, Intent data) {
        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
        Exception exception = codeResponse.getException();
        
        if (exception == null) {
            // Success.
            String code = codeResponse.getCode();
            performTokenExchange(code);

        } else {
            if (exception instanceof FoursquareCancelException) {
                // Cancel.
                toastMessage(this, "Canceled");

            } else if (exception instanceof FoursquareDenyException) {
                // Deny.
            	toastMessage(this, "Denied");
                
            } else if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = exception.getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                toastMessage(this, errorMessage + " [" + errorCode + "]");
                
            } else if (exception instanceof FoursquareUnsupportedVersionException) {
                // Unsupported Fourquare app version on the device.
            	toastError(this, exception);
                
            } else if (exception instanceof FoursquareInvalidRequestException) {
                // Invalid request.
            	toastError(this, exception);
                
            } else {
                // Error.
            	toastError(this, exception);
            }
        }
    }
    
    private void onCompleteTokenExchange(int resultCode, Intent data) {
        AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
        Exception exception = tokenResponse.getException();
        
        if (exception == null) {
            String accessToken = tokenResponse.getAccessToken();
            // Success.
           // toastMessage(this, "Access token: " + accessToken);
            
            //Insert into DB
            
            // Persist the token for later use. In this example, we save
            // it to shared prefs.
            foursquare_reg="1";
    		String query1 = "INSERT INTO REGISTER(facebook , interests , foursquare ) "
    				+ "values ("
    				+ facebook_reg+ ", "+ interests_reg+ ", "+ foursquare_reg+ ")";
    		System.out.println("roop DB "+query1);
    		System.out.println("Roop DB query query " + query1);
    		sqlHandler.executeQuery(query1);
            connectWithHttpGet(accessToken);
            ExampleTokenStore.get().setToken(accessToken);
            Intent i = new Intent(this,MainActivity.class);
    		startActivity(i);
    		finish();

            
        } else {
            if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = ((FoursquareOAuthException) exception).getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                toastMessage(this, errorMessage + " [" + errorCode + "]");
                
            } else {
                // Other exception type.
            	toastError(this, exception);
            }
        }
    }
    private void connectWithHttpGet(String auth) {

		class HttpPostAsyncTask extends AsyncTask<String, String, String>{
			
			@Override
			protected void onPreExecute() {
				
			}
			@Override
			protected String doInBackground(String... params) {
				
				String auth = params[0];
		
				System.out.println("Roopgundeep HTTP clients " );
				try {
					
					int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
					HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
					HttpClient client = new DefaultHttpClient(httpParams);
					HttpPost request = new HttpPost(LINK);
					String p="abcd";
					//BasicNameValuePair regIDBasicNameValuePair = new BasicNameValuePair("friends", json);
					BasicNameValuePair accessTokenBasicNameValuePair = new BasicNameValuePair("accessToken", auth);
					BasicNameValuePair userIDBasicNameValuePair = new BasicNameValuePair("userID", userID);
					List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
					//nameValuePairList.add(regIDBasicNameValuePair);
					nameValuePairList.add(accessTokenBasicNameValuePair);
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
			
			}			
		}
		// Initialize the AsyncTask class
		HttpPostAsyncTask httpGetAsyncTask = new HttpPostAsyncTask(); 
		httpGetAsyncTask.execute(auth); 
	}
    /**
     * Exchange a code for an OAuth Token. Note that we do not recommend you
     * do this in your app, rather do the exchange on your server. Added here
     * for demo purposes.
     * 
     * @param code 
     *          The auth code returned from the native auth flow.
     */
    private void performTokenExchange(String code) {
        Intent intent = FoursquareOAuth.getTokenExchangeIntent(this, CLIENT_ID, CLIENT_SECRET, code);
        startActivityForResult(intent, REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
    }
    
    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastError(Context context, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
