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

    public Animal(String kind, Tail tail, double energy,
                  FoodPreferences foodPreferences, int averageLifeExpectancy, List<String> habitats) {
        this.kind = kind;
        this.tail = tail;
        this.energy = energy;
        this.foodPreferences = foodPreferences;
        this.averageLifeExpectancy = averageLifeExpectancy;
        this.habitats = habitats;
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

    public void setFoodPreferences(FoodPreferences foodPreferences) {
        this.foodPreferences = foodPreferences;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Double.compare(animal.energy, energy) == 0 &&
                averageLifeExpectancy == animal.averageLifeExpectancy &&
                Objects.equals(kind, animal.kind) &&
                Objects.equals(tail, animal.tail) &&
                foodPreferences == animal.foodPreferences &&
                Objects.equals(habitats, animal.habitats);
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
