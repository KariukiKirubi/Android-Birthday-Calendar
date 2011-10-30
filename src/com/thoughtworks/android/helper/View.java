package com.thoughtworks.android.helper;

import com.thoughtworks.android.model.Friend;
import com.thoughtworks.android.model.Friends;

public class View {
    public String getFriendsHavingBirthday(Friends friends) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Friend friend : friends.getFriendsHavingBirthday()) {
            stringBuilder.append(friend.getName()).append(": ")
                    .append(friend.getBirthday()).append("\n");
        }
        return stringBuilder.toString();
    }
}
