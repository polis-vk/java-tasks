package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public enum Colour implements Serializable {
    GREY("grey"),
    BLACK("black"),
    WHITE("white"),
    RED("red");
    private String colour;

    Colour() {}

    Colour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return String.valueOf(colour);
    }
}