package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Holder implements Serializable {
    private final String name;

    public Holder(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
