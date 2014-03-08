package com.example.parsemmm;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/*

NOT USED IN FINAL PRODUCT

*/

public class ContactView extends LinearLayout {
	
	TextView contactNameTV;
	TextView contactTypeTV;
	TextView contactRelationTV;
	TextView contactHomeTV;
	TextView contactCellTV;
	TextView contactWorkTV;
	
	public ContactView(Context context){
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_contact, this, true);
		this.contactNameTV = (TextView)findViewById(R.id.contactNameDisplay);
		this.contactTypeTV = (TextView)findViewById(R.id.contactTypeDisplay);
		this.contactRelationTV = (TextView)findViewById(R.id.contactRelationDisplay);
		this.contactHomeTV = (TextView)findViewById(R.id.contactHomeDisplay);
		this.contactCellTV = (TextView)findViewById(R.id.contactCellDisplay);
		this.contactWorkTV = (TextView)findViewById(R.id.contactWorkDisplay);
	
		
	}

	public void setContact(String contactid) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
		query.getInBackground(contactid, new GetCallback<ParseObject>() {
		  public void done(ParseObject contact, ParseException e) {
		    if (e == null) {
		    	contactNameTV.setText(contact.getString("name"));
				contactTypeTV.setText(contact.getString("type"));
				contactRelationTV.setText(contact.getString("relation"));
				contactHomeTV.setText(contact.getString("homePhone"));
				contactCellTV.setText(contact.getString("cellPhone"));
				contactWorkTV.setText(contact.getString("workPhone"));
		    }
		    else
		    {
		    	
		    }
		  }
		});
		
	}


}
