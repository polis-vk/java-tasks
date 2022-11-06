package ru.mail.polis.homework.io.objects;


import java.io.Serializable;

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
