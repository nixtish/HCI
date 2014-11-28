package com.example.hci;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity implements OnClickListener
{
	static public List<String> classList = new ArrayList<String>();
	private Button btn_search_group;
	private Button btn_create_group;
	private Button btn_user_schedule;
	private Button btn_mygroups;
	private ImageView imgv_bckgrnd_main;
//	private ImageView imgview_main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		  Parse.initialize(this, "B5BeLGhtq3GCbKJBWgigtklJSmZW2N2UbtFa6Lhi", "psVT9n6D8BO24FQkaj9ZHXwE35Oy17rqALD5iT3s");
		  if(classList.isEmpty())
		  {
		  ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		  query.whereEqualTo("unity_id", "kdhanas");
		  query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> classlist, ParseException e) {
					// TODO Auto-generated method stub
					 if (e == null) {
						 	classList = classlist.get(0).getList("class_list");
				            Log.d("main", "Retrieved " + classList);
				        } else {
				            Log.d("main", "Error: " + e.getMessage());
				        }
				}
			});
		  }
		// Instantiate all UI elements
		
		
 
		 
		 // Change action bar color
		 ActionBar actionBar = getActionBar();
		 actionBar.setBackgroundDrawable(new ColorDrawable(0xFF660200));
		/* ActionBar actionBar = getSupportActionBar();
		 actionBar.hide();*/
		
		// Instantiate all UI elements
		 	 
	//	imgv_bckgrnd_main = (ImageView)findViewById(R.id.imgvw_main_activity);

		btn_search_group = (Button) findViewById(R.id.button_searchGroup);
		btn_create_group = (Button) findViewById(R.id.button_createGroup);
		btn_user_schedule= (Button) findViewById(R.id.button_userSchedule);
		btn_mygroups     = (Button) findViewById(R.id.button_mygroups);
		
		
		btn_create_group.setOnClickListener(this);
		btn_search_group.setOnClickListener(this);
		btn_user_schedule.setOnClickListener(this);
		btn_mygroups.setOnClickListener(this);
	
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
			overridePendingTransition(R.animator.activity_switcher1s, R.animator.activity_switchers1);
		} else if (id == R.id.button_createGroup) {
			Intent i2 = new Intent(this, CreateGroupActivity.class);
			//	i2.putExtra(this, CreateGroupActivity.class);
			startActivity(i2);
			overridePendingTransition(R.animator.activity_switcher1s, R.animator.activity_switchers1);
		} else if (id == R.id.button_userSchedule) {
			Intent i3 = new Intent(this, UserScheduleActivity.class);
			startActivity(i3);
			overridePendingTransition(R.animator.activity_switcher1s, R.animator.activity_switchers1);
		} else if (id == R.id.button_mygroups) {
			Intent i4 = new Intent(this, MyGroupsActivity.class);
			startActivity(i4);
			overridePendingTransition(R.animator.activity_switcher1s, R.animator.activity_switchers1);
		}
		
	}
}
