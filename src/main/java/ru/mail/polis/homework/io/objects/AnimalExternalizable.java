package ru.mail.polis.homework.io.objects;

import com.sun.tools.javac.jvm.Gen;

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
public class AnimalExternalizable implements Externalizable, Cloneable {
    public enum Gender {MALE, FEMALE, NEUTRAL, MAYBE_MALE, MAYBE_FEMALE, OTHER}

    private List<String> habitat;
    private String species;
    private short age;
    private AnimalExternalizable.Gender gender;
    private boolean realExistence;
    private List<AnimalExternalizable> children;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(ArrayList<String> habitat, String species, short age, AnimalExternalizable.Gender gender, boolean realExistence) {
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

    public AnimalExternalizable(ArrayList<String> habitat, String species, short age, AnimalExternalizable.Gender gender, boolean realExistence, ArrayList<AnimalExternalizable> children) {
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(habitat);
        out.writeUTF(species);
        out.writeShort(age);
        out.writeObject(gender);
        out.writeBoolean(realExistence);
        out.writeObject(children);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        habitat = (ArrayList<String>) in.readObject();
        species = in.readUTF();
        age = in.readShort();
        gender = (AnimalExternalizable.Gender) in.readObject();
        realExistence = in.readBoolean();
        children = (ArrayList<AnimalExternalizable>) in.readObject();
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
        AnimalExternalizable o = (AnimalExternalizable) obj;
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

    public List<AnimalExternalizable> getChildren() {
        return children;
    }

    public void setChildren(List<AnimalExternalizable> children) {
        this.children = children;
    }

    public void addChild(AnimalExternalizable child) {
        this.children.add(child);
    }
}
