package com.thoughtworks.android.listener;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.FacebookError;
import com.thoughtworks.android.FacebookListener;
import com.thoughtworks.android.model.Birthday;
import com.thoughtworks.android.model.Friend;
import com.thoughtworks.android.model.Friends;
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
        List<Friend> friendList = new ArrayList<Friend>();
        try {
            JSONObject json = new JSONObject(response);
            JSONArray friendsJson = json.getJSONArray("data");
            int friendsSize = friendsJson == null ? 0 : friendsJson.length();
            for (int count = 0; count < friendsSize; count++) {
                JSONObject friendJson = friendsJson.getJSONObject(count);
                String birthday = friendJson.has("birthday") ? friendJson.getString("birthday") : "";
                Friend friend = new Friend(friendJson.getString("name"), new Birthday(birthday));
                friendList.add(friend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Friends friends = new Friends(friendList);
        facebookListener.notifyFriendsRecieved(friends);
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