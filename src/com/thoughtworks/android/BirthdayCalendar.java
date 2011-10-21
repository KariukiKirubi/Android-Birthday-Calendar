package com.thoughtworks.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.facebook.android.Facebook;
import com.thoughtworks.android.listener.AuthorizationDialogListener;
import com.thoughtworks.android.model.Contacts;

public class BirthdayCalendar extends Activity implements BirthdayView {

    private Facebook facebook;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        facebook = new Facebook("216175911783054");
        facebook.authorize(this, new String[]{"friends_birthday"}, new AuthorizationDialogListener(facebook, this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    public void showBirthday(final Contacts contacts) {
        runOnUiThread(new Runnable() {
            public void run() {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(contacts.toString());
                setContentView(textView);
            }
        });
    }
}