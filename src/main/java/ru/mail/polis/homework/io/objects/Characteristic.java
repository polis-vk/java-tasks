package ru.mail.polis.homework.io.objects;


public enum Characteristic {
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

}