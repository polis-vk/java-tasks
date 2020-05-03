package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.nio.file.Files;

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

    public Animal(int age, String name, Animal mom, Animal dad, Type type) {
        this.age = age;
        this.name = name;
        this.mom = mom;
        this.dad = dad;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name='" + name +
                ", mom=" + mom +
                ", dad=" + dad +
                ", type=" + type +
                '}';
    }
}

enum Type {
    Dog,
    Cat,
    Lion,
    Crocodile,
    Fish,
    Chicken,
    unidentifiedAnimal;

    public static Type parseType(String type) {
        switch (type) {
            case "Dog":
                return Dog;
            case "Cat":
                return Cat;
            case "Lion:":
                return Lion;
            case "Crocodile":
                return Crocodile;
            case "Fish":
                return Fish;
            case "Chicken":
                return Chicken;
            default:
                return unidentifiedAnimal;
        }
    }
}
