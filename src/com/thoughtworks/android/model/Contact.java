package com.thoughtworks.android.model;

public class Contact {
    private String name;
    private String birthday;

    public Contact(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }
}
