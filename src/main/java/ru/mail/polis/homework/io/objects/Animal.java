package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private String name;
    private int countOfLegs;
    private AnimalMoveType moveType;

    private AnimalHabitat location;
    private boolean isWild;
    private boolean isAnimal;

    public Animal(String name, int countOfLegs, AnimalMoveType moveType, AnimalHabitat location, boolean isWild, boolean isAnimal) {
        this.name = name;
        this.countOfLegs = countOfLegs;
        this.moveType = moveType;
        this.location = location;
        this.isWild = isWild;
        this.isAnimal = isAnimal;
    }

    public String getName() {
        return name;
    }

    public int getCountOfLegs() {
        return countOfLegs;
    }

    public AnimalMoveType getMoveType() {
        return moveType;
    }

    public AnimalHabitat getLocation() {
        return location;
    }

    public boolean isWild() {
        return isWild;
    }

    public boolean isAnimal() {
        return isAnimal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountOfLegs(int countOfLegs) {
        this.countOfLegs = countOfLegs;
    }

    public void setMoveType(AnimalMoveType moveType) {
        this.moveType = moveType;
    }

    public void setLocation(AnimalHabitat location) {
        this.location = location;
    }

    public void setWild(boolean wild) {
        isWild = wild;
    }

    public void setAnimal(boolean animal) {
        isAnimal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return getCountOfLegs() == animal.getCountOfLegs() && isWild() == animal.isWild()
                && isAnimal() == animal.isAnimal() && Objects.equals(getName(), animal.getName())
                && getMoveType() == animal.getMoveType() && Objects.equals(getLocation(), animal.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCountOfLegs(), getMoveType(), getLocation(), isWild(), isAnimal());
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", countOfLegs=" + countOfLegs +
                ", moveType=" + moveType +
                ", location=" + location +
                ", isWild=" + isWild +
                ", isAnimal=" + isAnimal +
                '}';
    }
}
