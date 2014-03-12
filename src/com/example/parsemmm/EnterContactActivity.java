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
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EnterContactActivity extends Activity implements OnItemSelectedListener{

	EditText contactNameET;
	Spinner contactTypeS;
	EditText contactRelationET;
	EditText homePhoneET;
	EditText cellPhoneET;
	EditText workPhoneET;
	TextView titleTV;
	String ID;
	int flow;
	String contactID;
	ParseObject contact;
	Spinner contactTypeSpinner;
	String contactType;
	int contactTypePosition = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_contact);
		
		Bundle bundle = this.getIntent().getExtras();
		ID = bundle.getString("userid");
		flow = bundle.getInt("pageFlow");
		/* flow = 1; Create contact, continue to allergy
		 * flow = 2; Create contact, continue to view contact
		 * flow = 3; Edit contact, continue to view contact
		 */
		contactID = bundle.getString("contactid");
		
		
		//assign all variables to widgets from the layout
		this.contactNameET = (EditText) findViewById(R.id.contactNameEditText);
		this.contactRelationET = (EditText) findViewById(R.id.contactRelationEditText);
		this.homePhoneET = (EditText)findViewById(R.id.contactPhoneEditText1);
		this.cellPhoneET = (EditText)findViewById(R.id.contactPhoneEditText2);
		this.workPhoneET = (EditText)findViewById(R.id.contactPhoneEditText3);
		this.titleTV = (TextView)findViewById(R.id.contactTextView);
		
		//Create and set up spinner
		this.contactTypeSpinner = (Spinner) findViewById(R.id.contactTypeSpinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
				R.array.contact_type_array, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		contactTypeSpinner.setAdapter(adapter);
		contactTypeSpinner.setOnItemSelectedListener(this);
		
		
		if(flow == 1 || flow == 2){ //Creating new contact, change title text 
			titleTV.setText("Create Contact");
		}
		else if(flow == 3){ //Editing contact
			titleTV.setText("Edit Contact");
			
			//Get the Contact from the Parse database
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
			query.getInBackground(contactID, new GetCallback<ParseObject>() {
			  public void done(ParseObject get_contact, ParseException e) {
			    if (e == null) {
			    	//set the Edit Text fields to values from the database
			    	contactNameET.setText(get_contact.getString("name"), TextView.BufferType.EDITABLE);
					contactRelationET.setText(get_contact.getString("relation"), TextView.BufferType.EDITABLE);
					homePhoneET.setText(get_contact.getString("homePhone"), TextView.BufferType.EDITABLE);
					cellPhoneET.setText(get_contact.getString("cellPhone"), TextView.BufferType.EDITABLE);
					workPhoneET.setText(get_contact.getString("workPhone"), TextView.BufferType.EDITABLE);
					contactTypeSpinner.setSelection(get_contact.getInt("contact_type_position"));
					contactType = get_contact.getString("contact_type");
			    }
			    else
			    {
			    	
			    }
			  }
			});
		}
		
		((Button)findViewById(R.id.contactSubmitButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				if(flow == 1 || flow == 2){ //Creating contact
					contact = new ParseObject("Contact");
					contact.put("user", ID);
					contact.put("name",contactNameET.getText().toString());
					contact.put("relation",contactRelationET.getText().toString());
					contact.put("homePhone",homePhoneET.getText().toString());
					contact.put("cellPhone",cellPhoneET.getText().toString());
					contact.put("workPhone",workPhoneET.getText().toString());
					contact.put("contact_type",contactType);
					contact.put("contact_type_position",contactTypePosition);
					
					contact.saveInBackground();
					if(flow == 1){ //Go to Allergy page
						Intent i = new Intent();
						i.putExtra("userid",ID);
						i.putExtra("allergyid", 0);
						i.putExtra("pageFlow", flow);
		                i.setClass(EnterContactActivity.this, EnterAllergyActivity.class);
		                startActivity(i);
					}
					else if(flow == 2){ //Go to View Info Page
						Intent i = new Intent();
						i.putExtra("userid",ID);
		                i.setClass(EnterContactActivity.this, ViewInfoActivity.class);
		                startActivity(i);
					}
				}
				else if(flow == 3){

					ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
					
					query.getInBackground(contactID, new GetCallback<ParseObject>() {
						  public void done(ParseObject get_contact, ParseException e) {
						    if (e == null) {
					    		get_contact.put("name",contactNameET.getText().toString());
								get_contact.put("relation",contactRelationET.getText().toString());
								get_contact.put("homePhone",homePhoneET.getText().toString());
								get_contact.put("cellPhone",cellPhoneET.getText().toString());
								get_contact.put("workPhone",workPhoneET.getText().toString());
								get_contact.put("contact_type",contactType);
								get_contact.put("contact_type_position",contactTypePosition);
								get_contact.saveInBackground();
						    }
						  }
						});
					//Only change values if a change is detected
			    
					
					//Go to View Info Page
					Intent i = new Intent();
					i.putExtra("userid",ID);
	                i.setClass(EnterContactActivity.this, ViewInfoActivity.class);
	                startActivity(i);
					 
					
					//finish();
				}
				message("saved");
            }
		});
		
		((Button)findViewById(R.id.additionalContactButton)).setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
				
				if(flow == 1 || flow == 2){ //Create new contact
					contact = new ParseObject("Contact");
					contact.put("user", ID);
					contact.put("name",contactNameET.getText().toString());
					contact.put("relation",contactRelationET.getText().toString());
					contact.put("homePhone",homePhoneET.getText().toString());
					contact.put("cellPhone",cellPhoneET.getText().toString());
					contact.put("workPhone",workPhoneET.getText().toString());
					contact.put("contact_type",contactType);
					contact.put("contact_type_position",contactTypePosition);
					contact.saveInBackground();
				}
				
				else if(flow == 3){ //Editing existing contact
					
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
					
					query.getInBackground(contactID, new GetCallback<ParseObject>() {
						  public void done(ParseObject get_contact, ParseException e) {
						    if (e == null) {
						    		get_contact.put("name",contactNameET.getText().toString());
									get_contact.put("relation",contactRelationET.getText().toString());
									get_contact.put("homePhone",homePhoneET.getText().toString());
									get_contact.put("cellPhone",cellPhoneET.getText().toString());
									get_contact.put("workPhone",workPhoneET.getText().toString());
								get_contact.put("contact_type",contactType);
								get_contact.put("contact_type_position",contactTypePosition);
								get_contact.saveInBackground();
						    }
						  }
						});
				}
				message("saved");
				Intent i = new Intent();
				i.putExtra("userid",ID);
				i.putExtra("contactid", 0);
				if(flow == 3){
					flow = 2;
				}
				i.putExtra("pageFlow", flow);
				//Go to a new Enter Contact Page
                i.setClass(EnterContactActivity.this, EnterContactActivity.class);
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
		getMenuInflater().inflate(R.menu.contact, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		contactType = (String) parent.getItemAtPosition(pos);
		contactTypePosition = pos;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
