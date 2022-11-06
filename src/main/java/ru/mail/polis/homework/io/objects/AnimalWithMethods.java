package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    public AnimalWithMethods(String name, AnimalType type, int age, double weight, Meal meal) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.weight = weight;
        this.meal = meal;
    }

    public String name;
    public AnimalType type;
    public int age;
    public double weight;

    public boolean isDangerous() {
        return age > 2 && weight > 40;
    }

    public Meal meal;

    private void writeObject(ObjectOutputStream o) {
        try {
            o.writeUTF(name);
            o.writeObject(type);
            o.writeInt(age);
            o.writeDouble(weight);
            o.writeObject(meal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void readObject(ObjectInputStream o) {
        try {
            name = (String) o.readObject();
            type = (AnimalType) o.readObject();
            age = (Integer) o.readObject();
            weight = (Double) o.readObject();
            meal = (Meal) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", age=" + age +
                ", weight=" + weight +
                ", meal=" + meal +
                '}';
    }
}
