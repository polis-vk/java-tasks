package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    enum FoodPreferences { //не знаю как это по-научному назвать
        CARNIVOROUS,
        HERBIVOROUS,
        OMNIVOROUS
    }

    private String kind;

    private Tail tail;

    private double energy;

    private FoodPreferences foodPreferences;

    private int averageLifeExpectancy;

    private List<String> habitats;

    public Animal() {}

    public Animal(String kind, boolean isTailLong,
                  FoodPreferences foodPreferences, int averageLifeExpectancy) {
        this.kind = kind;
        this.tail = new Tail(isTailLong);
        this.foodPreferences = foodPreferences;
        this.averageLifeExpectancy = averageLifeExpectancy;
        this.habitats = new ArrayList<>();
    }

    public String getKind() {
        return kind;
    }

    public Tail getTail() {
        return tail;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public FoodPreferences getFoodPreferences() {
        return foodPreferences;
    }

    public int getAverageLifeExpectancy() {
        return averageLifeExpectancy;
    }

    public List<String> getHabitats() {
        return habitats;
    }

    public void setHabitats(List<String> habitats) {
        this.habitats = habitats;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "kind='" + kind + '\'' +
                ", tail=" + tail +
                ", energy=" + energy +
                ", foodPreferences=" + foodPreferences +
                ", averageLifeExpectancy=" + averageLifeExpectancy +
                ", habitats=" + habitats +
                '}';
    }
}
