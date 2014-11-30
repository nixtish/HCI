package com.example.hci;

/*
 * Copyright (c) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */


import android.content.Intent;
import android.app.Activity;
import android.net.Uri;

import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.List;

/**
 * Asynchronously load the calendars.
 * 
 * @author Yaniv Inbar
 */
class AsyncLoadCalendars extends CalendarAsyncTask {

  AsyncLoadCalendars(SignInActivity calendarSample) {
    super(calendarSample);
  }

  @Override
  protected void doInBackground() throws IOException {
   // CalendarList feed = client.calendarList().list().setFields(CalendarInfo.FEED_FIELDS).execute();
    //model.reset(feed.getItems());
	  
	  String pageToken = null;
	  do {
	    Events events = client.events().list("hciproject5542@gmail.com").setPageToken(pageToken).execute();
	    List<Event> items = events.getItems();
	    for (Event event : items) {
	    	System.out.println(event.getId());
	    	}
	    pageToken = events.getNextPageToken();
	  } while (pageToken != null);
	  /*
	  Events events_1 = client.events().
	  System.out.println()
	  */
  }

  static void run(SignInActivity calendarSample) {
    new AsyncLoadCalendars(calendarSample).execute();
  }
}
