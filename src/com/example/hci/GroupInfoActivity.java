package com.example.hci;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.internal.lv;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GroupInfoActivity extends Activity implements OnClickListener,OnItemClickListener {
	
	
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
	private TextView txtv_comments_label;
	private TextView txtv_comments_actual;
	
	private ParseObject answer;
	private String test;
	private Boolean button_manip;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_info);
		
		
		ActionBar actionBar = getActionBar();
	    actionBar.setBackgroundDrawable(new ColorDrawable(0xFF660200));
	    
	    Integer intColor = 0xFF660200;
	    String hexColor = "#" + Integer.toHexString(intColor).substring(2);
	    
	   
		 
		 
		txtv_group_name_label = (TextView)findViewById(R.id.group_info_group_name_label);
		txtv_group_name_actual = (TextView)findViewById(R.id.group_info_group_name_actual);
		txtv_class_name_label = (TextView)findViewById(R.id.group_info_class_name_label);
		txtv_class_name_actual = (TextView)findViewById(R.id.group_info_class_name_actual);
		txtv_location_label = (TextView)findViewById(R.id.group_info_location_label);
		txtv_location_actual = (TextView)findViewById(R.id.group_info_location_actual);
		txtv_time_label=(TextView)findViewById(R.id.group_info_time_label);
		txtv_time_actual = (TextView)findViewById(R.id.group_info_time_actual);
		txtv_comments_label=(TextView)findViewById(R.id.group_info_comments_label);
		txtv_comments_actual = (TextView)findViewById(R.id.group_info_comments_actual);
		txtv_members_label = (TextView)findViewById(R.id.group_info_lv_label);
		lstvw_participants = (ListView)findViewById(R.id.group_info_lv_participants);
		btn_join_leave_group = (Button)findViewById(R.id.button_join_and_leave_group);
		
		txtv_group_name_label.setTextColor(0xFF660200);
		txtv_class_name_label.setTextColor(0xFF660200);
		txtv_class_name_label.setTextColor(0xFF660200);
		txtv_location_label.setTextColor(0xFF660200);
		txtv_members_label.setTextColor(0xFF660200);
		txtv_time_label.setTextColor(0xFF660200);
		txtv_comments_label.setTextColor(0xFF660200);
		
		//txtv_group_name_actual.setTextColor(0xFF660200);
		
		btn_join_leave_group.setOnClickListener(this);
		
		
		Intent i4 = getIntent();
		test = i4.getExtras().getString("group_name");
		Log.d("Group_names",test);
		
		button_manip = i4.getExtras().getBoolean("From_where");
		Log.d("bool",button_manip.toString());
	//	Log.d("bools",button_manip.toString());
		
		if (!button_manip)
		{
			btn_join_leave_group.setText("Join Group");
		}
		else
			btn_join_leave_group.setVisibility(View.INVISIBLE);
		
		try {
			getGroupInfoParse();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	public void getGroupInfoParse() throws ParseException{

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
		query.whereEqualTo("group_name", test);
		 query.findInBackground(new FindCallback<ParseObject>() {
			  @Override
			  public void done(List<ParseObject> object, ParseException e) {
				
				// TODO Auto-generated method stub
				
				if (e == null){
					Log.d("Class",object.get(0).getString("class_name"));
					Log.d("Location",object.get(0).getString("location"));
					
					answer = object.get(0);
					txtv_class_name_actual.setText(object.get(0).getString("class_name").toString());
					txtv_location_actual.setText(object.get(0).getString("location").toString());
					txtv_time_actual.setText(object.get(0).getString("time_day").toString());
					txtv_group_name_actual.setText(test);
					txtv_comments_actual.setText(object.get(0).getString("comments").toString());
					List<String> mem_list = new ArrayList<String>();
					mem_list = object.get(0).getList("members");
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroupInfoActivity.this,android.R.layout.simple_list_item_1
							,mem_list);
					lstvw_participants.setAdapter(adapter);
					lstvw_participants.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							
							Log.d("clicked","cliked");
//							Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" ) );
//							intent.putExtra( "sms_body", "Hey how you doing???" ); 
//							startActivity( intent );
							SmsManager sms = SmsManager.getDefault();
						       sms.sendTextMessage("+17164725029", null, "Heyyyyy!!", null, null);
							
						}
					});
					
					
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
		answer.addUnique("members", "John Cleese");
		answer.saveInBackground();
		JSONObject obj;
		try {
			Log.d("ascsac","button clicked");
			obj =new JSONObject();
			obj.put("alert","John Cleese Joined your group!");
			obj.put("action","com.example.hci.AcceptGroup");
			obj.put("group_name", test.toString());
			obj.put("customdata","John Cleese joined "+ test + "!");
			
			ParsePush push = new ParsePush();
			ParseQuery query = ParseInstallation.getQuery();
			
			 
			// Notification for Android users
			query.whereEqualTo("channels","bye");
			push.setQuery(query);
			push.setData(obj);
			push.sendInBackground(); 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Toast.makeText(getApplicationContext(), "Joined group: "+ test, Toast.LENGTH_SHORT ).show();

		finish();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.d("clicked","clicked");
	}
	
	
}
