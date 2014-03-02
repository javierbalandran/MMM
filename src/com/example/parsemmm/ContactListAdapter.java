package com.example.parsemmm;


import java.util.List;

import com.parse.ParseObject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class ContactListAdapter extends BaseAdapter {


	private Context m_context;

	private List<String> m_contactIDList;

	
	public ContactListAdapter(Context context, List<String> contactIDList) {
		this.m_context = context;
		this.m_contactIDList = contactIDList;
	}

	//@Override
	public int getCount() {
		return this.m_contactIDList.size();
	}

	//@Override
	public Object getItem(int position) {
		return this.m_contactIDList.get(position);
	}

	//@Override
	public long getItemId(int position) {
		return position;
	}

	//@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactView contactView = null;
		
		if (convertView == null) {
			contactView = new ContactView(m_context);
		}
		else {
			contactView = (ContactView)convertView;
		}
		contactView.setContact(this.m_contactIDList.get(position));
		return contactView;
	}
}
