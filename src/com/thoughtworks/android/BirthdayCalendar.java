package com.thoughtworks.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
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
                Context applicationContext = getApplicationContext();
                ScrollView scrollView = new ScrollView(applicationContext);
                TextView textView = new TextView(applicationContext);
                textView.setText(contacts.toString());
                scrollView.addView(textView);
                setContentView(scrollView);
            }
        });
    }
}