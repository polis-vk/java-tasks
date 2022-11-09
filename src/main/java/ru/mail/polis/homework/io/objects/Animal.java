package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    public Animal(String name, AnimalType type, int age, double weight, Meal meal) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.weight = weight;
        this.meal = meal;
    }
    public String name;
    public AnimalType type;
    public int age;
    public double weight;
    public boolean isDangerous() {
        return age > 2 && weight > 40;
    }

    public Meal meal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age && Double.compare(animal.weight, weight) == 0 && name.equals(animal.name) && type == animal.type && meal.equals(animal.meal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, age, weight, meal);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", age=" + age +
                ", weight=" + weight +
                ", meal=" + meal +
                '}';
    }


}
