package com.thoughtworks.android.model;

import java.util.ArrayList;
import java.util.List;

public class Friends {
    private List<Friend> friends;

    public Friends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<Friend> getFriendsHavingBirthday() {
        List<Friend> friendsHavingBirthday = new ArrayList<Friend>();
        for (Friend friend : friends) {
            if (friend.withoutBirthday()) continue;
            friendsHavingBirthday.add(friend);
        }
        return friendsHavingBirthday;
    }
}
