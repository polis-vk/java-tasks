package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */


public class AnimalExternalizable implements Externalizable {
    private String name;
    private double weight;
    private Parents parents;
    private List<Parents> genericOfRelatives;
    private Colour colour;

    AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, double weight, Parents parents, ArrayList<Parents> genericOfRelatives, Colour colour) {
        this.name = name;
        if (weight > 50) {
            this.weight = weight;
        } else {
            this.weight = 50;
        }
        this.parents = parents;
        this.genericOfRelatives = new ArrayList<>(genericOfRelatives);
        this.colour = colour;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        AnimalExternalizable animal = (AnimalExternalizable) obj;
        return name.equals(animal.name) &&
                weight == animal.weight &&
                parents.equals(animal.parents) &&
                genericOfRelatives.equals(animal.genericOfRelatives) &&
                colour.equals(animal.colour);

    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", parents='" + parents.toString() + '\'' +
                ", genericOfRelatives='" + genericOfRelatives.toString() + '\'' +
                ", color='" + colour.toString() + '\'' +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeDouble(weight);
        out.writeObject(parents);
        out.writeObject(genericOfRelatives);
        out.writeObject(colour);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        weight = in.readDouble();
        if (weight <= 50) {
            this.weight = 50;
        }
        parents = (Parents) in.readObject();
        genericOfRelatives = (List<Parents>) in.readObject();
        colour = (Colour) in.readObject();
    }

    public Colour getColor() {
        return colour;
    }

    public List<Parents> getGenericOfRelatives() {
        return genericOfRelatives;
    }

    public Parents getParents() {
        return parents;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}
