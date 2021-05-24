package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Place implements Serializable {
    private final String countryName;
    private final Double latitude;
    private final Double longitude;

    public Place(String countryName, Double latitude, Double longitude) {
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(countryName, place.countryName) &&
                Objects.equals(latitude, place.latitude) &&
                Objects.equals(longitude, place.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName, latitude, longitude);
    }

    @Override
    public String toString() {
        return "Place{" +
                "countryName='" + countryName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
