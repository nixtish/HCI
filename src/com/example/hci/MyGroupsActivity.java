package com.example.hci;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.datatype.Duration;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MyGroupsActivity extends ListActivity implements OnClickListener,OnItemClickListener{
	
	private Button btn_leave_group;
	private ListView lv_joined_groups;	
	
	private ArrayList<Model> groups = new ArrayList<Model>();
	private List<ParseObject> groups_parselist;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_groups);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(0xFF660200));
		
		btn_leave_group = (Button)findViewById(R.id.button_my_groups_leave_group);
//		lv_joined_groups = (ListView)findViewById(R.id.my_groups_list_view);
		btn_leave_group.setOnClickListener(this);
		fetchMembers();
		ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(this,
		        groups);
			setListAdapter(adapter);		
			getListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Log.d("asadas","sadasdasdasdas");
					Intent i3 = new Intent(getBaseContext(), GroupInfoActivity.class);	
				  	i3.putExtra("From_where", true );
				  	i3.putExtra("group_name", groups.get(position).getName());			  	
				  	startActivity(i3);
					
				}
			});

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Log.d("sadsad",groups.get(position).getName());
		Intent i3 = new Intent(this, GroupInfoActivity.class);	
	  	i3.putExtra("From_where", true);
	  	i3.putExtra("group_name", l.getItemAtPosition(position).toString());			  	
	  	startActivity(i3);
	}
	
	
	private void fetchMembers()
	  {
		  ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
		  query.whereEqualTo("members","John Cleese");
//		  query.whereEqualTo("class_list", "Algos")
		  query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> categories, ParseException e) {
					// TODO Auto-generated method stub
					groups_parselist = categories;
					 if (e == null) {
						 if(!categories.isEmpty())
						 {		 if(!groups.isEmpty())
					    	 groups.clear();
						 for (int i = 0; i < categories.size(); i++) {
						       String c = categories.get(i).getString("group_name");
						    	 groups.add(get(c));
						    	 
//						    	 adapter.notifyDataSetChanged();
										        
				            
				        }
						getListView().invalidateViews();
						 Log.d("main", "Retrieved " + groups.get(0).getName());
						 Log.d("main", "Retrieved " + groups.get(0).isSelected());
						 }}else {
				            Log.d("main", "Error: " + e.getMessage());
				        }
				}
					 
			});
	  }
		  

	  private Model get(String s) {
		    return new Model(s);
		  }

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id = v.getId();
		if (id == R.id.button_my_groups_leave_group) {
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE:
			            //Yes button clicked 
			        	for (int i = 0; i < groups.size(); i++) {
			    			if(groups.get(i).isSelected())
			    			{
			    				groups_parselist.get(i).removeAll("members", Arrays.asList("John Cleese"));
			    				groups_parselist.get(i).saveInBackground();
			    			}	    			
			    		}
						Toast.makeText(getApplicationContext(), "Left group(s)", Toast.LENGTH_SHORT ).show();	
						finish();
			            break;
			        
			        }
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Confirm!").setMessage("Press yes to confirm?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();
			
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Left group", Toast.LENGTH_SHORT ).show();
	}
	
	
	

}
