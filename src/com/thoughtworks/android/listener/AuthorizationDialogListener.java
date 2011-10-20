package com.thoughtworks.android.listener;

import android.os.Bundle;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.thoughtworks.android.BirthdayView;

public class AuthorizationDialogListener implements Facebook.DialogListener {

    private Facebook facebook;
    private BirthdayView birthdayView;

    public AuthorizationDialogListener(Facebook facebook, BirthdayView birthdayView) {
        this.facebook = facebook;
        this.birthdayView = birthdayView;
    }

    public void onComplete(Bundle values) {
        AsyncFacebookRunner asyncFacebookRunner = new AsyncFacebookRunner(facebook);
        Bundle friendFields = new Bundle();
        friendFields.putString("fields", "birthday,name");
        asyncFacebookRunner.request("me/friends", friendFields, new FriendsRequestListener(birthdayView));
    }

    public void onFacebookError(FacebookError e) {
    }

    public void onError(DialogError e) {
    }

    public void onCancel() {
    }
}
