package com.thoughtworks.android;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.thoughtworks.android.model.Birthday;
import com.thoughtworks.android.model.Friend;
import com.thoughtworks.android.model.Friends;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendar {
    private String baseUri;
    private Activity activity;
    private static final String EVENT_TITLE_SUFFIX = "'s Birthday";
    public static final int MILLISECONDS_IN_A_DAY = 24 * 60 * 60 * 1000;
    private static final String BASE_URI_PRE_FROYO = "content://calendar";
    private static final String BASE_URI_POST_FROYO = "content://com.android.calendar";

    public Calendar(Activity activity) {
        this.activity = activity;
        this.baseUri = getBaseUri();
    }

    public void addBirthdays(Friends friends) {
        String calendarId = getActiveCalendarId();
        for (Friend friend : friends.getFriendsHavingBirthday()) {
            Uri event = insertToCalendar(createEvent(calendarId, friend));
            addReminder(event);
        }

    }

    public List<Friend> getAddedFriends() {
        List<Friend> friends = new ArrayList<Friend>();
        String calendarId = getActiveCalendarId();

        Uri eventsUri = Uri.parse(baseUri + "/events");
        String[] projection = new String[]{"title", "dtstart"};
        String eventSelection = "calendar_id=" + calendarId + " and title like '%Birthday%'";
        Cursor eventsCursor = activity.managedQuery(eventsUri, projection, eventSelection, null, null);
        if (eventsCursor != null && eventsCursor.moveToFirst()) {
            while (!eventsCursor.isLast()) {
                int titleIndex = eventsCursor.getColumnIndex("title");
                int birthdayIndex = eventsCursor.getColumnIndex("dtstart");
                String title = eventsCursor.getString(titleIndex);
                String birthday = new SimpleDateFormat("MM/dd")
                        .format(new Date(eventsCursor.getLong(birthdayIndex)));
                String[] titleSplit = title.split(EVENT_TITLE_SUFFIX);
                Friend friend = new Friend(titleSplit[0], new Birthday(birthday));
                friends.add(friend);
                eventsCursor.moveToNext();
            }
        }
        return friends;
    }

    private String getActiveCalendarId() {
        String[] projection = new String[]{"_id", "name"};
        Uri calendarsUri = Uri.parse(baseUri + "/calendars");
        Cursor activeCalendarsCursor = getActiveCalendarsCursor(projection, calendarsUri);
        String calendarId = "";
        if (activeCalendarsCursor.moveToFirst()) {
            int idColumnIndex = activeCalendarsCursor.getColumnIndex("_id");
            calendarId = activeCalendarsCursor.getString(idColumnIndex);
        }
        return calendarId;
    }

    private void addReminder(Uri event) {
        Uri remindersUri = Uri.parse(baseUri + "/reminders");
        ContentValues reminder = new ContentValues();
        reminder.put("event_id", Long.parseLong(event.getLastPathSegment()));
        reminder.put("method", 1);
        reminder.put("minutes", 1);
        activity.getContentResolver().insert(remindersUri, reminder);
    }

    private Uri insertToCalendar(ContentValues event) {
        Uri eventsUri = Uri.parse(baseUri + "/events");
        return activity.getContentResolver().insert(eventsUri, event);
    }

    private Cursor getActiveCalendarsCursor(String[] projection, Uri calendarsUri) {
        return activity.managedQuery(calendarsUri, projection, "selected=1", null, null);
    }

    private ContentValues createEvent(String calendarId, Friend friend) {
        long birthdayTime = friend.getBirthdayTime();
        String title = friend.getName() + EVENT_TITLE_SUFFIX;
        ContentValues event = new ContentValues();
        event.put("calendar_id", calendarId);
        event.put("title", title);
        event.put("allDay", 1);
        event.put("hasAlarm", 1);
        event.put("dtstart", birthdayTime);
        event.put("dtend", birthdayTime + MILLISECONDS_IN_A_DAY);
        return event;
    }

    private String getBaseUri() {
        Cursor cursor = activity.managedQuery(Uri.parse(BASE_URI_PRE_FROYO + "/calendars"), null, null, null, null);
        if (cursor == null)
            return BASE_URI_POST_FROYO;
        return BASE_URI_PRE_FROYO;
    }
}