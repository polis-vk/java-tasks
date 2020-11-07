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
public class AnimalWithMethods implements Serializable, Cloneable {
    public enum Gender {MALE, FEMALE, NEUTRAL, MAYBE_MALE, MAYBE_FEMALE, OTHER}

    private List<String> habitat;
    private String species;
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
        out.writeObject(habitat);
        out.writeUTF(species);
        out.writeShort(age);
        out.writeObject(gender);
        out.writeBoolean(realExistence);
        out.writeObject(children);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.habitat = (ArrayList<String>) in.readObject();
        this.species = in.readUTF();
        age = in.readShort();
        gender = (Gender) in.readObject();
        realExistence = in.readBoolean();
        children = (ArrayList<AnimalWithMethods>) in.readObject();
        if (age < 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected AnimalExternalizable clone() throws CloneNotSupportedException {
        return (AnimalExternalizable) super.clone();
    }

    @Override
    public String toString() {
        return "Habitat: " + this.habitat.toString() +
                " Species: " + this.species +
                " Age: " + this.age +
                " Gender: " + this.gender +
                " Real existence: " + this.realExistence +
                " Children: " + this.children.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AnimalWithMethods o = (AnimalWithMethods) obj;
        return this.habitat.toString().equals(o.getHabitat().toString()) &&
                this.species.equals(o.species) &&
                this.age == o.age &&
                this.gender == o.gender &&
                this.realExistence == o.realExistence &&
                this.children.equals(o.children);
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
