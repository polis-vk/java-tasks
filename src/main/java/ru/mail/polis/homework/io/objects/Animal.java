package ru.mail.polis.homework.io.objects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    private final int age;
    private final String name;
    private final Animal mom;
    private final Animal dad;
    private final Type type;

    public Animal(int age, String name) {
        this(age, name, null, null, Type.unidentifiedAnimal);
    }

    public Animal(int age, String name, Type type) {
        this(age, name, null, null, type);
    }

    public Animal(int age, String name, Animal mom, Animal dad, Type type) {
        this.age = age;
        this.name = name;
        this.mom = mom;
        this.dad = dad;
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Animal getMom() {
        return mom;
    }

    public Animal getDad() {
        return dad;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Objects.equals(name, animal.name) &&
                Objects.equals(mom, animal.mom) &&
                Objects.equals(dad, animal.dad) &&
                type == animal.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, mom, dad, type);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name=" + name +
                ", mom=" + mom +
                ", dad=" + dad +
                ", type=" + type +
                '}';
    }
}
