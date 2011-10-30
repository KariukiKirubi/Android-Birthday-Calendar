package com.thoughtworks.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.thoughtworks.android.helper.View;
import com.thoughtworks.android.listener.AuthorizationDialogListener;
import com.thoughtworks.android.listener.FriendsRequestListener;
import com.thoughtworks.android.model.Friends;

public class FacebookBirthdayCalendar extends Activity implements FacebookListener {
    private View view;
    private Facebook facebook;
    private ProgressDialog progressDialog;
    private static final String APP_ID = "216175911783054";

    public FacebookBirthdayCalendar() {
        view = new View();
        facebook = new Facebook(APP_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        showStatus("Authorizing...");
        facebook.authorize(this, new String[]{"friends_birthday"}, new AuthorizationDialogListener(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    public void notifyAuthorizationSuccess() {
        showStatusOnUiThread("Fetching friends...");
        fetchFriends();
    }

    public void notifyFriendsRecieved(Friends friends) {
        showStatusOnUiThread("Adding friends to calendar...");
        new Calendar(this).addBirthdays(friends);
        showBirthday(friends);
    }

    private void fetchFriends() {
        AsyncFacebookRunner asyncFacebookRunner = new AsyncFacebookRunner(facebook);
        Bundle friendFields = new Bundle();
        friendFields.putString("fields", "birthday,name");
        asyncFacebookRunner.request("me/friends", friendFields, new FriendsRequestListener(this));
    }

    private void showBirthday(final Friends friends) {
        runOnUiThread(new Runnable() {
            public void run() {
                hideStatus();
                Context applicationContext = getApplicationContext();
                ScrollView scrollView = new ScrollView(applicationContext);
                TextView textView = new TextView(applicationContext);
                String status = "The following friends have been added to your calendar:\n\n";
                textView.setText(status + view.getFriendsHavingBirthday(friends));
                scrollView.addView(textView);
                setContentView(scrollView);
            }
        });
    }

    private void showStatusOnUiThread(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                hideStatus();
                showStatus(message);
            }
        });
    }

    private void showStatus(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void hideStatus() {
        progressDialog.hide();
    }
}