package com.example.hci;



import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.view.View.OnClickListener;

public class UserScheduleActivity extends Activity implements OnClickListener{
	
	
	private static final Level LOGGING_LEVEL = Level.OFF;

    private static final String PREF_ACCOUNT_NAME = "accountName";

    //static final String TAG = "CalendarSampleActivity";

    private static final int CONTEXT_EDIT = 0;

    private static final int CONTEXT_DELETE = 1;

    private static final int CONTEXT_BATCH_ADD = 2;

    static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;

    static final int REQUEST_AUTHORIZATION = 1;

    static final int REQUEST_ACCOUNT_PICKER = 2;

    private final static int ADD_OR_EDIT_CALENDAR_REQUEST = 3;

    final HttpTransport transport = AndroidHttp.newCompatibleTransport();

    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    GoogleAccountCredential credential;
    
    com.google.api.services.calendar.Calendar client;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        Logger.getLogger("com.google.api.client").setLevel(LOGGING_LEVEL);

		setContentView(R.layout.user_schedule);
		
		ActionBar actionBar = getActionBar();
		 actionBar.setBackgroundDrawable(new ColorDrawable(0xFF660200));
		//TestFragment fragment = new TestFragment();
		//FragmentTransaction ft = getFragmentManager().beginTransaction();
		//ft.add(R.id.test_fragment11, fragment).commit();
		
		
		credential =
                GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
            // Calendar client
            client = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential).setApplicationName("HCI")
                .build();
        
            
            Uri eventUri;
            eventUri = Uri.parse("content://com.android.calendar/events");
            long result = 0;
            String projection[] = { "_id", "title" };
            Cursor cursor = getContentResolver().query(eventUri, null, null, null,
                    null);

            if (cursor.moveToFirst()) {

                String calName;
                String calID;

                int nameCol = cursor.getColumnIndex(projection[1]);
                int idCol = cursor.getColumnIndex(projection[0]);
                do {
                    calName = cursor.getString(nameCol);
                    calID = cursor.getString(idCol);

                     if (calName != null && calName.contains("CSC 554 Concepts")) {
                    result = Integer.parseInt(calID);
                    }

                } while (cursor.moveToNext());
                cursor.close();
            }
            System.out.println(result);
            
            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, result);
            Intent intent = new Intent(Intent.ACTION_VIEW)
               .setData(uri);
            startActivity(intent);
            onBackPressed();
	
	}
	 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
	}

}
