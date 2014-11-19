package com.example.hci;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class MainActivity extends Activity implements OnClickListener
{

	private Button btn_search_group;
	private Button btn_create_group;
	private Button btn_user_schedule;
	private Button btn_later;
//	private ImageView imgview_main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Instantiate all UI elements
		
		btn_search_group = (Button) findViewById(R.id.button_searchGroup);
		btn_create_group = (Button) findViewById(R.id.button_createGroup);
		btn_user_schedule= (Button) findViewById(R.id.button_userSchedule);
		btn_later        = (Button) findViewById(R.id.button_later);
		
		
		
		btn_create_group.setOnClickListener(this);
		btn_search_group.setOnClickListener(this);
		btn_user_schedule.setOnClickListener(this);
		btn_later.setOnClickListener(this);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//test comment
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
				
		// Find a better way to implement intents
		
		int id = v.getId();
		if (id == R.id.button_searchGroup) {
			Intent i1 = new Intent(this, SearchGroupActivity.class);
			startActivity(i1);
		} else if (id == R.id.button_createGroup) {
			Intent i2 = new Intent(this, SignInActivity.class);
			i2.putExtra(SignInActivity.TYPE_KEY, SignInActivity.Type.BACKGROUND.name());
			startActivity(i2);
		} else if (id == R.id.button_userSchedule) {
			Intent i3 = new Intent(this, UserScheduleActivity.class);
			startActivity(i3);
		} else if (id == R.id.button_later) {
			Intent i4 = new Intent(this, SignInActivity.class);
			startActivity(i4);
		}
		
	}
}
