package ru.mail.polis.homework.io.objects;

import java.util.Objects;

public class OwnerAnimal {
    private final String name;
    private String country;

    public OwnerAnimal(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OwnerAnimal ownerAnimal = (OwnerAnimal) obj;
        return Objects.equals(name, ownerAnimal.name) &&
                Objects.equals(country, ownerAnimal.country);
    }

    @Override
    public String toString() {
        return "OwnerAnimal{" +
                "name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}
