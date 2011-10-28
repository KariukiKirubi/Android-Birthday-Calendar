package com.thoughtworks.android.model;

public class Contact {
    private String name;
    private Birthday birthday;

    public Contact(String name, Birthday birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public boolean withoutBirthday() {
        return birthday.getDate().equals("");
    }

    public long getBirthdayTime() {
        return birthday.getTimeInMillis();
    }
}
