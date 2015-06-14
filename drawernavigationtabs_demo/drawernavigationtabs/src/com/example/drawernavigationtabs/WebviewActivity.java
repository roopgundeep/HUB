package com.example.drawernavigationtabs;

import bolts.WebViewAppLinkResolver;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.web_view_detail);
       
        String url = getIntent().getStringExtra("url");
        //WebViewFragment fragobj = WebViewFragment.newInstance();
        WebView w = (WebView) findViewById(R.id.webview_detail);
        w.setWebViewClient(new MyBrowser());
        w.loadUrl(url);
       /* getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content_frame,
                WebViewFragment.newInstance(),
                WebViewFragment.TAG).commit();*/
        
    	/*getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content_frame, fragobj, fragobj.TAG).commit();*/

    }
	private class MyBrowser extends WebViewClient {
	      @Override
	      public boolean shouldOverrideUrlLoading(WebView view, String url) {
	         view.loadUrl(url);
	         return true;
	      }
	   }
}
