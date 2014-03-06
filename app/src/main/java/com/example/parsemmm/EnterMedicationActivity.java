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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EnterMedicationActivity extends Activity {

	
	String ID;
	int flow;
	String medicationID;
	ParseObject medication;
	
	TextView medicationTitleTV;
	EditText medicationNameET;
	EditText medicationAmountET;
	EditText medicationIntervalET;
	EditText medicationDescriptionET;
	RadioGroup medicationRG;
	RadioButton prescribedRB;
	RadioButton otcRB;
	String PorOTC;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_medication);
		
		
		Bundle bundle = this.getIntent().getExtras();
		ID = bundle.getString("userid");
		flow = bundle.getInt("pageFlow");
		/* flow = 1; Create contact, continue to allergy
		 * flow = 2; Create contact, continue to view contact
		 * flow = 3; Edit contact, continue to view contact
		 */
		medicationID = bundle.getString("medicationid");
		
		this.medicationNameET = (EditText)findViewById(R.id.medicationNameEditText);
		this.medicationAmountET = (EditText)findViewById(R.id.medicationAmountEditText);
		this.medicationIntervalET = (EditText)findViewById(R.id.medicationIntervalEditText);
		this.medicationDescriptionET = (EditText)findViewById(R.id.medicationDescriptionEditText);
		this.medicationRG = (RadioGroup)findViewById(R.id.medicationRadioGroup);
		this.prescribedRB = (RadioButton)findViewById(R.id.prescribedRadioButton);
		this.otcRB = (RadioButton)findViewById(R.id.OTCRadioButton);
		this.medicationTitleTV = (TextView)findViewById(R.id.medicationTextView);
		if(flow == 1 || flow == 2){
			medicationTitleTV.setText("Create Medication");
		}
		else if(flow == 3){
			medicationTitleTV.setText("Edit Medication");
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Medication");
			query.getInBackground(medicationID, new GetCallback<ParseObject>() {
			  public void done(ParseObject get_medication, ParseException e) {
			    if (e == null) {
			    	medicationNameET.setHint(get_medication.getString("name"));
			    	medicationAmountET.setHint(get_medication.getString("amount"));
			    	medicationIntervalET.setHint(get_medication.getString("interval"));
			    	medicationDescriptionET.setHint(get_medication.getString("description"));
			    	if(get_medication.getString("source").equals("Prescribed")){
			    		medicationRG.check(R.id.prescribedRadioButton);
			    	}
			    	else if(get_medication.getString("source").equals("Over the Counter")){
			    		medicationRG.check(R.id.OTCRadioButton);
			    	}
			    }
			    else
			    {
			    	
			    }
			  }
			});
			
		}
		
		
		
		((Button)findViewById(R.id.additionalMedicationButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				
				if(medicationRG.getCheckedRadioButtonId()== R.id.prescribedRadioButton){
					PorOTC = "Prescribed";
				}
				else{
					PorOTC = "Over the Counter";
				}
				
				if(flow == 1 || flow == 2){
					medication = new ParseObject("Medication");
					medication.put("user", ID);
					medication.put("name",medicationNameET.getText().toString());
					medication.put("amount",medicationAmountET.getText().toString());
					medication.put("interval",medicationIntervalET.getText().toString());
					medication.put("description",medicationDescriptionET.getText().toString());
					medication.put("source", PorOTC);
					medication.saveInBackground();
				}
				
				else if(flow == 3){
					
					
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Medication");
					query.getInBackground(medicationID, new GetCallback<ParseObject>() {
					  public void done(ParseObject get_medication, ParseException e) {
					    if (e == null) {
					    	if(!medicationNameET.getText().toString().equals("")){
					    		get_medication.put("name",medicationNameET.getText().toString());
							}
							if(!medicationAmountET.getText().toString().equals("")){
								get_medication.put("amount",medicationAmountET.getText().toString());
							}
							if(!medicationIntervalET.getText().toString().equals("")){
								get_medication.put("interval",medicationIntervalET.getText().toString());
							}
							if(!medicationDescriptionET.getText().toString().equals("")){
								get_medication.put("description",medicationDescriptionET.getText().toString());
							}
							get_medication.put("source", PorOTC);
							get_medication.saveInBackground();
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
				i.putExtra("medicationid", 0);
				if(flow == 3){
					flow = 2;
				}
				i.putExtra("pageFlow", flow);
                i.setClass(EnterMedicationActivity.this, EnterMedicationActivity.class);
                startActivity(i);
            }
		});
		
		
		/* flow = 1; Create medication, continue to history
		 * flow = 2; Create medication, continue to view medication
		 * flow = 3; Edit medication, continue to view medication
		 */
		
		((Button)findViewById(R.id.continueMedicationButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				
				if(medicationRG.getCheckedRadioButtonId()== R.id.prescribedRadioButton){
					PorOTC = "Prescribed";
				}
				else if(medicationRG.getCheckedRadioButtonId()== R.id.OTCRadioButton){
					PorOTC = "Over the Counter";
				}
				else{
					PorOTC = "";
				}
				if(flow == 1 || flow == 2){
					medication = new ParseObject("Medication");
					medication.put("user", ID);
					medication.put("name",medicationNameET.getText().toString());
					medication.put("amount",medicationAmountET.getText().toString());
					medication.put("interval",medicationIntervalET.getText().toString());
					medication.put("description",medicationDescriptionET.getText().toString());
					medication.put("source", PorOTC);
					medication.saveInBackground();
					if(flow == 1){
						Intent i = new Intent();
						i.putExtra("userid",ID);
						i.putExtra("historyid", 0);
						i.putExtra("pageFlow", flow);
		                i.setClass(EnterMedicationActivity.this, EnterHistoryActivity.class);
		                startActivity(i);
					}
					else if(flow == 2){
						Intent i = new Intent();
						i.putExtra("userid",ID);
		                i.setClass(EnterMedicationActivity.this, ViewInfoActivity.class);
		                startActivity(i);
					}
				}
				else if(flow == 3){
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Medication");
					query.getInBackground(medicationID, new GetCallback<ParseObject>() {
					  public void done(ParseObject get_medication, ParseException e) {
					    if (e == null) {
					    	if(!medicationNameET.getText().toString().equals("")){
					    		get_medication.put("name",medicationNameET.getText().toString());
							}
							if(!medicationAmountET.getText().toString().equals("")){
								get_medication.put("amount",medicationAmountET.getText().toString());
							}
							if(!medicationIntervalET.getText().toString().equals("")){
								get_medication.put("interval",medicationIntervalET.getText().toString());
							}
							if(!medicationDescriptionET.getText().toString().equals("")){
								get_medication.put("description",medicationDescriptionET.getText().toString());
							}
							get_medication.put("source", PorOTC);
							get_medication.saveInBackground();
					    }
					    else
					    {
					    	
					    }
					  }
					});
					message("saved");
					Intent i = new Intent();
					i.putExtra("userid",ID);
	                i.setClass(EnterMedicationActivity.this, ViewInfoActivity.class);
	                startActivity(i);
					    
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
		getMenuInflater().inflate(R.menu.enter_medication_history, menu);
		return true;
	}

}
