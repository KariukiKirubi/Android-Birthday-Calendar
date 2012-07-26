package com.thoughtworks.android.listener;

import android.os.Bundle;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.thoughtworks.android.FacebookListener;

public class AuthorizationDialogListener implements Facebook.DialogListener {
    private FacebookListener facebookListener;

    public AuthorizationDialogListener(FacebookListener facebookListener) {
        this.facebookListener = facebookListener;
    }

    public void onComplete(Bundle values) {
        facebookListener.notifyAuthorizationSuccess();
    }

    public void onFacebookError(FacebookError e) {
        facebookListener.notifyFailure("Facebook Authorization failure");
    }

    public void onError(DialogError e) {
        facebookListener.notifyFailure("Failed to connect to server");
    }

    public void onCancel() {
        facebookListener.moveOutOfApplication();
    }
}
