package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    enum FoodPreferences { //не знаю как это по-научному назвать
        CARNIVOROUS,
        HERBIVOROUS,
        OMNIVOROUS
    }

    private final String kind;

    private final Tail tail;

    private double energy;

    private final Animal.FoodPreferences foodPreferences;

    private final int averageLifeExpectancy;

    private List<String> habitats;

    public AnimalWithMethods(String kind, boolean isTailLong,
                             Animal.FoodPreferences foodPreferences, int averageLifeExpectancy) {
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

    public Animal.FoodPreferences getFoodPreferences() {
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

    public void eat() {
        if (energy < 90) {
            setEnergy(energy + 10);
        } else {
            setEnergy(100);
        }
    }
}
