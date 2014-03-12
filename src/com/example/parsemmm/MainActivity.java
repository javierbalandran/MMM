package com.example.parsemmm;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	TextView firstnameTextView;
	TextView lastnameTextView;
	
	ParseUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Parse.initialize(this, "aG9y9eYlMDTFaHWBDUcZWdj9IwlOFX16PLmcJUIO", "C6RxUzTiN4tki5t9zn7bAtvB8DPYy6FeUsvFqZrr"); 
		
		final ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser != null){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main_return);
			
			ParseQuery<ParseUser> query = ParseUser.getQuery();
			
			try {
				user = query.get(currentUser.getObjectId());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.firstnameTextView = (TextView) findViewById(R.id.firstNameReturnTextView);
			this.lastnameTextView = (TextView) findViewById(R.id.lastNameReturnTextView);
			firstnameTextView.setText(user.getString("first_name")+ " ");
			lastnameTextView.setText(user.getString("last_name")+ "!");
			
			
			((Button)findViewById(R.id.returnContinueButton)).setOnClickListener(new OnClickListener() {
				@Override
	            public void onClick(View v) {
					Intent i = new Intent();
					i.putExtra("userid", currentUser.getObjectId());
	                i.setClass(MainActivity.this, ViewInfoActivity.class);
	                startActivity(i);
	                
	            }
			});
			
			((Button)findViewById(R.id.logOutButton)).setOnClickListener(new OnClickListener() {
				@Override
	            public void onClick(View v) {
					ParseUser.logOut();
					finish();
					startActivity(getIntent());
	                
	            }
			});
				
		}
		else{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			
			((Button)findViewById(R.id.login_Button)).setOnClickListener(new OnClickListener() {
				@Override
	            public void onClick(View v) {
					Intent i = new Intent();
	                i.setClass(MainActivity.this, LoginActivity.class);
	                startActivity(i);
	                
	            }
			});
			
			((Button)findViewById(R.id.register_Button)).setOnClickListener(new OnClickListener() {
				@Override
	            public void onClick(View v) {
					Intent i = new Intent();
	                i.setClass(MainActivity.this, RegisterActivity.class);
	                startActivity(i);
	            }
			});
		}
	}

	@Override
	public void onBackPressed(){
		System.exit(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
