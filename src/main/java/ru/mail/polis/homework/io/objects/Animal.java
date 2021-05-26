package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private final int age;
    private final String name;
    private final Species species;
    private final Areal areal;
    private final Animal ancestor;

    public Animal(int age, String name) {
        this(age, name, Species.UNDEFINED, Areal.UNDEFINED, null);
    }

    public Animal(int age, String name, Species species) {
        this(age, name, species, Areal.UNDEFINED, null);
    }

    public Animal(int age, String name, Species species, Areal areal) {
        this(age, name, species, areal, null);
    }

    public Animal(int age, String name, Species species, Areal areal, Animal ancestor) {
        this.age = age;
        this.name = name;
        this.species = species;
        this.areal = areal;
        this.ancestor = ancestor;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public Areal getAreal() {
        return areal;
    }

    public Animal getAncestor() {
        return ancestor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Objects.equals(name, animal.name) &&
                species == animal.species &&
                areal == animal.areal &&
                Objects.equals(ancestor, animal.ancestor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, species, areal, ancestor);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age = " + age +
                ", name = " + name +
                ", species = " + species +
                ", areal = " + areal +
                ", ancestor = " + ancestor +
                '}';
    }
}
