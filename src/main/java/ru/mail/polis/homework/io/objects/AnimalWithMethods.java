package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods {
    private String name;
    private int age;
    private double weight;
    private AnimalType type;
    private Person owner;
    private List<Food> nutrition;

    public AnimalWithMethods() {
    }

    public AnimalWithMethods(String name, int age, double weight, AnimalType type, Person owner, List<Food> nutrition) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.type = type;
        this.owner = owner;
        this.nutrition = nutrition;
    }

    public void writeObject(ObjectOutputStream output) {
        try {
            output.writeUTF(name);
            output.writeInt(age);
            output.writeDouble(weight);
            output.writeObject(type);
            output.writeObject(owner);
            output.writeObject(nutrition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadObject(ObjectInputStream input) {
        try {
            this.name = input.readUTF();
            this.age = input.readInt();
            this.weight = input.readDouble();
            this.type = (AnimalType) input.readObject();
            this.owner = (Person) input.readObject();
            this.nutrition = (List<Food>) input.readObject();
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
