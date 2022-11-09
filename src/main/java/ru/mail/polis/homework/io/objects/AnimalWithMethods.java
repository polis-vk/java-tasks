package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private String name;
    private int age;
    private double weight;
    private boolean isVegetarian;
    private AnimalType animalType;
    private WorkerWithMethods overseer;

    public AnimalWithMethods(String name, int age, double weight,
                                boolean isVegetarian, AnimalType animalType, WorkerWithMethods overseer) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.isVegetarian = isVegetarian;
        this.animalType = animalType;
        this.overseer = overseer;
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

    public WorkerWithMethods getOverseer() {
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
        AnimalWithMethods animal = (AnimalWithMethods) o;
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
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", isVegetarian=" + isVegetarian +
                ", animalType=" + animalType +
                ", overseer" + overseer +
                '}';
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
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

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        if (in.readByte() == 1) {
            name = in.readUTF();
        }
        age = in.readInt();
        weight = in.readDouble();
        isVegetarian = in.readBoolean();
        if (in.readByte() == 1) {
            animalType = AnimalType.valueOf(in.readUTF());
        }
        overseer = (WorkerWithMethods) in.readObject();
    }
}
