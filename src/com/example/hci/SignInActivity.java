package com.example.hci;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Activity;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.*;
import java.lang.*;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInActivity extends Activity implements android.view.View.OnClickListener
{
	private static final String TAG = "SignInActivity";
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    public static final String EXTRA_ACCOUNTNAME = "extra_accountname";

    private TextView mOut;


    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;

    private String mEmail;

    private Type requestType;

    public static String TYPE_KEY = "type_key";
    public static enum Type {FOREGROUND, BACKGROUND, BACKGROUND_WITH_SYNC}
    
    
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
    
    
    private ImageView imgv_sign_in;
    private ImageView imgv_stdy;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // enable logging
        Logger.getLogger("com.google.api.client").setLevel(LOGGING_LEVEL);
        // view and menu
        setContentView(R.layout.sign_in);
        getActionBar().hide();
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

     //   mOut = (TextView) findViewById(R.id.message);
        imgv_sign_in = (ImageView)findViewById(R.id.imgv_sign_in);
        //imgv_stdy = (ImageView)findViewById(R.id.imgv_sign_in_stdy);
        
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        //Bundle extras = getIntent().getExtras();
        //requestType = Type.valueOf(extras.getString(TYPE_KEY));
       // setTitle(getTitle() + " - " + requestType.name());
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

                     if (calName != null && calName.contains("Porking")) {
                    result = Integer.parseInt(calID);
                    }

                } while (cursor.moveToNext());
                cursor.close();
            }
            System.out.println(result);
            /*
            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, result);
            Intent intent = new Intent(Intent.ACTION_VIEW)
               .setData(uri);
            startActivity(intent);
            */
           
            /*
        if (extras.containsKey(EXTRA_ACCOUNTNAME)) {
            mEmail = extras.getString(EXTRA_ACCOUNTNAME);
            getTask(SignInActivity.this, mEmail, SCOPE).execute();
            
        } */
    }

    public void onClick(View view) {
    	  if (view.getId() == R.id.sign_in_button) {
    		  
    		  credential =
    	                GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));
    	            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
    	            
    	            if (credential.getSelectedAccountName() == null) {
    	                // ask user to choose account
    	                chooseAccount();
    	            }
    	            credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
    	            // Calendar client
    	            client = new com.google.api.services.calendar.Calendar.Builder(
    	                transport, jsonFactory, credential).setApplicationName("HCI")
    	                .build();
    		  System.out.println("Sign in works");
    		  Intent i = new Intent(this,MainActivity.class);
    		  startActivity(i);
    	  }
    	}
    
    
   
    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
          public void run() {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                connectionStatusCode, SignInActivity.this, REQUEST_GOOGLE_PLAY_SERVICES);
            dialog.show();
            mOut.setText(PREF_ACCOUNT_NAME);
          }
        });
      }
    /*
    @Override
    protected void onResume() {
      super.onResume();
      if (checkGooglePlayServicesAvailable()) {
        haveGooglePlayServices();
      }
    }
    */
    
    /** Check that Google Play services APK is installed and up to date. */
    private boolean checkGooglePlayServicesAvailable() {
      final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
      if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
        showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        return false;
      }
      return true;
    }

    private void haveGooglePlayServices() {
      // check if there is already an account selected
      if (credential.getSelectedAccountName() == null) {
        // ask user to choose account
        chooseAccount();
      } else {
        // load calendars
      	System.out.println("Made it before execution");
       AsyncLoadCalendars.run(this);
      	
        System.out.println("Made it after execution");
      }
    }

    private void chooseAccount() {
      startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
        super.onActivityResult(requestCode, resultCode, data);

       /* if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            if (resultCode == RESULT_OK) {
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                getUsername();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You must pick an account", Toast.LENGTH_SHORT).show();
            }
        } else if ((requestCode == REQUEST_CODE_RECOVER_FROM_AUTH_ERROR ||
                requestCode == REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR)
                && resultCode == RESULT_OK) {
            handleAuthorizeResult(resultCode, data);
            return;
        }
        
        */
        switch (requestCode) {
        case REQUEST_GOOGLE_PLAY_SERVICES:
          if (resultCode == Activity.RESULT_OK) {
            haveGooglePlayServices();
          } else {
            checkGooglePlayServicesAvailable();
          }
          break;
        case REQUEST_AUTHORIZATION:
          if (resultCode == Activity.RESULT_OK) {
        	 System.out.println("In request_authorization"); 
            AsyncLoadCalendars.run(this);
        	  
          } else {
            chooseAccount();
          }
          break;
        case REQUEST_ACCOUNT_PICKER:
          if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
            String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
            if (accountName != null) {
            	System.out.println(accountName);
              credential.setSelectedAccountName(accountName);
              SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
              SharedPreferences.Editor editor = settings.edit();
              editor.putString(PREF_ACCOUNT_NAME, accountName);
              editor.commit();
         	 System.out.println("In request_account_picker"); 
              AsyncLoadCalendars.run(this);
              
                            
            }
          }
          break;
        }
        
    }
	
	
	
	private void handleAuthorizeResult(int resultCode, Intent data) {
        if (data == null) {
            show("Unknown error, click the button again");
            return;
        }
        if (resultCode == RESULT_OK) {
            Log.i(TAG, "Retrying");
            getTask(this, mEmail, SCOPE).execute();
            return;
        }
        if (resultCode == RESULT_CANCELED) {
            show("User rejected authorization.");
            return;
        }
        show("Unknown error, click the button again");
    }
	
	
    /** Called by button in the layout 
    public void greetTheUser(View view) {
        //getUsername();
    	haveGooglePlayServices();
    	
    }
*/

    /** Attempt to get the user name. If the email address isn't known yet,
     * then call pickUserAccount() method so the user can pick an account.
     */
    private void getUsername() {
        if (mEmail == null) {
            pickUserAccount();
        } else {
            if (isDeviceOnline()) {
                getTask(SignInActivity.this, mEmail, SCOPE).execute();
            } else {
                Toast.makeText(this, "No network connection available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** Starts an activity in Google Play Services so the user can pick an account */
    private void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    /** Checks whether the device currently has a network connection */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    /**
     * This method is a hook for background threads and async tasks that need to update the UI.
     * It does this by launching a runnable under the UI thread.
     */
    public void show(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOut.setText(message);
            }
        });
    }

    /**
     * This method is a hook for background threads and async tasks that need to provide the
     * user a response UI when an exception occurs.
     */
    public void handleException(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e instanceof GooglePlayServicesAvailabilityException) {
                    // The Google Play services APK is old, disabled, or not present.
                    // Show a dialog created by Google Play services that allows
                    // the user to update the APK
                    int statusCode = ((GooglePlayServicesAvailabilityException)e)
                            .getConnectionStatusCode();
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
                            SignInActivity.this,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                    dialog.show();
                } else if (e instanceof UserRecoverableAuthException) {
                    // Unable to authenticate, such as when the user has not yet granted
                    // the app access to the account, but the user can fix this.
                    // Forward the user to an activity in Google Play services.
                    Intent intent = ((UserRecoverableAuthException)e).getIntent();
                    startActivityForResult(intent,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                }
            }
        });
    }

    /**
     * Note: This approach is for demo purposes only. Clients would normally not get tokens in the
     * background from a Foreground activity.
     */
    private AbstractGetNameTask getTask(
            SignInActivity activity, String email, String scope) {
                        return new GetNameInBackground(activity, email, scope);
        }




	
    
}

