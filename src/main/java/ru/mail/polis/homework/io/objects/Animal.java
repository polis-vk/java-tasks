package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    private String name;
    private int age;
    private PhylumOfAnimals phylumOfAnimals;

    public Animal(String name, int age, PhylumOfAnimals phylumOfAnimals) {
        this.name = name;
        this.age = age;
        this.phylumOfAnimals = phylumOfAnimals;
    }

    public Animal() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhylumOfAnimals(PhylumOfAnimals phylumOfAnimals) {
        this.phylumOfAnimals = phylumOfAnimals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return age == animal.age && Objects.equals(name, animal.name) && phylumOfAnimals == animal.phylumOfAnimals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, phylumOfAnimals);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phylumOfAnimals=" + phylumOfAnimals +
                '}';
    }

    public PhylumOfAnimals getPhylumOfAnimals() {
        return phylumOfAnimals;
    }

    public enum PhylumOfAnimals {
        ANNELIDS,
        ARTHROPODS,
        BRYOZOA,
        CHORDATES,
        CNIDARIA,
        ENCHINODERMS,
        MOLLUSCS,
        NEMATODES,
        PLATYHELMINTHES,
        ROTIFERS,
        SPONGES;

        private static final List<PhylumOfAnimals> VALUES = Arrays.asList(PhylumOfAnimals.values());
        private static final int SIZE = VALUES.size();

        public static PhylumOfAnimals getRandom(Random random) {
            return VALUES.get(random.nextInt(SIZE));
        }
    }

}
