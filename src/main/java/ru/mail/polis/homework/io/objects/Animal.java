package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    private final String name;
    private final int age;
    private final Holder holder;
    private final AnimalType animalType;
    private final List<Food> food;

    public Animal(String name, int age, AnimalType animalType) {
        this(name, age, animalType, null);
    }

    public Animal(String name, AnimalType animalType) {
        this(name, 0, animalType, null);
    }

    public Animal(String name, int age, AnimalType animalType, Holder holder) {
        this.name = name;
        this.age = age;
        this.animalType = animalType;
        this.holder = holder;
        food = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    public AnimalType getAnimalType() {
        return animalType;
    }


    public Holder getHolder() {
        return holder;
    }

    public List<Food> getFood() {
        return food;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                name.equals(animal.name) &&
                Objects.equals(holder, animal.holder) &&
                animalType == animal.animalType &&
                food.equals(animal.food);
    }

}

