package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    private final int age;
    private final String alias;
    private final Color color;
    private final Animal father;
    private final Animal mother;

    public Animal(int age, String alias) {
        this(age, alias, Color.UNDEFINED, null, null);
    }

    public Animal(int age, String alias, Color color) {
        this(age, alias, color, null, null);
    }

    public Animal(int age, String alias, Color color, Animal father, Animal mother) {
        this.age = age;
        this.alias = alias;
        this.color = color;
        this.father = father;
        this.mother = mother;
    }

    public int getAge() {
        return age;
    }

    public String getAlias() {
        return alias;
    }

    public Color getColor() {
        return color;
    }

    public Animal getFather() {
        return father;
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
                Objects.equals(alias, animal.alias) &&
                Objects.equals(father, animal.father) &&
                Objects.equals(mother, animal.mother) &&
                color == animal.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, alias, color, father, mother);
    }

    @Override
    public String toString() {
        return "Animal[" +
                "age=" + age +
                ", alias=" + alias +
                ", color=" + color +
                ", father=" + father +
                ", mother=" + mother +
                ']';
    }

}
