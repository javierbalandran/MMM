package com.example.parsemmm;


import java.util.List;

import com.parse.ParseObject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class ContactListAdapter extends BaseAdapter {


	private Context m_context;

	private List<ParseObject> m_contactList;

	
	public ContactListAdapter(Context context, List<ParseObject> contactList) {
		this.m_context = context;
		this.m_contactList = contactList;
	}

	//@Override
	public int getCount() {
		return this.m_contactList.size();
	}

	//@Override
	public Object getItem(int position) {
		return this.m_contactList.get(position);
	}

	//@Override
	public long getItemId(int position) {
		return position;
	}

	//@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactView contactView = null;
		
		if (convertView == null) {
			contactView = new ContactView(m_context, this.m_contactList.get(position));
		}
		else {
			contactView = (ContactView)convertView;
		}
		contactView.setContact(this.m_contactList.get(position));
		return contactView;
	}
}
