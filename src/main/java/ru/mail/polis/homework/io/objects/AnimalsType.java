package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public enum AnimalsType implements Serializable {
    Fish(0),
    Bird(1),
    Mammals(2),
    Length(3);

    private final int value;

    AnimalsType(int value) {
        this.value = value;
    }

    public static AnimalsType fromValue(int value) {
        for (AnimalsType my : AnimalsType.values()) {
            if (my.value == value) {
                return my;
            }
        }

        return null;
    }

    public int getValue() {
        return value;
    }
}
