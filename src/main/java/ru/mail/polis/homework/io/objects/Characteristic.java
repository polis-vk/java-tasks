package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public enum Characteristic implements Serializable {
    STUPID("Stupid"),
    SMART("smart"),
    PASSIVE("passive"),
    AGGRESSIVE("aggressive"),
    PEACEFUL("peaceful"),
    HOSTILE("hostile");

    String characteristic;

    Characteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public String toString() {
        return characteristic;
    }
}
