package com.example.drawernavigationtabs;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;


@SuppressLint("NewApi")
public class FacebookShare extends Activity {
	
	private UiLifecycleHelper uiHelper;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
		.setLink("https://www.facebook.com/hub.theApp")
		.setPicture("https://www.facebook.com/hub.theApp/photos/a.468081896628577.1073741825.468081709961929/469469239823176/?type=1&theater")
		.setName("NA1ME").setCaption("Caption")
		.setDescription("Descript1ion").build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
		finish();
	}
	
	
}
