package com.thoughtworks.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.thoughtworks.android.helper.View;
import com.thoughtworks.android.listener.AuthorizationDialogListener;
import com.thoughtworks.android.listener.FriendsRequestListener;
import com.thoughtworks.android.model.Friend;
import com.thoughtworks.android.model.Friends;

import java.util.List;

public class FacebookBirthdayCalendar extends Activity implements FacebookListener {
    private View view;
    private Calendar calendar;
    private Facebook facebook;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private static final String APP_ID = "216175911783054";

    public FacebookBirthdayCalendar() {
        view = new View();
        facebook = new Facebook(APP_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = new Calendar(this);
        List<Friend> friends = calendar.getAddedFriends();
        if (!friends.isEmpty()) {
            showBirthday(view.serializeFriends(friends));
            return;
        }
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this).create();
        facebook.authorize(this, new String[]{"friends_birthday"}, new AuthorizationDialogListener(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    public void notifyAuthorizationSuccess() {
        showStatus("Fetching friends...");
        fetchFriends();
    }

    public void notifyFriendsRecieved(Friends friends) {
        showStatus("Adding friends to calendar...");
        calendar.addBirthdays(friends);
        showBirthday(friends);
    }

    public void notifyFailure(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                alertDialog.setMessage(message);
                alertDialog.setTitle("Failure");
                alertDialog.setCancelable(false);
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveOutOfApplication();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    public void moveOutOfApplication() {
        moveTaskToBack(true);
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
                String friendsHavingBirthday =
                        view.serializeFriends(friends.getFriendsHavingBirthday());
                showBirthday(friendsHavingBirthday);
            }
        });
    }

    private void showBirthday(String friendsHavingBirthday) {
        ScrollView scrollView = new ScrollView(this);
        TextView textView = new TextView(this);
        String status = "The following friends have been added to your calendar:\n\n";
        textView.setText(status + friendsHavingBirthday);
        scrollView.addView(textView);
        setContentView(scrollView);
    }

    private void showStatus(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                hideStatus();
                progressDialog.setMessage(message);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });
    }

    private void hideStatus() {
        progressDialog.hide();
    }
}