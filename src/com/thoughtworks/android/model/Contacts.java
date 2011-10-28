package com.thoughtworks.android.model;

import java.util.ArrayList;
import java.util.List;

public class Contacts {
    private List<Contact> contacts;

    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContactsWithBirthday() {
        List<Contact> contactsWithBirthday = new ArrayList<Contact>();
        for (Contact contact : contacts) {
            if (contact.withoutBirthday()) continue;
            contactsWithBirthday.add(contact);
        }
        return contactsWithBirthday;
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
