package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private int weight;
    private String name;

    public enum Color {
        RED,
        BLUE,
        GREEN,
        BLACK
    }

    private Color color;

    private Owner owner;

    public Animal() {
    }

    public Animal(int weight, String name, Color color, Owner owner) {
        this.weight = weight;
        this.name = name;
        this.color = color;
        this.owner = owner;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return weight == animal.weight &&
                name.equals(animal.name) &&
                owner.equals(animal.getOwner()) &&
                color == animal.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, name, owner, color);
    }
}
