package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Habitat implements Serializable {
    private final String area;

    public Habitat(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Habitat{" +
                "area='" + area + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitat habitat = (Habitat) o;
        return Objects.equals(getArea(), habitat.getArea());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArea());
    }
}
