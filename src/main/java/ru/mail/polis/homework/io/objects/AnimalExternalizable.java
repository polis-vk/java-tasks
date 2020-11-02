package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {

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

    public AnimalExternalizable() {}

    public AnimalExternalizable(String kind, boolean isTailLong,
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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getKind());
        out.writeObject(this.getTail());
        out.writeObject(this.getEnergy());
        out.writeObject(this.getFoodPreferences());
        out.writeObject(this.getAverageLifeExpectancy());
        out.writeObject(this.getHabitats());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        kind = (String) in.readObject();
        tail = (Tail) in.readObject();
        energy = (Double) in.readObject();
        foodPreferences = (FoodPreferences) in.readObject();
        averageLifeExpectancy = (int) in.readObject();
        habitats = (List<String>) in.readObject();
    }
}
