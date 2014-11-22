package com.example.hci;

import javax.xml.datatype.Duration;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchGroupActivity extends Activity implements OnClickListener{
	
	private String[] listview_demoarray = { "One", "Two", "Three", "Four", "Five" };
	private Spinner spnr_class;
	private Button btn_search_grp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_group);
		spnr_class = (Spinner) findViewById(R.id.spinner_class_selector_search_group);
		btn_search_grp = (Button) findViewById(R.id.button_to_execute_group_search);
		
		btn_search_grp.setOnClickListener(this);
		
		
		// Code to include fragment in activity
		
	}
	
	/*  CODE FOR FRAGMENT TO DISPLAY SEARCH RESULTS */
	
	public static class DisplaySearchGroupResults extends ListFragment  {

		private TextView txtv_f1;
		private Button btn_frag1_test;

		String[] numbers_text = new String[] { "one", "two", "three", "four",
			    "five", "six", "seven", "eight", "nine", "ten", "eleven",
			    "twelve", "thirteen", "fourteen", "fifteen" };
			  String[] numbers_digits = new String[] { "1", "2", "3", "4", "5", "6", "7",
			    "8", "9", "10", "11", "12", "13", "14", "15" };

			  @Override
			  public void onListItemClick(ListView l, View v, int position, long id) {
			  	  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
				  DisplayGroupInfo dgi_fragment = new DisplayGroupInfo();
					FragmentTransaction ft2 = getFragmentManager().beginTransaction();
					ft2.replace(R.id.search_group_container, dgi_fragment).addToBackStack("Search results fragment");
					ft2.commit();
			  }

			  @Override
			  public View onCreateView(LayoutInflater inflater, ViewGroup container,
			    Bundle savedInstanceState) {
			   ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			     inflater.getContext(), android.R.layout.simple_list_item_1,
			     numbers_text);
			   setListAdapter(adapter);
			   return super.onCreateView(inflater, container, savedInstanceState);
			  }
			 }
		

		/*@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// Inflate the layout for this fragment
			View rootViewDisplaySearchResults =  inflater.inflate(R.layout.fragment_display_search_group_results, container, false);

			// Declare UI elements
			txtv_f1 = (TextView) rootViewDisplaySearchResults.findViewById(R.id.txtview_f_d_s_results);
			btn_frag1_test = (Button) rootViewDisplaySearchResults.findViewById(R.id.btn_frag1);

			return rootViewDisplaySearchResults;
		}*/

//	}
	
    /* CODE FOR FRAGMENT TO DISPLAY GROUP INFO */
	
	public static class DisplayGroupInfo extends Fragment {
		
			
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			View rootViewDisplayInfo =  inflater.inflate(R.layout.fragment_display_group_info, container, false);
			
			
			// Declare UI elements
			TextView txtv_f2 = (TextView) rootViewDisplayInfo.findViewById(R.id.txtvw_display_group_info_fragment);
			
			return rootViewDisplayInfo;
	    }

		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id = v.getId();
		if (id == R.id.button_to_execute_group_search) {
			Log.d("Search","clickedddd");
			DisplaySearchGroupResults dsgr_fragment = new DisplaySearchGroupResults();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.search_group_container, dsgr_fragment).commit();
			
				
			
	}
	
	}
	
	
}


