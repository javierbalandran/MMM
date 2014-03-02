package com.example.parsemmm;

import java.util.List;
import java.util.Locale;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ViewInfoActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	static ParseUser user;
	static String ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_info);

		Bundle bundle = this.getIntent().getExtras();
		ID = bundle.getString("userid");
		
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_info, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		int position;
		
		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			
			position = getArguments().getInt(ARG_SECTION_NUMBER);
			
			if(position == 1){
				View rootView = inflater.inflate(R.layout.activity_basic_info, container, false);
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				
				try {
					user = query.get(ID);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TextView firstnameTV = (TextView)rootView.findViewById(R.id.firstNameDisplay);
				firstnameTV.setText(user.getString("first_name"));
				
				TextView middleInitialTV = (TextView)rootView.findViewById(R.id.middleInitialDisplay);
				middleInitialTV.setText(user.getString("middle_initial"));
				
				TextView lastnameTV = (TextView)rootView.findViewById(R.id.lastNameDisplay);
				lastnameTV.setText(user.getString("last_name"));
				
				TextView dayTV = (TextView)rootView.findViewById(R.id.dayDisplay);
				dayTV.setText(user.getString("day"));
				
				TextView monthTV = (TextView)rootView.findViewById(R.id.monthDisplay);
				monthTV.setText(user.getString("month")+ ", ");
				
				TextView yearTV = (TextView)rootView.findViewById(R.id.yearDisplay);
				yearTV.setText(user.getString("year"));
				
				TextView bloodTypeTV = (TextView)rootView.findViewById(R.id.bloodTypeDisplay);
				bloodTypeTV.setText(user.getString("blood_type"));
				
				((Button)rootView.findViewById(R.id.basicEditButton)).setOnClickListener(new OnClickListener() {
					@Override
		            public void onClick(View v) {
						Intent i = new Intent();
						i.putExtra("userid",ID);
						i.putExtra("pageFlow", 2);
		                i.setClass(getActivity(), EnterBasicActivity.class);
		                startActivity(i);
		                
		            }
				});
				
				return rootView;
			}
			/*else if(position == 2){
				final View rootView = inflater.inflate(R.layout.contact_list_view, container, false);
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
				query.whereEqualTo("user", ID);
				query.findInBackground(new FindCallback<ParseObject>(){

					@Override
					public void done(List<ParseObject> contactList, ParseException e) {
						if(e == null){
							ContactListAdapter m_contactListAdapter = new ContactListAdapter(getActivity(), contactList);
							ListView m_vwContactLayout = (ListView)rootView.findViewById(R.id.contactListViewGroup);
							m_vwContactLayout.setAdapter(m_contactListAdapter);
						}
						else{
							
						}
						
					}
					
				});
			}*/
			else{
				View rootView = inflater.inflate(R.layout.activity_contact_view, container,false);
				return rootView;
			}
			//return container;
		}
	}

}
