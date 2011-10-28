package com.thoughtworks.android;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.thoughtworks.android.model.Contact;
import com.thoughtworks.android.model.Contacts;

public class Calendar {
    private Activity activity;
    public static final int MILLISECONDS_IN_A_DAY = 24 * 60 * 60 * 1000;
    private static final String CALENDAR_BASE_URL = "content://com.android.calendar";

    public Calendar(Activity activity) {
        this.activity = activity;
    }

    public void addBirthdays(Contacts contacts) {
        String[] projection = new String[]{"_id", "name"};
        Uri calendarsUri = Uri.parse(CALENDAR_BASE_URL + "/calendars");
        Cursor activeCalendarsCursor = getActiveCalendarsCursor(projection, calendarsUri);
        if (activeCalendarsCursor.moveToFirst()) {
            for (Contact contact : contacts.getContactsWithBirthday()) {
                int idColumnIndex = activeCalendarsCursor.getColumnIndex("_id");
                String calendarId = activeCalendarsCursor.getString(idColumnIndex);
                insertToCalendar(createEvent(calendarId, contact));
            }
        }
    }

    private void insertToCalendar(ContentValues event) {
        Uri eventsUri = Uri.parse(CALENDAR_BASE_URL + "/events");
        activity.getContentResolver().insert(eventsUri, event);
    }

    private Cursor getActiveCalendarsCursor(String[] projection, Uri calendarsUri) {
        return activity.managedQuery(calendarsUri, projection, "selected=1", null, null);
    }

    private ContentValues createEvent(String calendarId, Contact contact) {
        long birthdayTime = contact.getBirthdayTime();
        String title = contact.getName() + "'s Birthday";
        ContentValues event = new ContentValues();
        event.put("calendar_id", calendarId);
        event.put("title", title);
        event.put("allDay", 1);
        event.put("hasAlarm", 1);
        event.put("dtstart", birthdayTime);
        event.put("dtend", birthdayTime + MILLISECONDS_IN_A_DAY);
        return event;
    }
}