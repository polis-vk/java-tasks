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
public class AnimalWithMethods extends Animal implements Serializable {

    private String name;
    private Habitat habitat;
    private Iq iq;
    private int age;
    private boolean isPredator;
    private List<String> food;

    public AnimalWithMethods() {

    }

    public AnimalWithMethods(String name, Habitat habitat, Iq iq, int age, boolean isPredator, List<String> food) {
        this.name = name;
        this.habitat = habitat;
        this.iq = iq;
        this.age = age;
        this.isPredator = isPredator;
        this.food = food;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeObject(habitat);
        out.writeObject(iq);
        out.writeInt(age);
        out.writeBoolean(isPredator);
        out.writeInt(food.size());
        for (String s : food) {
            out.writeUTF(s);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        this.habitat = (Habitat) in.readObject();
        this.iq = (Iq) in.readObject();
        this.age = in.readInt();
        this.isPredator = in.readBoolean();
        int size = in.readInt();
        food = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            food.add(in.readUTF());
        }
    }

    public void setIq(Iq iq) {
        this.iq = iq;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFood(List<String> food) {
        this.food = food;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public Iq getIq() {
        return iq;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public List<String> getFood() {
        return food;
    }

    public boolean isPredator() {
        return isPredator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalWithMethods)) return false;
        AnimalWithMethods animalWithMethods = (AnimalWithMethods) o;
        return name.equals(animalWithMethods.name) &&
                habitat == animalWithMethods.habitat &&
                iq.equals(animalWithMethods.iq) &&
                age == animalWithMethods.age &&
                isPredator == animalWithMethods.isPredator &&
                food.equals(animalWithMethods.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, habitat, iq, age, iq, food);
    }
}
