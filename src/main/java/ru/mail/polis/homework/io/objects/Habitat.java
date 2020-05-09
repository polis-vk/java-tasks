package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Habitat implements Serializable {
    public final String country;
    public final int area;
    public final String climate;

    public Habitat(String country, int area, String climate) {
        this.country = country;
        this.area = area;
        this.climate = climate;
    }

    public String getCountry() {
        return country;
    }

    public int getArea() {
        return area;
    }

    public String getClimate() {
        return climate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Habitat habitat = (Habitat) obj;
        return this.country.equals(habitat.country) &&
                this.area == habitat.area &&
                this.climate.equals(habitat.climate);
    }

    @Override
    public String toString() {
        return "Species{" +
                "country=" + country +
                ", area=" + area +
                ", climate=" + climate +
                '}';
    }
}
