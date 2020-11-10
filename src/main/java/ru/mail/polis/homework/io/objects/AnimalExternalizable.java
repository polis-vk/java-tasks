package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable extends Animal implements Externalizable {

    private String name;
    private Habitat habitat;
    private Iq iq;
    private int age;
    private boolean isPredator;
    private List<String> food;

    public AnimalExternalizable() {

    }

    public AnimalExternalizable(String name, Habitat habitat, Iq iq, int age, boolean isPredator, List<String> food) {
        this.name = name;
        this.habitat = habitat;
        this.iq = iq;
        this.age = age;
        this.isPredator = isPredator;
        this.food = food;
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeUTF(this.getName());
        objectOutput.writeObject(this.getHabitat());
        objectOutput.writeObject(this.getIq().getIqSize());
        objectOutput.writeInt(this.getAge());
        objectOutput.writeBoolean(this.isPredator());
        objectOutput.writeInt(food.size());
        for (String s : food) {
            objectOutput.writeUTF(s);
        }
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.name = objectInput.readUTF();
        this.habitat = (Habitat) objectInput.readObject();
        this.iq = new Iq(objectInput.readInt());
        this.age = objectInput.readInt();
        this.isPredator = objectInput.readBoolean();
        int size = objectInput.readInt();
        food = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            food.add(objectInput.readUTF());
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
        if (!(o instanceof AnimalExternalizable)) return false;
        AnimalExternalizable animalExternalizable = (AnimalExternalizable) o;
        return name.equals(animalExternalizable.name) &&
                habitat == animalExternalizable.habitat &&
                iq.equals(animalExternalizable.iq) &&
                age == animalExternalizable.age &&
                isPredator == animalExternalizable.isPredator &&
                food.equals(animalExternalizable.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, habitat, iq, age, isPredator, food);
    }
}
