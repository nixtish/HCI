package com.example.hci;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.lv;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class GroupInfoActivity extends Activity implements OnClickListener {
	
	
	private TextView txtv_group_name_actual;
	private TextView txtv_group_name_label;
	private TextView txtv_class_name_actual;
	private TextView txtv_class_name_label;
	private TextView txtv_location_label;
	private TextView txtv_location_actual;
	private TextView txtv_time_label;
	private TextView txtv_time_actual;
	private TextView txtv_members_label;	
	private ListView lstvw_participants;
	private Button btn_join_leave_group;
	
	
	private String test;
	private Boolean button_manip;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_info);
		
		
		ActionBar actionBar = getActionBar();
		 actionBar.setBackgroundDrawable(new ColorDrawable(0xFF660200));
		 
		 
		txtv_group_name_label = (TextView)findViewById(R.id.group_info_group_name_label);
		txtv_group_name_actual = (TextView)findViewById(R.id.group_info_group_name_actual);
		txtv_class_name_label = (TextView)findViewById(R.id.group_info_class_name_label);
		txtv_class_name_actual = (TextView)findViewById(R.id.group_info_class_name_actual);
		txtv_location_label = (TextView)findViewById(R.id.group_info_location_label);
		txtv_location_actual = (TextView)findViewById(R.id.group_info_location_actual);
		txtv_time_label=(TextView)findViewById(R.id.group_info_time_label);
		txtv_time_actual = (TextView)findViewById(R.id.group_info_time_actual);
		txtv_members_label = (TextView)findViewById(R.id.group_info_lv_label);
		lstvw_participants = (ListView)findViewById(R.id.group_info_lv_participants);
		
		btn_join_leave_group = (Button)findViewById(R.id.button_join_and_leave_group);
		
		btn_join_leave_group.setOnClickListener(this);
		
		
		Intent i4 = getIntent();
		test = i4.getExtras().getString("group_name");
		Log.d("Group_names",test);
		button_manip = i4.getExtras().getBoolean("From_where");
	//	Log.d("bools",button_manip.toString());
		
		if (!button_manip)
		{
			btn_join_leave_group.setText("Join Group");
		}
		else
			btn_join_leave_group.setText("Leave Group");
		
		getGroupInfoParse();
				
	}
	
	
	public void getGroupInfoParse(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
		query.whereEqualTo("group_name", test);
		 query.findInBackground(new FindCallback<ParseObject>() {
			  @Override
			  public void done(List<ParseObject> object, ParseException e) {
				
				// TODO Auto-generated method stub
				
				if (e == null){
					Log.d("Class",object.get(0).getString("class_name"));
					Log.d("Location",object.get(0).getString("location"));
					
					
					txtv_class_name_actual.setText(object.get(0).getString("class_name").toString());
					txtv_location_actual.setText(object.get(0).getString("location").toString());
					txtv_group_name_actual.setText(test);
					List<String> mem_list = new ArrayList<String>();
					mem_list = object.get(0).getList("members");
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroupInfoActivity.this,android.R.layout.simple_list_item_1
							,mem_list);
					lstvw_participants.setAdapter(adapter);
					
					
					Log.d("results",object.toString());
				}
				else
					Log.d("DATA", e.getMessage());
			}
		});
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
