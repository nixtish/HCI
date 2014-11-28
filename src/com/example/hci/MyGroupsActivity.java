package com.example.hci;

import javax.xml.datatype.Duration;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MyGroupsActivity extends Activity implements OnClickListener{
	
	private Button btn_leave_group;
	private ListView lv_joined_groups;	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_groups);
		ActionBar actionBar = getActionBar();
		 actionBar.setBackgroundDrawable(new ColorDrawable(0xFF660200));
		
		btn_leave_group = (Button)findViewById(R.id.button_my_groups_leave_group);
		lv_joined_groups = (ListView)findViewById(R.id.my_groups_list_view);
		
		btn_leave_group.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id = v.getId();
		if (id == R.id.button_my_groups_leave_group) {
			Toast.makeText(getApplicationContext(), "Left group", Toast.LENGTH_SHORT ).show();
		}
		finish();
	}
	
	
	

}
