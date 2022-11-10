package ru.mail.polis.homework.io.objects;

import java.util.Objects;

public class Habitat {
    private final String place;
    private final int length;
    private final int height;
    private final int width;

    public Habitat(String place, int length, int height, int width) {
        this.place = place;
        this.length = length;
        this.height = height;
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public String getPlace() {
        return place;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, length, height, width);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Habitat habitat = (Habitat) obj;
        return Objects.equals(place, habitat.place) && length == habitat.length &&
                height == habitat.height && width == habitat.width;
    }

    @Override
    public String toString() {
        return "Habitat{" +
                "place='" + place + '\'' +
                ", length=" + length +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
