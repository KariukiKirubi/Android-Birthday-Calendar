package com.thoughtworks.android;

import com.thoughtworks.android.model.Contacts;

public interface FacebookListener {
    void notifyContactsRecieved(Contacts contacts);
}
