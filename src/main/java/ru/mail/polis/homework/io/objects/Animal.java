package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    private final String name;
    private final AnimalType type;
    private final AnimalOwner owner;
    private final List<String> listFood;

    public Animal(String name, AnimalType type, AnimalOwner owner, List<String> listFood) {
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.listFood = listFood;
    }

    public String getName() {
        return name;
    }

    public List<String> getListFood() {
        return listFood;
    }

    public AnimalType getType() {
        return type;
    }

    public AnimalOwner getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal other = (Animal) obj;
        return other.name.equals(this.name)
                && other.type.equals(this.type)
                && other.owner.equals(this.owner)
                && other.listFood.equals(this.listFood);
    }

    @Override
    public String toString() {
        return "{ name : " + this.name + ", type : "
                + this.type + ", "
                + this.owner + ", " + this.listFood + " }";
    }
}
