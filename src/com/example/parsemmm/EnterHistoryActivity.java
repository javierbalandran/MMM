package com.example.parsemmm;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
import android.widget.TextView;
import android.widget.Toast;

public class EnterHistoryActivity extends Activity implements OnItemSelectedListener{

	String ID;
	int flow;
	String historyID;
	
	TextView historyTitleTV;
	EditText historyNameET; 
	Spinner daySpinner;
	Spinner monthSpinner;
	EditText yearET;
	EditText historyDescriptionET;
	
	ParseObject history; 
	
	String day;
	String month;
	int dayPosition;
	int monthPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_history);
		
		Bundle bundle = this.getIntent().getExtras();
		ID = bundle.getString("userid");
		flow = bundle.getInt("pageFlow");
		historyID = bundle.getString("historyid");
		
		this.historyTitleTV = (TextView)findViewById(R.id.historyTextView);
		this.historyNameET = (EditText)findViewById(R.id.historyTitleEditText);
		this.daySpinner = (Spinner)findViewById(R.id.historyDaySpinner);
		this.monthSpinner = (Spinner)findViewById(R.id.historyMonthSpinner);
		this.yearET = (EditText)findViewById(R.id.historyYearEditText);
		this.historyDescriptionET = (EditText)findViewById(R.id.historyDescriptionEditText);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.day_array, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		daySpinner.setAdapter(adapter);
		daySpinner.setOnItemSelectedListener(this);
		
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, 
				R.array.month_array, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		monthSpinner.setAdapter(adapter2);
		monthSpinner.setOnItemSelectedListener(this);
		
		
		if(flow == 1 || flow == 2){
			historyTitleTV.setText("Create Medical History Event");
		}
		else if(flow == 3){
			ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
			query.getInBackground(historyID, new GetCallback<ParseObject>() {
			  public void done(ParseObject get_history, ParseException e) {
			    if (e == null) {
			    	historyTitleTV.setText("Edit Medical History Event");
					historyNameET.setHint(get_history.getString("name"));
					historyDescriptionET.setHint(get_history.getString("description"));
					daySpinner.setSelection(get_history.getInt("day_position"));
					monthSpinner.setSelection(get_history.getInt("month_position"));
					yearET.setHint(get_history.getString("year"));
			    }
			    else
			    {
			    	
			    }
			  }
			});
		}
		
		((Button)findViewById(R.id.historyAdditionalButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				if(flow == 1 || flow == 2){
					history = new ParseObject("History");
					history.put("user",ID);
					history.put("name", historyNameET.getText().toString());
					history.put("description",historyDescriptionET.getText().toString());
					history.put("day", day);
					history.put("day_position", dayPosition);
					history.put("month", month);
					history.put("month_position", monthPosition);
					history.put("year", yearET.getText().toString());
					history.saveInBackground();
				}
				else if(flow == 3){
					ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
					query.getInBackground(historyID, new GetCallback<ParseObject>() {
					  public void done(ParseObject get_history, ParseException e) {
					    if (e == null) {
					    	if(!historyNameET.getText().toString().equals("")){
					    		get_history.put("name", historyNameET.getText().toString());
							}
							if(!historyDescriptionET.getText().toString().equals("")){
								get_history.put("description", historyDescriptionET.getText().toString());
							}
							if(!yearET.getText().toString().equals("")){
								get_history.put("year", yearET.getText().toString());
							}
							get_history.put("day", day);
							get_history.put("day_position", dayPosition);
							
							get_history.put("month", month);
							get_history.put("month_position", monthPosition);
							get_history.saveInBackground();
					    }
					    else
					    {
					    	
					    }
					  }
					});

				}
				
				message("saved");
				Intent i = new Intent();
				i.putExtra("userid",ID);
				i.putExtra("historyid", 0);
				i.putExtra("pageFlow", flow);
                i.setClass(EnterHistoryActivity.this, EnterHistoryActivity.class);
                startActivity(i);
            }
		});
		
		
		((Button)findViewById(R.id.historyContinueButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				
				Intent i = new Intent();
				i.putExtra("userid",ID);
				if(flow == 1 || flow == 2){
					history = new ParseObject("History");
					history.put("user",ID);
					history.put("name", historyNameET.getText().toString());
					history.put("description",historyDescriptionET.getText().toString());
					history.put("day", day);
					history.put("day_position", dayPosition);
					history.put("month", month);
					history.put("month_position", monthPosition);
					history.put("year", yearET.getText().toString());
					history.saveInBackground();
					
					i.putExtra("historyid", 0);
					i.putExtra("pageFlow", flow);
					
					if(flow == 1){
						i.setClass(EnterHistoryActivity.this, ViewInfoActivity.class); //Change to images
					}
					else if(flow == 2){
						i.setClass(EnterHistoryActivity.this, ViewInfoActivity.class);
					}
					
				}
				else if(flow == 3){
					ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
					query.getInBackground(historyID, new GetCallback<ParseObject>() {
					  public void done(ParseObject get_history, ParseException e) {
					    if (e == null) {
					    	if(!historyNameET.getText().toString().equals("")){
					    		get_history.put("name", historyNameET.getText().toString());
							}
							if(!historyDescriptionET.getText().toString().equals("")){
								get_history.put("description", historyDescriptionET.getText().toString());
							}
							if(!yearET.getText().toString().equals("")){
								get_history.put("year", yearET.getText().toString());
							}
							get_history.put("day", day);
							get_history.put("day_position", dayPosition);
							
							get_history.put("month", month);
							get_history.put("month_position", monthPosition);
							get_history.saveInBackground();
					    }
					    else
					    {
					    	
					    }
					  }
					});
					i.setClass(EnterHistoryActivity.this, ViewInfoActivity.class);
				}
				message("saved");
				
				startActivity(i);
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_history, menu);
		return true;
	}
	
	void message(String text){
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.show();
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
		switch(parent.getId()){
			case R.id.historyDaySpinner:
				day = (String) parent.getItemAtPosition(pos);
				dayPosition = pos;
				break;
			case R.id.historyMonthSpinner:
				month = (String)parent.getItemAtPosition(pos);
				monthPosition = pos; 
				break;
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
