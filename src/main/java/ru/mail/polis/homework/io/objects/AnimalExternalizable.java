package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Objects;

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

    private Animal.FoodPreferences foodPreferences;

    private int averageLifeExpectancy;

    private List<String> habitats;

    public AnimalExternalizable() {}

    public AnimalExternalizable(String kind, Tail tail, double energy,
                                Animal.FoodPreferences foodPreferences, int averageLifeExpectancy, List<String> habitats) {
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


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(kind);
        out.writeObject(tail);
        out.writeDouble(energy);
        out.writeObject(foodPreferences);
        out.writeInt(averageLifeExpectancy);
        out.writeObject(habitats);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        kind = in.readUTF();
        tail = (Tail) in.readObject();
        energy = in.readDouble();
        foodPreferences = (Animal.FoodPreferences) in.readObject();
        averageLifeExpectancy = in.readInt();
        habitats = (List<String>) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return Double.compare(that.energy, energy) == 0 &&
                averageLifeExpectancy == that.averageLifeExpectancy &&
                Objects.equals(kind, that.kind) &&
                Objects.equals(tail, that.tail) &&
                foodPreferences == that.foodPreferences &&
                Objects.equals(habitats, that.habitats);
    }
}
