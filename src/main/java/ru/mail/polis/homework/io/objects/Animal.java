package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */

enum Habitat {
    WATER,
    LAND,
    AIR
}

public class Animal implements Serializable {
    private final String name;
    private final Habitat habitat;
    private final Iq iq;
    private final int age;
    private final boolean isPredator;
    private final List<String> food;

    public Animal(String name, Habitat habitat, Iq iq, int age, boolean isPredator, List<String> food) {
        this.name = name;
        this.habitat = habitat;
        this.iq = iq;
        this.age = age;
        this.isPredator = isPredator;
        this.food = food;
    }

    public Iq getIq() {
        return iq;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public List<String> getFood() {
        return food;
    }

    public boolean isPredator() {
        return isPredator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return getName().equals(animal.getName()) &&
                getHabitat() == animal.getHabitat() &&
                getIq().equals(animal.getIq()) &&
                getAge() == (animal.getAge()) &&
                isPredator() == animal.isPredator() &&
                getFood().equals(animal.getFood());
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", habitat=" + habitat +
                ", iq=" + iq +
                ", age=" + age +
                ", isPredator=" + isPredator +
                ", food=" + food +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHabitat(), getIq(), getAge(), isPredator(), getFood());
    }
}
