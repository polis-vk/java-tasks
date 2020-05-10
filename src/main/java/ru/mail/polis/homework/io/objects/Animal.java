package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    public final String name;
    public final int age;
    public final Species species;
    public final List<Habitat> habitats;

    public Animal(String name, int age, Species species, List<Habitat> habitats) {
        this.name = name;
        this.age = age;
        this.species = species;
        this.habitats = habitats;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Species getSpecies() {
        return species;
    }

    public List<Habitat> getHabitats() {
        return habitats;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal animal = (Animal) obj;
        return this.age == animal.age &&
                this.name.equals(animal.name) &&
                this.species.equals(animal.species) &&
                this.habitats.equals(animal.habitats);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name=" + name +
                ", age=" + age +
                ", species=" + species +
                ", habitats=" + habitats +
                '}';
    }
}
