package ru.mail.polis.homework.io.objects;

public enum Colour {
    WHITE(0, 0, 0),
    BLACK(255, 255, 255),
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255);

    private final int r;
    private final int g;
    private final int b;

    Colour(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
