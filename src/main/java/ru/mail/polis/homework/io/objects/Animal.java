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
    private final String name;
    private final int age;
    private final boolean isWild;
    private final boolean isFed;
    private final AnimalAbilityType animalAbilityType;
    private final Location animalLocation;

    public Animal(Location animalLocation, String name, int age, boolean isWild, boolean isFed, AnimalAbilityType animalAbilityType) {
        this.name = name;
        this.age = age;
        this.isWild = isWild;
        this.isFed = isFed;
        this.animalAbilityType = animalAbilityType;
        this.animalLocation = animalLocation;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isWild() {
        return isWild;
    }

    public boolean isFed() {
        return isFed;
    }

    public AnimalAbilityType getAnimalAbilityType() {
        return animalAbilityType;
    }

    public Location getAnimalLocation() {
        return animalLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age && isWild == animal.isWild && isFed == animal.isFed && Objects.equals(name, animal.name) && animalAbilityType == animal.animalAbilityType && Objects.equals(animalLocation, animal.animalLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isWild, isFed, animalAbilityType, animalLocation);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isWild=" + isWild +
                ", isFed=" + isFed +
                ", animalAbilityType=" + animalAbilityType +
                ", animalLocation=" + animalLocation +
                '}';
    }
}
