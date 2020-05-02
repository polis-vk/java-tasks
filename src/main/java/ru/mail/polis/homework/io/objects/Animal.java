package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    private final String name;
    private int age;
    private final Animal dad;
    private final Animal mum;
    private final Type type;

    public Animal(String name, int age, Type type) {
        this(name, age, null, null, type);
    }

    public Animal(String name, Type type) {
        this(name, 0, null, null, type);
    }

    public Animal(String name, int age, Animal dad, Animal mum, Type type) {
        this.name = name;
        this.age = age;
        this.dad = dad;
        this.mum = mum;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                name.equals(animal.name) &&
                Objects.equals(dad, animal.dad) &&
                Objects.equals(mum, animal.mum) &&
                type == animal.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, dad, mum, type);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name=" + name +
                ", age=" + age +
                ", type=" + type +
                ", dad=" + dad +
                ", mam=" + mum +
                '}';
    }
}
