package com.example.parsemmm;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LoginActivity extends Activity {

	EditText usernameET;
	EditText passwordET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.usernameET = (EditText)findViewById(R.id.loginUsernameEditText);
		this.passwordET = (EditText)findViewById(R.id.loginPasswordEditText);
		
		((Button)findViewById(R.id.loginButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				ParseUser.logInInBackground(usernameET.getText().toString(), passwordET.getText().toString(), new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					    	
					    	Intent i = new Intent();
			                i.setClass(LoginActivity.this, BasicInfoActivity.class);
			                i.putExtra("userid", user.getObjectId());
			                startActivity(i);
			                
					      // Hooray! The user is logged in.
					    } else {
					    	message(e.getMessage());
					      // Signup failed. Look at the ParseException to see what happened.
					    }
					  }
					});
				
			}
		});
	}
	
	void message(String text){
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
