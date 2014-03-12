package com.example.parsemmm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
		Parse.initialize(this, "aG9y9eYlMDTFaHWBDUcZWdj9IwlOFX16PLmcJUIO", "C6RxUzTiN4tki5t9zn7bAtvB8DPYy6FeUsvFqZrr"); 
		
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
	public void onBackPressed(){
		Intent i = new Intent();
        i.setClass(ViewInfoActivity.this, MainActivity.class);
        startActivity(i);
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
			return 5;
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
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
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
				
				TextView genderTV = (TextView)rootView.findViewById(R.id.genderDisplay);
				genderTV.setText(user.getString("gender"));
				
				TextView dayTV = (TextView)rootView.findViewById(R.id.dayDisplay);
				dayTV.setText(user.getString("day") + ", ");
				
				TextView monthTV = (TextView)rootView.findViewById(R.id.monthDisplay);
				monthTV.setText(user.getString("month")+ " ");
				
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
			else if(position == 2){
				final ArrayList<String> contactIdList = new ArrayList<String>();
				final View rootView = inflater.inflate(R.layout.contact_list_view, container, false);
				
				
				((Button)rootView.findViewById(R.id.newContactButton)).setOnClickListener(new OnClickListener() {
					@Override
		            public void onClick(View v) {
						Intent i = new Intent();
						i.putExtra("userid",ID);
						i.putExtra("contactid", 0);
						i.putExtra("pageFlow", 2);
		                i.setClass(getActivity(), EnterContactActivity.class);
		                startActivity(i);
		                
		            }
				});
				
			//	contactIdList.isEmpty();
				
				//Toast toast2 = Toast.makeText(getActivity(), "empty "+contactIdList.isEmpty(), Toast.LENGTH_SHORT);
				//toast2.show();
				contactIdList.add("Ri9rUDH9S2");
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
				query.whereEqualTo("user", ID);
				query.findInBackground(new FindCallback<ParseObject>(){
					
					@Override
					public void done(List<ParseObject> contactList, ParseException e) {
						if(e == null){
							//Toast toast = Toast.makeText(getActivity(),"size : "+ contactList.size() , Toast.LENGTH_SHORT);
						//	toast.show();
							for(int i = 0; i < contactList.size(); i++){
								contactIdList.add(contactList.get(i).getObjectId());
								//Toast toast = Toast.makeText(getActivity(), "check"+ i , Toast.LENGTH_SHORT);
								//toast.show();
							}
							//contactIdList.notify();
							
						}
						else{
							
						}
					}
				});
				
			
				//Toast toast = Toast.makeText(getActivity(), "check size "+ contactIdList.size() , Toast.LENGTH_SHORT);
				//toast.show();
				
				ContactListAdapter2 m_contactListAdapter = new ContactListAdapter2(getActivity(), inflater, contactIdList);
				ListView m_vwContactLayout = (ListView)rootView.findViewById(R.id.contactListViewGroup);
				m_vwContactLayout.setAdapter(m_contactListAdapter);
				return rootView;
			}
			else if(position == 3){
				final ArrayList<String> allergyIdList = new ArrayList<String>();
				final View rootView = inflater.inflate(R.layout.allergy_list_view, container, false);
				
				((Button)rootView.findViewById(R.id.newAllergyButton)).setOnClickListener(new OnClickListener() {
					@Override
		            public void onClick(View v) {
						Intent i = new Intent();
						i.putExtra("userid",ID);
						i.putExtra("allergyid", 0);
						i.putExtra("pageFlow", 2);
		                i.setClass(getActivity(), EnterAllergyActivity.class);
		                startActivity(i);
		                
		            }
				});
				
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Allergy");
				query.whereEqualTo("user", ID);
				query.findInBackground(new FindCallback<ParseObject>(){
					
					@Override
					public void done(List<ParseObject> allergyList, ParseException e) {
						if(e == null){
						
							for(int i = 0; i < allergyList.size(); i++){
								allergyIdList.add(allergyList.get(i).getObjectId());
								//Toast toast = Toast.makeText(getActivity(),contactList.get(i).getString("objectId") , Toast.LENGTH_SHORT);
								//toast.show();
							}
						}
						else{		
						}
					}
				});
				allergyIdList.add("d1X1jS386c");
				//allergyIdList.add("JbMmiS35Fa");
				AllergyListAdapter m_allergyListAdapter = new AllergyListAdapter(getActivity(), inflater, allergyIdList);
				ListView m_vwContactLayout = (ListView)rootView.findViewById(R.id.allergyListViewGroup);
				m_vwContactLayout.setAdapter(m_allergyListAdapter);
				return rootView;
			}
			else if(position == 4){
				final ArrayList<String> medicationIdList = new ArrayList<String>();
				final View rootView = inflater.inflate(R.layout.medication_list_view, container, false);
				
				((Button)rootView.findViewById(R.id.newMedicationButton)).setOnClickListener(new OnClickListener() {
					@Override
		            public void onClick(View v) {
						Intent i = new Intent();
						i.putExtra("userid",ID);
						i.putExtra("medicationid", 0);
						i.putExtra("pageFlow", 2);
		                i.setClass(getActivity(), EnterMedicationActivity.class);
		                startActivity(i);
		                
		            }
				});
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Medication");
				query.whereEqualTo("user", ID);
				query.findInBackground(new FindCallback<ParseObject>(){
					
					@Override
					public void done(List<ParseObject> medicationList, ParseException e) {
						if(e == null){
						
							for(int i = 0; i < medicationList.size(); i++){
								medicationIdList.add(medicationList.get(i).getObjectId());
								//Toast toast = Toast.makeText(getActivity(),contactList.get(i).getString("objectId") , Toast.LENGTH_SHORT);
								//toast.show();
							}
						}
						else{		
						}
					}
				});
				medicationIdList.add("u8Gcm5LXRt");
				//medicationIdList.add("2LdatJXr1E");
				MedicationListAdapter m_medicationListAdapter = new MedicationListAdapter(getActivity(), inflater, medicationIdList);
				ListView m_vwContactLayout = (ListView)rootView.findViewById(R.id.medicationListViewGroup);
				m_vwContactLayout.setAdapter(m_medicationListAdapter);
				return rootView;
			}
			else if(position == 5){
				final ArrayList<String> historyIdList = new ArrayList<String>();
				final View rootView = inflater.inflate(R.layout.history_list_view, container, false);
				
				((Button)rootView.findViewById(R.id.newHistoryButton)).setOnClickListener(new OnClickListener() {
					@Override
		            public void onClick(View v) {
						Intent i = new Intent();
						i.putExtra("userid",ID);
						i.putExtra("historyid", 0);
						i.putExtra("pageFlow", 2);
		                i.setClass(getActivity(), EnterHistoryActivity.class);
		                startActivity(i);
		                
		            }
				});
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
				query.whereEqualTo("user", ID);
				query.findInBackground(new FindCallback<ParseObject>(){
					
					@Override
					public void done(List<ParseObject> historyList, ParseException e) {
						if(e == null){
						
							for(int i = 0; i < historyList.size(); i++){
								historyIdList.add(historyList.get(i).getObjectId());
								//Toast toast = Toast.makeText(getActivity(),contactList.get(i).getString("objectId") , Toast.LENGTH_SHORT);
								//toast.show();
							}
						}
						else{		
						}
					}
				});
				historyIdList.add("lxALD6qLs6");
				//historyIdList.add("BdDVaxFmvE");
				HistoryListAdapter m_historyListAdapter = new HistoryListAdapter(getActivity(), inflater, historyIdList);
				ListView m_vwContactLayout = (ListView)rootView.findViewById(R.id.historyListViewGroup);
				m_vwContactLayout.setAdapter(m_historyListAdapter);
				return rootView;
			}
			else{
				View rootView = inflater.inflate(R.layout.view_contact, container,false);
				return rootView;
			}
			
			//return container;
		}
	}
	
	
	
	static class ContactListAdapter2 extends BaseAdapter {

		private Context m_context;
		private LayoutInflater m_inflater;
		private ArrayList<String> m_contactIDList;

		public ContactListAdapter2(Context context, LayoutInflater inflater, ArrayList<String> contactIDList) {
			m_context = context;
			m_inflater = inflater;
			m_contactIDList = contactIDList;
		}

		//@Override
		public int getCount() {
			//return 1;
			return m_contactIDList.size();
		}

		//@Override
		public Object getItem(int position) {
			return m_contactIDList.get(position);
		}

		//@Override
		public long getItemId(int position) {
			return position;
		}

		//@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
			
			final View row;
			row = m_inflater.inflate(R.layout.view_contact, parent, false);
			
			final TextView contactNameTV = (TextView)row.findViewById(R.id.contactNameDisplay);;
			final TextView contactTypeTV = (TextView)row.findViewById(R.id.contactTypeDisplay);
			final TextView contactRelationTV = (TextView)row.findViewById(R.id.contactRelationDisplay);
			final TextView contactHomeTV = (TextView)row.findViewById(R.id.contactHomeDisplay);
			final TextView contactCellTV = (TextView)row.findViewById(R.id.contactCellDisplay);
			final TextView contactWorkTV = (TextView)row.findViewById(R.id.contactWorkDisplay);
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
			query.getInBackground(m_contactIDList.get(position), new GetCallback<ParseObject>() {
			//	query.getInBackground("Ri9rUDH9S2", new GetCallback<ParseObject>() {	
			  public void done(final ParseObject contact, ParseException e) {
			    if (e == null) {
			    	contactNameTV.setText(contact.getString("name"));
					contactTypeTV.setText(contact.getString("contact_type"));
					contactRelationTV.setText(contact.getString("relation"));
					contactHomeTV.setText(contact.getString("homePhone"));
					contactCellTV.setText(contact.getString("cellPhone"));
					contactWorkTV.setText(contact.getString("workPhone"));
					
					((Button)row.findViewById(R.id.contactEditButton)).setOnClickListener(new OnClickListener() {
						@Override
			            public void onClick(View v) {
							Intent i = new Intent();
							i.putExtra("userid",ID);
							i.putExtra("contactid", contact.getObjectId());
							i.putExtra("pageFlow", 3);
			                i.setClass(m_context, EnterContactActivity.class);
			                m_context.startActivity(i);
			                
			            }
					});
					
					((Button)row.findViewById(R.id.contactRemoveButton)).setOnClickListener(new OnClickListener() {
						@Override
			            public void onClick(View v) {
							AlertDialog alertDialog = new AlertDialog.Builder(m_context).create();
							alertDialog.setTitle("Remove this Contact");
							alertDialog.setMessage("Are you sure? This will permanently delete this information!");
							
							alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int which) {
								   try {
									contact.delete();
									
									Intent i = new Intent();
									i.putExtra("userid",ID);
					                i.setClass(m_context, ViewInfoActivity.class);
					                m_context.startActivity(i);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							   }
							});
							alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
								   public void onClick(DialogInterface dialog, int which) {
								      // TODO Add your code for the button here.
								   }
								});
							// Set the Icon for the Dialog
							alertDialog.show();
			            }
					});
					
			    }
			    else
			    {
			    	
			    }
			  }
			});
			
			
			
			return row;
		}
	}
	
	static class AllergyListAdapter extends BaseAdapter {

		private Context m_context;
		private LayoutInflater m_inflater;
		private ArrayList<String> m_allergyIDList;

		public AllergyListAdapter(Context context, LayoutInflater inflater, ArrayList<String> allergyIDList) {
			m_context = context;
			m_inflater = inflater;
			m_allergyIDList = allergyIDList;
		}

		//@Override
		public int getCount() {
			//return 1;
			return m_allergyIDList.size();
		}

		//@Override
		public Object getItem(int position) {
			return m_allergyIDList.get(position);
		}

		//@Override
		public long getItemId(int position) {
			return position;
		}

		//@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
			
			final View row;
			row = m_inflater.inflate(R.layout.view_allergy, parent, false);
			
			final TextView allergyNameTV = (TextView)row.findViewById(R.id.allergyNameDisplay);;
			final TextView allergyTypeTV = (TextView)row.findViewById(R.id.allergyTypeDisplay);
			final TextView allergyDetailsTV = (TextView)row.findViewById(R.id.allergyDetailsDisplay);
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Allergy");
			query.getInBackground(m_allergyIDList.get(position), new GetCallback<ParseObject>() {
			//	query.getInBackground("Ri9rUDH9S2", new GetCallback<ParseObject>() {	
			  public void done(final ParseObject allergy, ParseException e) {
			    if (e == null) {
			    	allergyNameTV.setText(allergy.getString("name"));
			    	allergyTypeTV.setText(allergy.getString("type"));
			    	allergyDetailsTV.setText(allergy.getString("details"));
			    
					
					((Button)row.findViewById(R.id.allergyEditButton)).setOnClickListener(new OnClickListener() {
						@Override
			            public void onClick(View v) {
							Intent i = new Intent();
							i.putExtra("userid",ID);
							i.putExtra("allergyid", allergy.getObjectId());
							i.putExtra("pageFlow", 3);
			                i.setClass(m_context, EnterAllergyActivity.class);
			                m_context.startActivity(i); 
			            }
					});	
					

					((Button)row.findViewById(R.id.allergyRemoveButton)).setOnClickListener(new OnClickListener() {
						@Override
			            public void onClick(View v) {
							AlertDialog alertDialog = new AlertDialog.Builder(m_context).create();
							alertDialog.setTitle("Remove this Allergy");
							alertDialog.setMessage("Are you sure? This will permanently delete this information!");
							
							alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int which) {
								   try {
									allergy.delete();
									
									Intent i = new Intent();
									i.putExtra("userid",ID);
					                i.setClass(m_context, ViewInfoActivity.class);
					                m_context.startActivity(i);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							   }
							});
							alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
								   public void onClick(DialogInterface dialog, int which) {
								      // TODO Add your code for the button here.
								   }
								});
							// Set the Icon for the Dialog
							alertDialog.show();
			            }
					});
					
			    }
			    else
			    {  	
			    }
			  }
			});
			return row;
		}
	}
	
	static class MedicationListAdapter extends BaseAdapter {

		private Context m_context;
		private LayoutInflater m_inflater;
		private ArrayList<String> m_medicationIDList;

		public MedicationListAdapter(Context context, LayoutInflater inflater, ArrayList<String> medicationIDList) {
			m_context = context;
			m_inflater = inflater;
			m_medicationIDList = medicationIDList;
		}

		//@Override
		public int getCount() {
			//return 1;
			return m_medicationIDList.size();
		}

		//@Override
		public Object getItem(int position) {
			return m_medicationIDList.get(position);
		}

		//@Override
		public long getItemId(int position) {
			return position;
		}

		//@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
			
			final View row;
			row = m_inflater.inflate(R.layout.view_medication, parent, false);
			
			final TextView medicationNameTV = (TextView)row.findViewById(R.id.medicationNameDisplay);;
			final TextView medicationSourceTV = (TextView)row.findViewById(R.id.medicationSourceDisplay);
			final TextView medicationAmountTV = (TextView)row.findViewById(R.id.medicationAmountDisplay);
			final TextView medicationIntervalTV = (TextView)row.findViewById(R.id.medicationIntervalDisplay);
			final TextView medicationDescriptionTV = (TextView)row.findViewById(R.id.medicationDescriptionDisplay);
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Medication");
			query.getInBackground(m_medicationIDList.get(position), new GetCallback<ParseObject>() {
			//	query.getInBackground("Ri9rUDH9S2", new GetCallback<ParseObject>() {	
			  public void done(final ParseObject medication, ParseException e) {
			    if (e == null) {
			    	medicationNameTV.setText(medication.getString("name"));
			    	medicationSourceTV.setText(medication.getString("source"));
			    	medicationAmountTV.setText(medication.getString("amount")+"/");
			    	medicationIntervalTV.setText(medication.getString("interval"));
			    	medicationDescriptionTV.setText(medication.getString("description"));

					
					((Button)row.findViewById(R.id.medicationEditButton)).setOnClickListener(new OnClickListener() {
						@Override
			            public void onClick(View v) {
							Intent i = new Intent();
							i.putExtra("userid",ID);
							i.putExtra("medicationid", medication.getObjectId());
							i.putExtra("pageFlow", 3);
			                i.setClass(m_context, EnterMedicationActivity.class);
			                m_context.startActivity(i); 
			            }
					});	
					
					((Button)row.findViewById(R.id.medicationRemoveButton)).setOnClickListener(new OnClickListener() {
						@Override
			            public void onClick(View v) {
							AlertDialog alertDialog = new AlertDialog.Builder(m_context).create();
							alertDialog.setTitle("Remove this Medication");
							alertDialog.setMessage("Are you sure? This will permanently delete this information!");
							
							alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int which) {
								   try {
									medication.delete();
									
									Intent i = new Intent();
									i.putExtra("userid",ID);
					                i.setClass(m_context, ViewInfoActivity.class);
					                m_context.startActivity(i);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							   }
							});
							alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
								   public void onClick(DialogInterface dialog, int which) {
								      // TODO Add your code for the button here.
								   }
								});
							// Set the Icon for the Dialog
							alertDialog.show();
			            }
					});
			    }
			    else
			    {  	
			    }
			  }
			});
			return row;
		}
		
		
	}
	
	static class HistoryListAdapter extends BaseAdapter {

		private Context m_context;
		private LayoutInflater m_inflater;
		private ArrayList<String> m_historyIDList;

		public HistoryListAdapter(Context context, LayoutInflater inflater, ArrayList<String> historyIDList) {
			m_context = context;
			m_inflater = inflater;
			m_historyIDList = historyIDList;
		}

		//@Override
		public int getCount() {
			//return 1;
			return m_historyIDList.size();
		}

		//@Override
		public Object getItem(int position) {
			return m_historyIDList.get(position);
		}

		//@Override
		public long getItemId(int position) {
			return position;
		}

		//@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
			
			final View row;
			row = m_inflater.inflate(R.layout.view_history, parent, false);
			
			final TextView historyNameTV = (TextView)row.findViewById(R.id.historyNameDisplay);;
			final TextView historyDayTV = (TextView)row.findViewById(R.id.historyDayDisplay);
			final TextView historyMonthTV = (TextView)row.findViewById(R.id.historyMonthDisplay);
			final TextView historyYearTV = (TextView)row.findViewById(R.id.historyYearDisplay);
			final TextView historyDescriptionTV = (TextView)row.findViewById(R.id.historyDescriptionDisplay);
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
			query.getInBackground(m_historyIDList.get(position), new GetCallback<ParseObject>() {
			//	query.getInBackground("Ri9rUDH9S2", new GetCallback<ParseObject>() {	
			  public void done(final ParseObject history, ParseException e) {
			    if (e == null) {
			    	historyNameTV.setText(history.getString("name"));
			    	historyDayTV.setText(history.getString("day")+ " ");
			    	historyMonthTV.setText(history.getString("month")+ " ");
			    	historyYearTV.setText(history.getString("year"));
			    	historyDescriptionTV.setText(history.getString("description"));

					
					((Button)row.findViewById(R.id.historyEditButton)).setOnClickListener(new OnClickListener() {
						@Override
			            public void onClick(View v) {
							Intent i = new Intent();
							i.putExtra("userid",ID);
							i.putExtra("historyid", history.getObjectId());
							i.putExtra("pageFlow", 3);
			                i.setClass(m_context, EnterHistoryActivity.class);
			                m_context.startActivity(i); 
			            }
					});	
					
					((Button)row.findViewById(R.id.historyRemoveButton)).setOnClickListener(new OnClickListener() {
						@Override
			            public void onClick(View v) {
							AlertDialog alertDialog = new AlertDialog.Builder(m_context).create();
							alertDialog.setTitle("Remove this Medical History Event");
							alertDialog.setMessage("Are you sure? This will permanently delete this information!");
							
							alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int which) {
								   try {
									history.delete();
									
									Intent i = new Intent();
									i.putExtra("userid",ID);
					                i.setClass(m_context, ViewInfoActivity.class);
					                m_context.startActivity(i);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							   }
							});
							alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
								   public void onClick(DialogInterface dialog, int which) {
								      // TODO Add your code for the button here.
								   }
								});
							// Set the Icon for the Dialog
							alertDialog.show();
			            }
					});
			    }
			    else
			    {  	
			    }
			  }
			});
			return row;
		}
	}
	 void message(String text){
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.show();
	}
	 
}


