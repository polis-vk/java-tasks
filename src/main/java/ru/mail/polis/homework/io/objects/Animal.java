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
    private boolean tail;
    private AnimalType type;
    private boolean predator;

    public Animal(String name, int age, double weight, boolean tail, AnimalType type, boolean predator) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.tail = tail;
        this.type = type;
        this.predator = predator;
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

    public boolean isTail() {
        return tail;
    }

    public AnimalType getType() {
        return type;
    }

    public boolean isPredator() {
        return predator;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", tail=" + tail +
                ", type=" + type +
                ", isPredator=" + predator +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, tail, type, predator);
    }
}
