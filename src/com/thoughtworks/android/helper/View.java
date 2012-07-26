package com.thoughtworks.android.helper;

import com.thoughtworks.android.model.Friend;

import java.util.List;

public class View {
    public String serializeFriends(List<Friend> friends) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Friend friend : friends) {
            stringBuilder.append(friend.getName()).append(": ")
                    .append(friend.getBirthday()).append("\n");
        }
        return stringBuilder.toString();
    }
}
