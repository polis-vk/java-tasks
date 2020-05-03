package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class AnimalOwner implements Serializable {
    private final String name;
    private final String phoneNumber;

    public AnimalOwner(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
