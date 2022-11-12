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
    private int age;
    private double weight;
    private boolean isVegetarian;
    private AnimalType animalType;
    private Worker overseer;

    public Animal(String name, int age, double weight,
                  boolean isVegetarian, AnimalType animalType, Worker overseer) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.isVegetarian = isVegetarian;
        this.animalType = animalType;
        this.overseer = overseer;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public Worker getOverseer() {
        return overseer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.getName())
                && age == animal.getAge()
                && Double.compare(animal.getWeight(), weight) == 0
                && isVegetarian == animal.isVegetarian()
                && Objects.equals(animalType, animal.getAnimalType())
                && Objects.equals(overseer, animal.getOverseer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, isVegetarian, animalType, overseer);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", isVegetarian=" + isVegetarian +
                ", animalType=" + animalType +
                ", overseer" + overseer +
                '}';
    }
}
