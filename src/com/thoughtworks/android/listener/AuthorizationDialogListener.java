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
    }

    public void onError(DialogError e) {
    }

    public void onCancel() {
    }
}
