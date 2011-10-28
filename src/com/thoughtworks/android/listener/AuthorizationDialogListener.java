package com.thoughtworks.android.listener;

import android.os.Bundle;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.thoughtworks.android.FacebookListener;

public class AuthorizationDialogListener implements Facebook.DialogListener {
    private Facebook facebook;
    private FacebookListener facebookListener;

    //TODO: Don't pass along the activity. Notify on authentication success and let the activity do the next step
    public AuthorizationDialogListener(Facebook facebook, FacebookListener facebookListener) {
        this.facebook = facebook;
        this.facebookListener = facebookListener;
    }

    public void onComplete(Bundle values) {
        AsyncFacebookRunner asyncFacebookRunner = new AsyncFacebookRunner(facebook);
        Bundle friendFields = new Bundle();
        friendFields.putString("fields", "birthday,name");
        asyncFacebookRunner.request("me/friends", friendFields, new FriendsRequestListener(facebookListener));
    }

    public void onFacebookError(FacebookError e) {
    }

    public void onError(DialogError e) {
    }

    public void onCancel() {
    }
}
