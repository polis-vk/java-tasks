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
public class AnimalExternalizable implements Externalizable {
    private Animal.AnimalClassification animalClassification;
    private String name;
    private int age;
    private int weight;
    private boolean isPredator;
    private List<String> areas = new ArrayList<>();

    public enum AnimalClassification {
        CRUSTACEANS,
        ARACHNIDS,
        INSECTS,
        MAMMALS,
        BIRDS,
        REPTILES,
        AMPHIBIANS,
        FISH
    }

    public AnimalExternalizable() {

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(animalClassification);
        out.writeUTF(name);
        out.writeInt(age);
        out.writeInt(weight);
        out.writeBoolean(isPredator);
        out.writeObject(areas);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        animalClassification = (Animal.AnimalClassification) in.readObject();
        name = in.readUTF();
        age = in.readInt();
        weight = in.readInt();
        isPredator = in.readBoolean();
        areas = (List<String>) in.readObject();
    }

    public AnimalExternalizable(Animal.AnimalClassification animalClassification, String name, int age, int weight,
                                boolean isPredator, List<String> areas) {
        this.animalClassification = animalClassification;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.isPredator = isPredator;
        this.areas = areas;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AnimalExternalizable animal = (AnimalExternalizable) obj;
        return animalClassification == animal.animalClassification &&
                name.equals(animal.name) &&
                age == animal.age &&
                weight == animal.weight &&
                isPredator == animal.isPredator &&
                Objects.equals(areas, animal.areas);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "animalClassification = " + animalClassification +
                ", name = "  + name +
                ", age = " + age +
                ", weight = " + weight +
                ", isPredator = " + isPredator +
                ", areas = " + areas +
                '}';
    }
}
