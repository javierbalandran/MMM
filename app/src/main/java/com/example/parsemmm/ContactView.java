package com.example.parsemmm;

import com.parse.ParseObject;

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

public class ContactView extends LinearLayout {
	
	TextView contactNameTV;
	TextView contactTypeTV;
	TextView contactRelationTV;
	TextView contactHomeTV;
	TextView contactCellTV;
	TextView contactWorkTV;
	
	public ContactView(Context context, ParseObject contact){
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_contact_view, this, true);
		this.contactNameTV = (TextView)findViewById(R.id.contactNameDisplay);
		this.contactTypeTV = (TextView)findViewById(R.id.contactTypeDisplay);
		this.contactRelationTV = (TextView)findViewById(R.id.contactRelationDisplay);
		this.contactHomeTV = (TextView)findViewById(R.id.contactHomeDisplay);
		this.contactCellTV = (TextView)findViewById(R.id.contactCellDisplay);
		this.contactWorkTV = (TextView)findViewById(R.id.contactWorkDisplay);
	
		
	}

	public void setContact(ParseObject contact) {
		contactNameTV.setText(contact.getString("name"));
		contactTypeTV.setText(contact.getString("type"));
		contactRelationTV.setText(contact.getString("relation"));
		contactHomeTV.setText(contact.getString("homePhone"));
		contactCellTV.setText(contact.getString("cellPhone"));
		contactWorkTV.setText(contact.getString("workPhone"));
		
	}


}
