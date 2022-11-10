package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Population implements Serializable {
    private final String name;
    private final long size;
    private final int density;

    public Population(String name, long size, int density) {
        this.name = name;
        this.size = size;
        this.density = density;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public int getDensity() {
        return density;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Population population = (Population) o;
        return population.name.equals(name) && population.size == size && population.density == density;
    }

    @Override
    public String toString() {
        return "Population{" +
                "name='" + name + '\'' +
                ", size=" + size + '\'' +
                ", density=" + density +
                '}';
    }

}
