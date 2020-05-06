package ru.mail.polis.homework.io.objects;

// описание питомника с кошечками

import java.io.Serializable;

public class Cattery implements Serializable{
    private final String catteryName;
    private final String address;

    public Cattery(String catteryName, String address) {
        this.catteryName = catteryName;
        this.address = address;
    }

    public String getCatteryName() {
        return catteryName;
    }

    public String getAddress() {
        return address;
    }

    public boolean equals (Cattery cattery) {
        if (this.catteryName.equals(cattery.getCatteryName())
                && this.address.equals(cattery.getAddress())) {
            return true;
        }
        return false;
    }
}
