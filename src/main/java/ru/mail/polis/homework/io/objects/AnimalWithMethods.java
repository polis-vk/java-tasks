package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    public enum Gender {MALE, FEMALE, NEUTRAL, MAYBE_MALE, MAYBE_FEMALE, OTHER}

    private List<String> habitat;
    private final String species;
    private short age;
    private Gender gender;
    private boolean realExistence;
    private List<AnimalWithMethods> children;

    public AnimalWithMethods(ArrayList<String> habitat, String species, short age, Gender gender, boolean realExistence) {
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        this.habitat = habitat;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.realExistence = realExistence;
        this.children = new ArrayList<>();
    }

    public AnimalWithMethods(ArrayList<String> habitat, String species, short age, Gender gender, boolean realExistence, ArrayList<AnimalWithMethods> children) {
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        this.habitat = habitat;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.realExistence = realExistence;
        this.children = children;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (age < 0) {
            throw new IllegalArgumentException();
        }
    }

    public List<String> getHabitat() {
        return habitat;
    }

    public void setHabitat(List<String> habitat) {
        this.habitat = habitat;
    }

    public void addHabitat(String habitat) {
        this.habitat.add(habitat);
    }

    public String getSpecies() {
        return species;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public void increaseAgeOn(short diff) {
        if (diff < 0) {
            throw new IllegalArgumentException();
        }
        this.age += diff;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isRealExistence() {
        return realExistence;
    }

    public void setRealExistence(boolean realExistence) {
        this.realExistence = realExistence;
    }

    public List<AnimalWithMethods> getChildren() {
        return children;
    }

    public void setChildren(List<AnimalWithMethods> children) {
        this.children = children;
    }

    public void addChild(AnimalWithMethods child) {
        this.children.add(child);
    }
}
