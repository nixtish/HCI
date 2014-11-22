package com.example.hci;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MyGroups extends Activity implements OnClickListener{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_group);
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
	
	
	

}
