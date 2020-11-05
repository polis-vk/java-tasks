package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */

public class Animal implements Serializable {
    private String name;
    private BioTag tag;
    private int age;
    private List<String> habitat;
    private Size size;
    private double weight;
    private boolean canFly;

    enum Size {
        LARGE,
        MEDIUM,
        SMALL
    }

    public Animal() {
    }

    public Animal(String name, BioTag tag, int age, List<String> habitat, Size size, double weight, boolean canFly) {
        this.name = name;
        this.tag = tag;
        this.age = age;
        this.habitat = habitat;
        this.size = size;
        this.weight = weight;
        this.canFly = canFly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BioTag getTag() {
        return tag;
    }

    public void setTag(BioTag tag) {
        this.tag = tag;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getHabitat() {
        return habitat;
    }

    public void setHabitat(List<String> habitat) {
        this.habitat = habitat;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Double.compare(animal.weight, weight) == 0 &&
                canFly == animal.canFly &&
                Objects.equals(name, animal.name) &&
                Objects.equals(tag, animal.tag) &&
                Objects.equals(habitat, animal.habitat) &&
                size == animal.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tag, age, habitat, size, weight, canFly);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", tag=" + tag +
                ", age=" + age +
                ", habitat=" + habitat +
                ", size=" + size +
                ", weight=" + weight +
                ", canFly=" + canFly +
                '}';
    }
}
