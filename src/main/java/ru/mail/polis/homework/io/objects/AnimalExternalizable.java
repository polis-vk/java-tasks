package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {
    private String name;
    private int age;
    private double weight;
    private AnimalType type;
    private Person owner;
    private List<Food> nutrition;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, int age, double weight, AnimalType type, Person owner, List<Food> nutrition) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.type = type;
        this.owner = owner;
        this.nutrition = nutrition;
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) {
        try {
            objectOutput.writeUTF(name);
            objectOutput.writeInt(age);
            objectOutput.writeDouble(weight);
            objectOutput.writeObject(type);
            objectOutput.writeObject(owner);
            objectOutput.writeObject(nutrition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readExternal(ObjectInput objectInput) {
        try {
            this.name = objectInput.readUTF();
            this.age = objectInput.readInt();
            this.weight = objectInput.readDouble();
            this.type = (AnimalType) objectInput.readObject();
            this.owner = (Person) objectInput.readObject();
            this.nutrition = (List<Food>) objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", type=" + type +
                ", owner=" + owner +
                ", nutrition=" + nutrition +
                '}';
    }
}
