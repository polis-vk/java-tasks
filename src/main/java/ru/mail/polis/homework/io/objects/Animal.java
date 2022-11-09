package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

import ru.mail.polis.homework.oop.vet.MoveType;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    int countLegs;
    boolean isPet;
    boolean isFly;
    String name;
    MoveType moveType;
    Population population;

    public Animal(int countLegs, boolean isPet, boolean isFly, String name, MoveType moveType, Population population) {
        this.countLegs = countLegs;
        this.isPet = isPet;
        this.isFly = isFly;
        this.name = name;
        this.moveType = moveType;
        this.population = population;
    }

    public Animal() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return countLegs == animal.getCountLegs() && isFly == animal.isFly()
                && isPet == animal.isPet() && moveType == animal.getMoveType()
                && Objects.equals(name, animal.getName())
                && Objects.equals(population, animal.getPopulation());
    }

    @Override
    public String toString() {
        return "Animal{" +
                "countLegs=" + countLegs +
                ", name='" + (name == null ? "null" : name) +
                ", isPet=" + isPet +
                ", isFly=" + isFly +
                ", moveType=" + moveType +
                ", population" + (population == null ? "null" : population);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(countLegs, name, isFly, isPet, moveType);
        result = 31 * result + Objects.hashCode(population);
        return result;
    }

    public int getCountLegs() {
        return countLegs;
    }

    public void setCountLegs(int countLegs) {
        this.countLegs = countLegs;
    }

    public boolean isPet() {
        return isPet;
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public boolean isFly() {
        return isFly;
    }

    public void setFly(boolean fly) {
        isFly = fly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }
}
