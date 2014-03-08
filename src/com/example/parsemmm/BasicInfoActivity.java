package com.example.parsemmm;

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


/*

NOT USED IN FINAL APP

*/
public class BasicInfoActivity extends Activity {

	
	TextView firstnameTV;
	TextView middleInitialTV;
	TextView lastnameTV;
	TextView dobTV;
	TextView bloodTypeTV;
	String ID;
	ParseUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_info);
		Bundle bundle = this.getIntent().getExtras();
		ID = bundle.getString("userid");
		
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		
		try {
			user = query.get(ID);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.firstnameTV = (TextView)findViewById(R.id.firstNameDisplay);
		firstnameTV.setText(user.getString("first_name"));
		
		this.middleInitialTV = (TextView)findViewById(R.id.middleInitialDisplay);
		middleInitialTV.setText(user.getString("middle_initial"));
		
		this.lastnameTV = (TextView)findViewById(R.id.lastNameDisplay);
		lastnameTV.setText(user.getString("last_name"));
		
		//this.dobTV = (TextView)findViewById(R.id.dateDisplay);
		dobTV.setText(user.getString("dob"));
		
		this.bloodTypeTV = (TextView)findViewById(R.id.bloodTypeDisplay);
		bloodTypeTV.setText(user.getString("blood_type"));
		
		
		
		((Button)findViewById(R.id.basicEditButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("userid",ID);
				i.putExtra("pageFlow", 2);
                i.setClass(BasicInfoActivity.this, EnterBasicActivity.class);
                startActivity(i);
                
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basic_info, menu);
		return true;
	}

}
