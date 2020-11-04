package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */




public class Animal implements Serializable {
    private String name;
    private double weight;
    private Parents parents;
    private List<Parents> genericOfRelatives;
    private Colour colour;

    public Animal(String name, double weight, Parents parents, List<Parents> genericOfRelatives, Colour colour) {
        this.name = name;
        this.weight = weight;
        this.parents = parents;
        this.genericOfRelatives = new ArrayList<>(genericOfRelatives);
        this.colour = colour;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Animal animal = (Animal) obj;
        return name.equals(animal.name) &&
                weight == animal.weight &&
                parents.equals(animal.parents) &&
                genericOfRelatives.equals(animal.genericOfRelatives) &&
                colour.equals(animal.colour);

    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", parents='" + parents.toString() + '\'' +
                ", genericOfRelatives='" + genericOfRelatives.toString() + '\'' +
                ", color='" + colour.toString() + '\'' +
                '}';
    }

    public Colour getColor() {
        return colour;
    }

    public List<Parents> getGenericOfRelatives() {
        return genericOfRelatives;
    }

    public Parents getParents() {
        return parents;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}
