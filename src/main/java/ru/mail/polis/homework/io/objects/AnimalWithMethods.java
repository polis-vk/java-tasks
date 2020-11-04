package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {

    private String kind;

    private Tail tail;

    private double energy;

    private Animal.FoodPreferences foodPreferences;

    private int averageLifeExpectancy;

    private List<String> habitats;

    public AnimalWithMethods() {}

    public AnimalWithMethods(String kind, Tail tail, double energy,
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

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Tail getTail() {
        return tail;
    }

    public void setTail(Tail tail) {
        this.tail = tail;
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

    public void setFoodPreferences(Animal.FoodPreferences foodPreferences) {
        this.foodPreferences = foodPreferences;
    }

    public int getAverageLifeExpectancy() {
        return averageLifeExpectancy;
    }

    public void setAverageLifeExpectancy(int averageLifeExpectancy) {
        this.averageLifeExpectancy = averageLifeExpectancy;
    }

    public List<String> getHabitats() {
        return habitats;
    }

    public void setHabitats(List<String> habitats) {
        this.habitats = habitats;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(getKind());
        out.writeObject(getTail());
        out.writeDouble(getEnergy());
        out.writeObject(getFoodPreferences());
        out.writeInt(getAverageLifeExpectancy());
        for (String habitat : getHabitats()) {
            out.writeUTF(habitat);
        }
    }

    private void readObject(ObjectInputStream in) throws  IOException, ClassNotFoundException {
        setKind(in.readUTF());
        setTail((Tail)in.readObject());
        setEnergy(in.readDouble());
        setFoodPreferences((Animal.FoodPreferences)in.readObject());
        setAverageLifeExpectancy(in.readInt());
        int listSize = in.readInt();
        List<String> habitats = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            habitats.add(in.readUTF());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return Double.compare(that.energy, energy) == 0 &&
                averageLifeExpectancy == that.averageLifeExpectancy &&
                Objects.equals(kind, that.kind) &&
                Objects.equals(tail, that.tail) &&
                foodPreferences == that.foodPreferences &&
                Objects.equals(habitats, that.habitats);
    }
}
