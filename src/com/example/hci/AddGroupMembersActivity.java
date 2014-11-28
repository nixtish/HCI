package com.example.hci;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;



public class AddGroupMembersActivity extends ListActivity implements OnClickListener  {
	
	public List<Model> members = new ArrayList<Model>();
	ArrayAdapter<Model> adapter;
	private Button bt_add_members;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_fragment);	
		getListView().invalidateViews();
		ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(this,
		        CreateGroupActivity.members);
			setListAdapter(adapter);
		    
			bt_add_members = (Button) findViewById(R.id.button_to_add_members);
//			bt_add_members.setVisibility(View.INVISIBLE);
			bt_add_members.setOnClickListener(this);

	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {  
//		   new CustomToast(getActivity(), numbers_digits[(int) id]);     
		  }  
	
	 

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() == R.id.button_to_add_members)
		{
			ArrayList<String> result = new ArrayList<String>();
			for (int i = 0; i < CreateGroupActivity.members.size(); i++) {
				if(CreateGroupActivity.members.get(i).isSelected())
				{
					result.add(CreateGroupActivity.members.get(i).getName());
				}
			}
			Intent returnIntent = new Intent();
			returnIntent.putStringArrayListExtra("result", result);
			
			setResult(RESULT_OK,returnIntent);
			finish();
		}
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	
	
	 } 
