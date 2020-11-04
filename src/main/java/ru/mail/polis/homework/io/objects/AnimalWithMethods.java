package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */



public class AnimalWithMethods implements Serializable {
    private String name;
    private double weight;
    private Parents parents;
    private List<Parents> genericOfRelatives;
    private Colour colour;

    AnimalWithMethods() {
    }

    public AnimalWithMethods(String name, double weight, Parents parents, List<Parents> genericOfRelatives, Colour colour) {
        this.name = name;
        this.weight = weight;
        if(weight < 50) {
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
        AnimalWithMethods animal = (AnimalWithMethods) obj;
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

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeDouble(weight);
        out.writeObject(parents);
        out.writeObject(genericOfRelatives);
        out.writeObject(colour);
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        weight = in.readDouble();
        if(weight < 50) {
            weight = 50;
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
