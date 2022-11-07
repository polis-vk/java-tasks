package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class PlaceOfResidence implements Serializable {
    private String country;
    private String terrain;

    public PlaceOfResidence(String country, String terrain) {
        this.country = country;
        this.terrain = terrain;
    }

    public PlaceOfResidence() {

    }

    public String getCountry() {
        return country;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    @Override
    public String toString() {
        return "PlaceOfResidence{" +
                "country='" + country + '\'' +
                ", terrain='" + terrain + '\'' +
                '}';
    }
}
