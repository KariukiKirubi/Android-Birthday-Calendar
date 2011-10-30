package com.thoughtworks.android;

import com.thoughtworks.android.model.Friends;

public interface FacebookListener {
    void notifyAuthorizationSuccess();

    void notifyFriendsRecieved(Friends friends);
}
