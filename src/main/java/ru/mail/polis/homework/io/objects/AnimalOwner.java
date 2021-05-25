package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class AnimalOwner implements Serializable {
    private String lastName;
    private String firstName;

    public AnimalOwner(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }



    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof AnimalOwner)) {
            return false;
        }

        AnimalOwner that = (AnimalOwner) obj;

        return lastName.equals(that.lastName) && firstName.equals(that.firstName);
    }
}
