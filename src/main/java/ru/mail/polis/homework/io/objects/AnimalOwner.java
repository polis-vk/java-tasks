package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class AnimalOwner implements Serializable {
    private String name;
    private String address;
    private long phoneNumber;

    public AnimalOwner(String name, String address, long phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AnimalOwner)) {
            return false;
        }

        AnimalOwner animalOwner = (AnimalOwner) obj;
        return Objects.equals(this.name, animalOwner.name) && Objects.equals(this.address, animalOwner.address) &&
                this.phoneNumber == animalOwner.phoneNumber;
    }
}
