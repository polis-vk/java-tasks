package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Food implements Serializable {
    private String name;
    private int energy;

    public Food(String name, int energy) {
        this.name = name;
        this.energy = energy;
    }

    public Food() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return energy == food.energy && Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, energy);
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", energy=" + energy +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
