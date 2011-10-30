package com.thoughtworks.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.thoughtworks.android.listener.AuthorizationDialogListener;
import com.thoughtworks.android.listener.FriendsRequestListener;
import com.thoughtworks.android.model.Friends;

public class FacebookBirthdayCalendar extends Activity implements FacebookListener {
    private Facebook facebook;
    private Calendar calendar;
    private static final String APP_ID = "216175911783054";

    public FacebookBirthdayCalendar() {
        facebook = new Facebook(APP_ID);
        calendar = new Calendar(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebook.authorize(this, new String[]{"friends_birthday"}, new AuthorizationDialogListener(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    private void showBirthday(final Friends friends) {
        runOnUiThread(new Runnable() {
            public void run() {
                Context applicationContext = getApplicationContext();
                ScrollView scrollView = new ScrollView(applicationContext);
                TextView textView = new TextView(applicationContext);
                textView.setText(friends.toString());
                scrollView.addView(textView);
                setContentView(scrollView);
            }
        });
    }

    @Override
    public void notifyFriendsRecieved(Friends friends) {
        calendar.addBirthdays(friends);
        showBirthday(friends);
    }

    @Override
    public void notifyAuthorizationSuccess() {
        fetchMyFriends();
    }

    private void fetchMyFriends() {
        AsyncFacebookRunner asyncFacebookRunner = new AsyncFacebookRunner(facebook);
        Bundle friendFields = new Bundle();
        friendFields.putString("fields", "birthday,name");
        asyncFacebookRunner.request("me/friends", friendFields, new FriendsRequestListener(this));
    }
}