package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    protected int age;
    protected String name;
    protected Habitat habitat;
    protected List<String> food;
    protected boolean sexIsMale;
    protected double height;
    protected Heart heart;

    public enum Habitat {
        WATER,
        LAND,
        AIR
    }

    public Animal(int age, String name, Habitat habitat, List<String> food,
                  boolean gender, double height, Heart heart) {
        this.age = age;
        this.name = name;
        this.habitat = habitat;
        this.food = food;
        this.sexIsMale = gender;
        this.height = height;
        this.heart = heart;
    }

    public Animal() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                sexIsMale == animal.sexIsMale &&
                Double.compare(animal.height, height) == 0 &&
                Objects.equals(name, animal.name) &&
                habitat == animal.habitat &&
                Objects.equals(food, animal.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, habitat, food, sexIsMale, height);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", habitat=" + habitat +
                ", food=" + food +
                ", sexIsMale=" + sexIsMale +
                ", height=" + height +
                '}';
    }
}
