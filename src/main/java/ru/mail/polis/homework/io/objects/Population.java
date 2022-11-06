package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Population implements Serializable {
    Mainland mainland;
    long size;

    public Population(Mainland mainland, long size) {
        this.mainland = mainland;
        this.size = size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainland, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Population population = (Population) o;
        return mainland == population.mainland && population.size == size;
    }

    @Override
    public String toString() {
        return "Population{" +
                "MainLand=" + mainland.toString() +
                ", size='" + size +
                '}';
    }
}
