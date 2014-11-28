package com.example.hci;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;



public class CreateGroupActivity extends Activity implements OnClickListener,OnItemSelectedListener{
	
	private  EditText et_group_name;
	private  Button bt_create_group;
	private  Button bt_add_people;
	public  Spinner classes_spinner;
	private  Spinner locations_spinner;
	private static int previouslyFetchedPosition;
	private ListView membersListVIew;
	public static List<Model> members = new ArrayList<Model>();
	private ArrayList<String> groupMembers = new ArrayList<String>();
	
	MainActivity mainObject = new MainActivity();

	
	private static final String TAG = "MyActivity";
	ParseObject group;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.create_group);

		et_group_name = (EditText) findViewById(R.id.enter_group_name_to_create);
		bt_create_group = (Button)findViewById(R.id.button_to_create_group);
		bt_add_people = (Button)findViewById(R.id.button_to_add_people);
		classes_spinner = (Spinner)findViewById(R.id.create_group_class_selector);
		locations_spinner = (Spinner)findViewById(R.id.create_group_location_selection);
		membersListVIew = (ListView)findViewById(R.id.create_group_list_view);
		
		previouslyFetchedPosition = -1;
		Log.d(TAG,"class list ::: " + MainActivity.classList);
			
		
		bt_create_group.setOnClickListener(this);
		bt_add_people.setOnClickListener(this);
        classes_spinner.setOnItemSelectedListener(this);
        locations_spinner.setOnItemSelectedListener(this);
		populateClassSpinner();
		populateLocationsSpinner();
		generateListView();
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.button_to_create_group)
		{
			if(!(et_group_name.getText().toString().length() == 0))
			{
			group = new ParseObject("Group");
			group.put("group_name", et_group_name.getText().toString());
			group.put("location",locations_spinner.getSelectedItem().toString());
			group.put("class_name", classes_spinner.getSelectedItem().toString());
			group.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e) {
					// TODO Auto-generated method stub
					if(e == null)
					{
						Toast.makeText(getApplicationContext(), "New Group created",Toast.LENGTH_SHORT ).show();
						finish();
					}
					else
					{
						Log.d(TAG,":::: Error Occured::::");
					}
				}
	        });
	
		}
			else{
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {

				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Enter Group Name!").setNegativeButton("Cancel", dialogClickListener).show();
			}
		}else if (v.getId() == R.id.button_to_add_people) {
			
			if(previouslyFetchedPosition != classes_spinner.getSelectedItemPosition())
			{
				fetchMembers();
			}		
			Intent i = new Intent(this,AddGroupMembersActivity.class);
			
			startActivityForResult(i,1);


		}
		
	}
	
	  private void fetchMembers()
	  {
		  ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		  query.whereEqualTo("class_list",classes_spinner.getSelectedItem().toString());
//		  query.whereEqualTo("class_list", "Algos")
		  query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> categories, ParseException e) {
					// TODO Auto-generated method stub
					 if (e == null) {
						 if(!members.isEmpty())
					    	 members.clear();
						 for (int i = 0; i < categories.size(); i++) {
						       String c = categories.get(i).getString("name");

						       if (!c.equals("Sakthi")) {
						    	 members.add(get(c));
						    	 
//						    	 adapter.notifyDataSetChanged();
							}						        
				            
				        }
						
						 Log.d("main", "Retrieved " + members.get(0).getName());
						 Log.d("main", "Retrieved " + members.get(0).isSelected());
						 }else {
				            Log.d("main", "Error: " + e.getMessage());
				        }
				}
			});
		  previouslyFetchedPosition = classes_spinner.getSelectedItemPosition();
	  }

	  private Model get(String s) {
		    return new Model(s);
		  }
	
	
	public void populateClassSpinner()
	{
			         
	        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
	                     (this, android.R.layout.simple_spinner_item,MainActivity.classList);
	                      
	        dataAdapter.setDropDownViewResource
	                     (android.R.layout.simple_spinner_dropdown_item);
	                      
	        classes_spinner.setAdapter(dataAdapter);
	        // Spinner item selection Listener  
	}
	
	public void populateLocationsSpinner()
	{
		
		String[] demo = {"Hunt Library","D.H Hill","EB1", "EB2", "EB3"};
	         
	        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
	                     (this, android.R.layout.simple_spinner_item,demo);
	                      
	        dataAdapter.setDropDownViewResource
	                     (android.R.layout.simple_spinner_dropdown_item);
	                      
	        locations_spinner.setAdapter(dataAdapter);
	        // Spinner item selection Listener  
	}
	
	private void generateListView()
	{
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
        (this, android.R.layout.simple_list_item_1,groupMembers);
         
		membersListVIew.setAdapter(dataAdapter);
		
	}
	
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	parent.setSelection(position);
    Log.d(TAG,"spinner clicked");
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	public void onBackPressed() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            //Yes button clicked 
//		        	CreateGroupActivity.super.onBackPressed();
		        	finish();
		            break;
		        }
		    }
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Warning!").setMessage("Discard all changes?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            Log.d("result",data.getStringArrayListExtra("result").toString());
	            if(!groupMembers.isEmpty())
	            	groupMembers.clear();
	            for (int i = 0; i < members.size(); i++) {
	    			if(CreateGroupActivity.members.get(i).isSelected())
	    			{
	    				groupMembers.add(members.get(i).getName());
	    			}	    			
	    		}
	            membersListVIew.invalidateViews();
	        
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}
	
}
