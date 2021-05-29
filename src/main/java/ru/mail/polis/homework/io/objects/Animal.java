package ru.mail.polis.homework.io.objects;

import jdk.tools.jlink.internal.Utils;

import java.io.Serializable;
import java.util.*;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private int age;
    private String name;
    private AnimalKind kind;
    private AnimalGender gender;
    private Animal mother;

    public Animal(int age,String name, AnimalKind kind, AnimalGender gender, Animal mother) {
        this.age = age;
        this.name = name;
        this.kind = kind;
        this.gender = gender;
        this.mother = mother;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public AnimalKind getKind() {
        return kind;
    }

    public AnimalGender getAnimalGender() {
        return gender;
    }

    public Animal getMother() {
        return mother;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Objects.equals(name, animal.name) &&
                kind == animal.kind &&
                gender == animal.gender &&
                Objects.equals(mother, animal.mother);

    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, kind, gender, mother);
    }

    @Override
    public String toString() {
        return "Animal[" +
                "age=" + age +
                ", name=" + name +
                ", kind=" + kind +
                ", gender=" + gender +
                ", mother=" + mother +
                ']';
    }
}


