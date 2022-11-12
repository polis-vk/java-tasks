package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class AnimalNursery implements Serializable {
    String address;
    boolean isWork;

    public AnimalNursery(String address, boolean isWork) {
        this.address = address;
        this.isWork = isWork;
    }

    public boolean isWork() {
        return isWork;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AnimalNursery nursery = (AnimalNursery) obj;
        return Objects.equals(address, nursery.address) &&
                isWork == nursery.isWork;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, isWork);
    }

    @Override
    public String toString() {
        return "{\"address\":" + "\"" + address + "\"," +
                "\"isWork\":" + "\"" + isWork + "\"}";
    }
}
