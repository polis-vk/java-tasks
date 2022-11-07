package ru.mail.polis.homework.io.objects;


import java.io.Serializable;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private String name;
    private int age;
    private double weight;
    private boolean alive;
    private AnimalType type;
    private boolean isPet;
    private PlaceOfResidence placeOfResidence;

    public Animal(String name, int age, double weight, boolean alive, AnimalType type, boolean isPet, PlaceOfResidence placeOfResidence) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.alive = alive;
        this.type = type;
        this.isPet = isPet;
        this.placeOfResidence = placeOfResidence;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isAlive() {
        return alive;
    }

    public AnimalType getType() {
        return type;
    }

    public boolean isPet() {
        return isPet;
    }

    public PlaceOfResidence getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public void setPlaceOfResidence(PlaceOfResidence placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", alive=" + alive +
                ", type=" + type +
                ", isPet=" + isPet +
                ", placeOfResidence=" + placeOfResidence +
                '}';
    }
}
