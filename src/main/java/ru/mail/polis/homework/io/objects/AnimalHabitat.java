package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class AnimalHabitat implements Serializable {

    private final String location;

    public AnimalHabitat(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalHabitat)) return false;
        AnimalHabitat that = (AnimalHabitat) o;
        return Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocation());
    }

    @Override
    public String toString() {
        return "AnimalHabitat{" +
                "location='" + location + '\'' +
                '}';
    }
}
