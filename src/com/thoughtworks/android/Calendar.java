package com.thoughtworks.android;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.thoughtworks.android.model.Contacts;

public class Calendar {
    private Activity activity;

    public Calendar(Activity activity) {
        this.activity = activity;
    }

    public void addBirthdays(Contacts contacts) {
        String[] projection = new String[]{"_id", "name"};
        Uri calendars = Uri.parse("content://com.android.calendar/calendars");
        Cursor managedCursor = activity.managedQuery(calendars, projection, "selected=1", null, null);
        if (managedCursor.moveToFirst()) {
            int idColumn = managedCursor.getColumnIndex("_id");
            java.util.Calendar cal = java.util.Calendar.getInstance();
            do {
                String calId = managedCursor.getString(idColumn);
                ContentValues event = new ContentValues();
                event.put("calendar_id", calId);
                event.put("title", "Dummy Title");
                event.put("description", "Dummy Desc");
                event.put("eventLocation", "Dummy Location");
                event.put("allDay", 0);
                event.put("dtstart", cal.getTimeInMillis());
                event.put("dtend", cal.getTimeInMillis() + 60 * 60 * 1000);
                Uri eventsUri = Uri.parse("content://com.android.calendar/events");
                activity.getContentResolver().insert(eventsUri, event);
            } while (managedCursor.moveToNext());
        }
    }
}