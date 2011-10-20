package com.thoughtworks.android.model;

import java.util.List;

public class Contacts {
    private List<Contact> contacts;

    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Contact contact : contacts) {
            stringBuilder.append(contact.getName()).append(": ")
                    .append(contact.getBirthday()).append("\n");
        }
        return stringBuilder.toString();
    }
}
