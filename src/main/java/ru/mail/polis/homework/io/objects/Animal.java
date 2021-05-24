package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.*;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    private String name;
    private int age;
    private PhylumOfAnimals phylumOfAnimals;
    private List<String> foods;

    public Animal(String name, int age, PhylumOfAnimals phylumOfAnimals) {
        this.name = name;
        this.age = age;
        this.phylumOfAnimals = phylumOfAnimals;
        new Animal();
    }

    public Animal() {
        foods = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhylumOfAnimals(PhylumOfAnimals phylumOfAnimals) {
        this.phylumOfAnimals = phylumOfAnimals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return age == animal.age && Objects.equals(name, animal.name) && phylumOfAnimals == animal.phylumOfAnimals &&
                Objects.equals(foods, animal.foods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, phylumOfAnimals, foods);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phylumOfAnimals=" + phylumOfAnimals +
                ", food=" + foods +
                '}';
    }

    public PhylumOfAnimals getPhylumOfAnimals() {
        return phylumOfAnimals;
    }

    public List<String> getFoods() {
        return foods;
    }

    public void setFoods(List<String> food) {
        this.foods = food;
    }
}
