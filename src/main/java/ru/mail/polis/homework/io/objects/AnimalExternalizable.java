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

    public AnimalExternalizable() {}

    public AnimalExternalizable(Animal animal) {
        this(animal.getName(),
                animal.getWeight(),
                animal.getParents(),
                animal.getGenericOfRelatives(),
                animal.getColor());
    }

    public AnimalExternalizable(String name, double weight, Parents parents, List<Parents> genericOfRelatives, Colour colour) {
        this.name = name;
        this.weight = weight;
        if(weight < 50) {
            this.weight = 50;
        }
        this.parents = new Parents(parents);
        this.genericOfRelatives = new ArrayList<>();
        for(Parents i : genericOfRelatives) {
            this.genericOfRelatives.add(new Parents(i));
        }
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

        out.writeUTF(parents.getMother());
        out.writeUTF(parents.getFather());

        out.writeInt(genericOfRelatives.size());
        for(Parents i : genericOfRelatives) {
            out.writeUTF(i.getMother());
            out.writeUTF(i.getFather());
        }
        out.writeObject(colour);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        weight = in.readDouble();

        if(weight < 50) {
            weight = 50;
        }
        parents = new Parents(in.readUTF(), in.readUTF());
        int n = in.readInt();
        genericOfRelatives = new ArrayList<>(n);
        for(int i = 0; i < n; ++i) {
            genericOfRelatives.add(new Parents(in.readUTF(), in.readUTF()));
        }
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