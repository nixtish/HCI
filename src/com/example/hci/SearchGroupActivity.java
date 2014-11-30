package com.example.hci;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SearchGroupActivity extends Activity implements OnClickListener{
	
	private String[] listview_demoarray = { "One", "Two", "Three", "Four", "Five" };
	private TextView txtv_search_results_label;
	public static Spinner spnr_class;
	private Button btn_search_grp;
	String[] class_text = new String[] { "HCI", "ALDA", "seven", "eight"};
	
	public void populateClassSpinner()

	{

	      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
	                    (this, android.R.layout.simple_spinner_item,class_text);
          dataAdapter.setDropDownViewResource
	                    (android.R.layout.simple_spinner_dropdown_item);
                  spnr_class.setAdapter(dataAdapter);

	       	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_group);
		
		ActionBar actionBar = getActionBar();
		 actionBar.setBackgroundDrawable(new ColorDrawable(0xFF660200));
		spnr_class = (Spinner) findViewById(R.id.spinner_class_selector_search_group);
		btn_search_grp = (Button) findViewById(R.id.button_to_execute_group_search);
		txtv_search_results_label = (TextView)findViewById(R.id.tv_dynamic_search_result_label);
		txtv_search_results_label.setTextColor(0xFF660200);
		//txtv_search_results_label.setVisibility(0);
	    populateClassSpinner();
		btn_search_grp.setOnClickListener(this);
		
				
	}
	
	/*  CODE FOR FRAGMENT TO DISPLAY SEARCH RESULTS */
	
	public static class DisplaySearchGroupResults extends ListFragment  {

		private TextView txtv_f1;
		private Button btn_frag1_test;
		private List<String> members = new ArrayList<String>();

		String[] numbers_text = new String[] { "one", "two", "three", "four",
			    "five", "six", "seven", "eight", "nine", "ten", "eleven",
			    "twelve", "thirteen", "fourteen", "fifteen" };
			  String[] numbers_digits = new String[] { "1", "2", "3", "4", "5", "6", "7",
			    "8", "9", "10", "11", "12", "13", "14", "15" };
			  
			  public ArrayAdapter<String> adapter;

			  @Override
			  public void onListItemClick(ListView l, View v, int position, long id) {
			  	  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
			  	  
			  	  /*DisplayGroupInfo dgi_fragment = new DisplayGroupInfo();
					FragmentTransaction ft2 = getFragmentManager().beginTransaction();
					ft2.replace(R.id.search_group_container, dgi_fragment).addToBackStack("Search results fragment");
					ft2.commit();*/
			  	  
			  	Intent i3 = new Intent(getActivity(), GroupInfoActivity.class);	
			  	i3.putExtra("From_where", 0 );
			  	i3.putExtra("group_name", l.getItemAtPosition(position).toString());			  	
			  	startActivity(i3);
			  }

			  @Override
			  public View onCreateView(LayoutInflater inflater, ViewGroup container,
			    Bundle savedInstanceState) {
			   ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			     inflater.getContext(), android.R.layout.simple_list_item_1, fetchMembers());
			   setListAdapter(adapter);
			   Log.d("Got here" , "Reached");
			   return super.onCreateView(inflater, container, savedInstanceState);
			  
			  }
			  
		  
			  private List<String> fetchMembers()

			  {
				  ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
				  query.whereEqualTo("class_name",SearchGroupActivity.spnr_class.getSelectedItem().toString());
				  //				  query.whereEqualTo("class_list", "HCI");
				  query.findInBackground(new FindCallback<ParseObject>() {
					  @Override
					  public void done(List<ParseObject> categories, ParseException e) {
						  // TODO Auto-generated method stub
						  if (e == null) {
							  if(!members.isEmpty())
								  members.clear();

							  for (int i = 0; i < categories.size(); i++) {

								  String c = categories.get(i).getString("group_name");

								   members.add(c);	
						  }
							  Log.d("Members", "List members" + members.toString());
							//  getListAdapter().notifyDataSetChanged();
							  getListView().invalidateViews();
							 
						      
						  }else {

							  Log.d("main", "Error: " + e.getMessage());

						  }
					  }
				  });
				  return members;
			  }
	}
		
		
	
    /* CODE FOR FRAGMENT TO DISPLAY GROUP INFO */
	
	/*public static class DisplayGroupInfo extends Fragment {		
			
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			View rootViewDisplayInfo =  inflater.inflate(R.layout.fragment_display_group_info, container, false);
			
			// Declare UI elements
			TextView txtv_f2 = (TextView) rootViewDisplayInfo.findViewById(R.id.txtvw_display_group_info_fragment);
			
			return rootViewDisplayInfo;
	    }

	}*/
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id = v.getId();
		if (id == R.id.button_to_execute_group_search) {
			
			DisplaySearchGroupResults dsgr_fragment = new DisplaySearchGroupResults();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.search_group_container, dsgr_fragment).commit();
			txtv_search_results_label.setText("Search Results");
			
	}
	
	}
	
}


