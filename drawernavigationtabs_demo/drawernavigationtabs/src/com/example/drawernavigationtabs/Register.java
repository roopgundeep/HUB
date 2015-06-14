package com.example.drawernavigationtabs;

import com.example.drawernavigationtabs.database.SqlHandler;
import com.example.drawernavigationtabs.MainActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends FragmentActivity {
	public SqlHandler sqlHandler;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.register);
		sqlHandler = new SqlHandler(this);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		Button done = (Button) findViewById(R.id.btnRegister);
		

		done.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  EditText usernameView= (EditText) findViewById(R.id.reg_fullname);
					EditText emailView= (EditText) findViewById(R.id.reg_email);
					EditText passwordView= (EditText) findViewById(R.id.reg_password);
				  		
				  		
				  		String name='"'+usernameView.getText().toString()+'"';
				  		String email='"'+emailView.getText().toString()+'"';
				  		String password='"'+passwordView.getText().toString()+'"';
				  		String query = "INSERT INTO USERS(name , emailId , password) " +
					    		"values ("
					      + name+", "+email+", "+password + ")";
				  		
				    sqlHandler.executeQuery(query);
				    
				    SharedPreferences sp = getSharedPreferences("valuedb", MODE_PRIVATE);
				    SharedPreferences.Editor editor = sp.edit();
				    editor.putInt("value", 1);
				    editor.commit();
				    System.out.println("roop: reg:email "+emailView.getText().toString());
			  		System.out.println("roop: reg:name "+usernameView.getText().toString());
			  		System.out.println("roop: reg:passwd "+passwordView.getText().toString());			  		
			  		finish();
			  		
			  }			  
		});
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
	        finish();
	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;

	    }

	    return true;
	}
}
