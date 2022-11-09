package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private String name;
    private int age;
    private double weight;
    private boolean isVegetarian;
    private AnimalType animalType;
    private WorkerExternalizable overseer;

    public AnimalExternalizable(String name, int age, double weight,
                                boolean isVegetarian, AnimalType animalType, WorkerExternalizable overseer) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.isVegetarian = isVegetarian;
        this.animalType = animalType;
        this.overseer = overseer;
    }

    public AnimalExternalizable() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public WorkerExternalizable getOverseer() {
        return overseer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return Objects.equals(name, animal.name) && age == animal.age && Double.compare(animal.weight, weight) == 0
                && isVegetarian == animal.isVegetarian && Objects.equals(animalType, animal.animalType)
                && Objects.equals(overseer, animal.overseer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, isVegetarian, animalType, overseer);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", isVegetarian=" + isVegetarian +
                ", animalType=" + animalType +
                ", overseer" + overseer +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (name != null) {
            out.writeByte(1);
            out.writeUTF(name);
        } else {
            out.writeByte(0);
        }
        out.writeInt(age);
        out.writeDouble(weight);
        out.writeBoolean(isVegetarian);
        if (animalType != null) {
            out.writeByte(1);
            out.writeUTF(animalType.name());
        } else {
            out.writeByte(0);
        }
        out.writeObject(overseer);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        if (in.readByte() == 1) {
            name = in.readUTF();
        }
        age = in.readInt();
        weight = in.readDouble();
        isVegetarian = in.readBoolean();
        if (in.readByte() == 1) {
            animalType = AnimalType.valueOf(in.readUTF());
        }
        overseer = (WorkerExternalizable) in.readObject();
    }
}
