package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class ResidencePlace implements Serializable {
    private String country;
    private String terrain;

    public ResidencePlace(String country, String terrain) {
        this.country = country;
        this.terrain = terrain;
    }

    public ResidencePlace() {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidencePlace that = (ResidencePlace) o;
        return Objects.equals(country, that.country) && Objects.equals(terrain, that.terrain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, terrain);
    }

    @Override
    public String toString() {
        return "ResidencePlace{" +
                "country='" + country + '\'' +
                ", terrain='" + terrain + '\'' +
                '}';
    }
}
