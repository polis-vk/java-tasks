package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Organization implements Serializable {

    private final String name;
    private final boolean isVolunteer;

    public Organization(String name, boolean isVolunteer) {
        this.name = name;
        this.isVolunteer = isVolunteer;
    }

    public String getName() {
        return name;
    }

    public boolean isVolunteer() {
        return isVolunteer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization organization = (Organization) o;
        return isVolunteer == organization.isVolunteer &&
            Objects.equals(name, organization.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isVolunteer);
    }

    @Override
    public String toString() {
        return "Organization{" +
            "title='" + name + '\'' +
            ", isVolunteer=" + isVolunteer +
            '}';
    }
}
