package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {
    public AnimalExternalizable(String name, AnimalType type, int age, double weight, Meal meal) {
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(type.toString());
        out.writeInt(age);
        out.writeDouble(weight);
        out.writeObject(meal);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        type = (AnimalType) in.readObject();
        age = (Integer) in.readObject();
        weight = (Double) in.readObject();
        meal = (Meal) in.readObject();
    }
}
