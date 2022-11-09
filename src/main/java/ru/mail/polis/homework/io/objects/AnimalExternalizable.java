package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age && Double.compare(that.weight, weight) == 0 && name.equals(that.name) && type == that.type && meal.equals(that.meal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, age, weight, meal);
    }

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
