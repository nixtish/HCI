package com.example.hci;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParsePushBroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class MyCustomReceiver extends ParsePushBroadcastReceiver {
	private static final String TAG = "MyCustomReceiver";
	int count = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			if (intent == null)
			{
				Log.d(TAG, "Receiver intent null");
			}
			else
			{
				String action = intent.getAction();
				Log.d(TAG, "got action from groupstudy " + action );
				if (action.equals("com.example.hci.AcceptGroup"))
				{
					count++;
					String title = null;
					String channel = intent.getExtras().getString("com.parse.Channel");
					JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
					Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
					Iterator itr = json.keys();
					while (itr.hasNext()) {
						String key = (String) itr.next();
					if (key.equals("alert")) {
					 title = json.getString(key);
					}
						if (key.equals("customdata"))
						{
//							Intent pupInt = new Intent(context, MyGroupsActivity.class);
//							pupInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//							context.getApplicationContext().startActivity(pupInt);
							  
					            generateNotification(context, title, json,"random");
						}
						Log.d(TAG, "..." + key + " => " + json.getString(key));
					}
				}
			}

		} catch (JSONException e) {
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
	
	private void generateNotification(Context context, String title, JSONObject json, String contenttext) throws JSONException {
	    Intent intent = new Intent(context, GroupInfoActivity.class);
	    intent.putExtra("From_where", true);	  
	  	intent.putExtra("group_name", json.getString("group_name"));
	    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	    NotificationManager mNotifM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

	    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setTicker(title).setAutoCancel(true).setVibrate(new long[]{200,300,200}).setDefaults(Notification.DEFAULT_ALL).setOngoing(true).setSmallIcon(R.drawable.user_phd_group_111).setContentTitle("Group Study").setContentText(json.getString("customdata"));
	    mBuilder.setContentIntent(contentIntent);
	    mNotifM.notify(1, mBuilder.build());
	    
	}
	
}