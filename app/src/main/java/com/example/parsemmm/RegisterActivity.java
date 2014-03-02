package com.example.parsemmm;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	EditText usernameET;
	EditText passwordET;
	EditText passwordCET;
	int FORWARD = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		this.usernameET = (EditText)findViewById(R.id.usernameEditText);
		this.passwordET = (EditText)findViewById(R.id.passwordEditText);
		this.passwordCET = (EditText)findViewById(R.id.confirmEditText);
		
		((Button)findViewById(R.id.registerButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				
				if(!passwordET.getText().toString().equals(passwordCET.getText().toString())){
					message("Error: Passwords do not match");
				}
				else{
					
					final ParseUser user = new ParseUser();
					user.setUsername(usernameET.getText().toString());
					user.setPassword(passwordET.getText().toString());
					
					user.signUpInBackground(new SignUpCallback() {
					  public void done(ParseException e) {
					    if (e == null) {
							Intent i = new Intent();
			                i.setClass(RegisterActivity.this, EnterBasicActivity.class);
			                i.putExtra("userid", user.getObjectId());
			                i.putExtra("pageFlow", FORWARD);
			                startActivity(i);
			                
					      // Hooray! Let them use the app now.
					    } else {
					    	message(e.getMessage());
					      // Sign up didn't succeed. Look at the ParseException
					      // to figure out what went wrong
					    }
					  }
					});
				}
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
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
