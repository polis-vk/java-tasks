package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Tail implements Serializable {

    private final boolean isLong;

    public Tail(boolean isLong) {
        this.isLong = isLong;
    }

    public boolean isLong() {
        return isLong;
    }
}
