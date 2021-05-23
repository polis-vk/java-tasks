package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Habitat implements Serializable {
    private final Mainland mainland;
    private final ClimateZone climateZone;

    public Habitat() {
        this.mainland = Mainland.UNKNOWN;
        this.climateZone = ClimateZone.UNKNOWN;
    }

    public Habitat(ClimateZone climateZone, Mainland mainland) {
        this.mainland = mainland;
        this.climateZone = climateZone;
    }


    public Mainland getMainland() {
        return mainland;
    }

    public ClimateZone getClimateZone() {
        return climateZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitat habitat = (Habitat) o;
        return mainland == habitat.mainland &&
                climateZone == habitat.climateZone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainland, climateZone);
    }

    @Override
    public String toString() {
        return "Habitat{" +
                "mainland=" + mainland +
                ", climateZone=" + climateZone +
                '}';
    }
}
