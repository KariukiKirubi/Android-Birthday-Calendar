package com.thoughtworks.android.listener;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.FacebookError;
import com.thoughtworks.android.FacebookListener;
import com.thoughtworks.android.model.Birthday;
import com.thoughtworks.android.model.Contact;
import com.thoughtworks.android.model.Contacts;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsRequestListener implements AsyncFacebookRunner.RequestListener {
    private FacebookListener facebookListener;

    public FriendsRequestListener(FacebookListener facebookListener) {
        this.facebookListener = facebookListener;
    }

    public void onComplete(String response, Object state) {
        List<Contact> contactList = new ArrayList<Contact>();
        try {
            JSONObject json = new JSONObject(response);
            JSONArray contactsJson = json.getJSONArray("data");
            int contactSize = contactsJson == null ? 0 : contactsJson.length();
            for (int count = 0; count < contactSize; count++) {
                JSONObject contactJson = contactsJson.getJSONObject(count);
                String birthday = contactJson.has("birthday") ? contactJson.getString("birthday") : "";
                Contact contact = new Contact(contactJson.getString("name"), new Birthday(birthday));
                contactList.add(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Contacts contacts = new Contacts(contactList);
        facebookListener.notifyContactsRecieved(contacts);
    }

    public void onIOException(IOException e, Object state) {
    }

    public void onFileNotFoundException(FileNotFoundException e, Object state) {
    }

    public void onMalformedURLException(MalformedURLException e, Object state) {
    }

    public void onFacebookError(FacebookError e, Object state) {
    }
}