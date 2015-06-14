package com.example.drawernavigationtabs.foursquare;

/**
 * Used as an example of holding onto a fetched token. You'd want to persist
 * the token in a real application so the user does not have to authenticate
 * every time they use the app.
 * 
 * Note that you should encrypt the token when persisting.
 * 
 * @date 2013-06-01
 */
public class ExampleTokenStore {
	private static ExampleTokenStore sInstance;
	private String token;
	
	public static ExampleTokenStore get() {
		if (sInstance == null) {
			sInstance = new ExampleTokenStore();
		}
		
		return sInstance;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}
