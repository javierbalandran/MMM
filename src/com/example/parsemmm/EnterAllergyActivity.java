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

public class EnterAllergyActivity extends Activity implements OnItemSelectedListener{

	String ID;
	int flow;
	String allergyID;
	EditText allergyNameET;
	EditText allergyDetailsET;
	TextView allergyTitleTV;
	Spinner allergyTypeSpinner;
	String allergyType;
	int allergyTypePosition;
	ParseObject allergy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_allergy);
		
		Bundle bundle = this.getIntent().getExtras();
		ID = bundle.getString("userid");
		flow = bundle.getInt("pageFlow");
		
		/* flow = 1; Create allergy, continue to medication
		 * flow = 2; Create allergy, continue to view allergy
		 * flow = 3; Edit allergy, continue to view allergy
		 */
		
		allergyID = bundle.getString("allergyid");
		
		
		this.allergyNameET = (EditText)findViewById(R.id.allergyNameEditText);
		this.allergyDetailsET = (EditText)findViewById(R.id.allergyDetailsEditText);
		this.allergyTitleTV = (TextView)findViewById(R.id.allergyTitleTextView);
		
		this.allergyTypeSpinner = (Spinner) findViewById(R.id.allergyTypeSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.allergy_type_array, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		allergyTypeSpinner.setAdapter(adapter);
		allergyTypeSpinner.setOnItemSelectedListener(this);
		
		if(flow == 1 || flow ==  2){
			allergyTitleTV.setText("Create Allergy");
		}
		else if(flow == 3){
			allergyTitleTV.setText("Edit Allergy");
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Allergy");
			query.getInBackground(allergyID, new GetCallback<ParseObject>() {
			  public void done(ParseObject get_allergy, ParseException e) {
			    if (e == null) {
			    	allergyTitleTV.setText("Edit Allergy");
					allergyNameET.setHint(get_allergy.getString("name"));
					allergyDetailsET.setHint(get_allergy.getString("details"));
					allergyTypeSpinner.setSelection(get_allergy.getInt("allergy_type_position"));
			    }
			    else
			    {
			    	
			    }
			  }
			});
		}
		
		((Button)findViewById(R.id.additionalAllergyButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				if(flow == 1 || flow == 2){
					allergy = new ParseObject("Allergy");
					allergy.put("user",ID);
					allergy.put("name", allergyNameET.getText().toString());
					allergy.put("details",allergyDetailsET.getText().toString());
					allergy.put("type", allergyType);
					allergy.put("typePosition", allergyTypePosition);
				}
				else if(flow == 3){
					
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Allergy");
					query.getInBackground(allergyID, new GetCallback<ParseObject>() {
					  public void done(ParseObject get_allergy, ParseException e) {
					    if (e == null) {
					    	if(!allergyNameET.getText().toString().equals("")){
					    		get_allergy.put("name", allergyNameET.getText().toString());
							}
							if(!allergyDetailsET.getText().toString().equals("")){
								get_allergy.put("details", allergyDetailsET.getText().toString());
							}
							get_allergy.put("type", allergyType);
							get_allergy.put("typePosition", allergyTypePosition);
							get_allergy.saveInBackground();
					    }
					    else
					    {
					    	
					    }
					  }
					});
					
				}
				
				
				Intent i = new Intent();
				i.putExtra("userid",ID);
				i.putExtra("allergyid", 0);
				i.putExtra("pageFlow", flow);
                i.setClass(EnterAllergyActivity.this, EnterAllergyActivity.class);
                startActivity(i);
            }
		});
		
		/* flow = 1; Create allergy, continue to medication
		 * flow = 2; Create allergy, continue to view allergy
		 * flow = 3; Edit allergy, continue to view allergy
		 */
		
		
		((Button)findViewById(R.id.continueAllergyButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("userid",ID);
				
				if(flow == 1 || flow == 2){
					allergy = new ParseObject("Allergy");
					allergy.put("user",ID);
					allergy.put("name", allergyNameET.getText().toString());
					allergy.put("details",allergyDetailsET.getText().toString());
					allergy.put("type", allergyType);
					allergy.put("typePosition", allergyTypePosition);
					allergy.saveInBackground();
					
					
					if(flow == 1){
						i.putExtra("pageFlow", 1);
						i.setClass(EnterAllergyActivity.this, EnterMedicationActivity.class); //Change to Medication
					}
					else if(flow == 2){
						i.setClass(EnterAllergyActivity.this, ViewInfoActivity.class);
					}
					
	                
				}
				else if(flow == 3){
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Allergy");
					query.getInBackground(allergyID, new GetCallback<ParseObject>() {
					  public void done(ParseObject get_allergy, ParseException e) {
					    if (e == null) {
					    	if(!allergyNameET.getText().toString().equals("")){
					    		get_allergy.put("name", allergyNameET.getText().toString());
							}
							if(!allergyDetailsET.getText().toString().equals("")){
								get_allergy.put("details", allergyNameET.getText().toString());
							}
							get_allergy.put("type", allergyType);
							get_allergy.put("typePosition", allergyTypePosition);
							get_allergy.saveInBackground();
					    }
					    else
					    {
					    	
					    }
					  }
					});
					i.setClass(EnterAllergyActivity.this,ViewInfoActivity.class);
				}
				
				
                startActivity(i);
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_allergy, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		allergyType = (String) parent.getItemAtPosition(pos);
		allergyTypePosition = pos;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	void message(String text){
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.show();
	}


}
