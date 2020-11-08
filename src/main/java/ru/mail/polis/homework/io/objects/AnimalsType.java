package ru.mail.polis.homework.io.objects;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public enum AnimalsType implements Serializable {
    Fish("Fish"),
    Bird("Bird"),
    Mammals("Mammals");

    private final String value;

    AnimalsType(String value) {
        this.value = value;
    }

    public static AnimalsType fromValue(String value) {
        for (AnimalsType my : AnimalsType.values()) {
            if (my.value.equals(value)) {
                return my;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }
}
