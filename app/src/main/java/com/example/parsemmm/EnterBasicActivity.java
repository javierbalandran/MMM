package com.example.parsemmm;

import com.parse.ParseException;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EnterBasicActivity extends Activity implements OnItemSelectedListener{

	EditText firstnameET;
	EditText lastnameET;
	EditText middleInitialET;
	String ID;
	String bloodType;
	ParseUser user;
	int flow;
	Spinner bloodSpinner; 
	Spinner daySpinner;
	Spinner monthSpinner;
	EditText yearET;
	String day;
	String month;
	int bloodTypePosition = 0;
	int dayPosition = 0;
	int monthPosition = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_basic);
		
		Bundle bundle = this.getIntent().getExtras();
		ID = bundle.getString("userid");
		flow = bundle.getInt("pageFlow");
		/*
		 * If flow = 1, first time creation. Continue brings us to Create Contact Page
		 * flow = 2, editing information. Continue brings us back to View Info Activity
		 */
		
		//ParseQuery for finding user
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		
		try {
			//Find user that is equal to ID passed through bundle
			user = query.get(ID);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.firstnameET = (EditText)findViewById(R.id.firstNameEditText);
		this.lastnameET = (EditText)findViewById(R.id.lastNameEditText);
		this.middleInitialET = (EditText)findViewById(R.id.middleInitialEditText);
		this.yearET = (EditText)findViewById(R.id.yearEditText);
		
		
		//Create and set up all spinners (Drop Down Menus)
		this.bloodSpinner = (Spinner) findViewById(R.id.bloodSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.blood_type_array, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bloodSpinner.setAdapter(adapter);
		bloodSpinner.setOnItemSelectedListener(this);
		
		this.daySpinner = (Spinner) findViewById(R.id.daySpinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, 
				R.array.day_array, android.R.layout.simple_spinner_item);
		
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		daySpinner.setAdapter(adapter2);
		daySpinner.setOnItemSelectedListener(this);
		
		this.monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, 
				R.array.month_array, android.R.layout.simple_spinner_item);
		
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		monthSpinner.setAdapter(adapter3);
		monthSpinner.setOnItemSelectedListener(this);
		if(flow == 2){
			
			//User already exists, fill edit text fields with current values
			firstnameET.setHint(user.getString("first_name"));
			lastnameET.setHint(user.getString("last_name"));
			middleInitialET.setHint(user.getString("middle_initial"));
			bloodSpinner.setSelection(user.getInt("blood_type_position"));
			daySpinner.setSelection(user.getInt("day_position"));
			monthSpinner.setSelection(user.getInt("month_position"));
			yearET.setHint(user.getString("year"));
		}
		
		((Button)findViewById(R.id.enterButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				Intent i = new Intent();
				if(flow == 1){
					//First time creation, fill all values
					
					user.put("first_name",firstnameET.getText().toString());
					user.put("last_name",lastnameET.getText().toString());
					user.put("middle_initial",middleInitialET.getText().toString());
					user.put("year",yearET.getText().toString());
					
					user.put("blood_type", bloodType);
					user.put("blood_type_position", bloodTypePosition);
					
					user.put("day", day);
					user.put("day_position",dayPosition);
					user.put("month", month);
					user.put("month_position", monthPosition);
					
					user.saveInBackground();
					
					i.setClass(EnterBasicActivity.this, EnterContactActivity.class);
					i.putExtra("contactID", 0);
					i.putExtra("pageFlow", 1);
				}
				else if(flow == 2){
					
					//only change values if there has been change, other leave it
					if(!firstnameET.getText().toString().equals("")){
						user.put("first_name",firstnameET.getText().toString());
					}
					if(!lastnameET.getText().toString().equals("")){
						user.put("last_name",lastnameET.getText().toString());
					}
					if(!middleInitialET.getText().toString().equals("")){
						user.put("middle_initial",middleInitialET.getText().toString());
					}
					if(!yearET.getText().toString().equals("")){
						user.put("year",yearET.getText().toString());
					}
					
					
					user.put("blood_type_position", bloodTypePosition);
					user.put("blood_type", bloodType);
					user.put("day", day);
					user.put("day_position",dayPosition);
					user.put("month", month);
					user.put("month_position", monthPosition);
					user.saveInBackground();
					i.setClass(EnterBasicActivity.this, ViewInfoActivity.class);
				}
				message("saved");
				i.putExtra("userid", ID);
                startActivity(i);
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
		getMenuInflater().inflate(R.menu.enter_basic, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		//detects Spinner changes
		switch(parent.getId()){
			case R.id.bloodSpinner: //detected change in blood type spinner, change values
				bloodType = (String) parent.getItemAtPosition(pos);
				bloodTypePosition = pos;
				break;
			case R.id.daySpinner:
				day = (String) parent.getItemAtPosition(pos);
				dayPosition = pos;
				break;
			case R.id.monthSpinner:
				month = (String)parent.getItemAtPosition(pos);
				monthPosition = pos; 
				break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
