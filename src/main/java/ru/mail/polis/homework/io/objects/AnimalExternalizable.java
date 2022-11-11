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

    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;

    private String name;
    private AnimalType type;
    private int age;
    private double weight;
    private MealExternalizable meal;

    public AnimalExternalizable(String name, AnimalType type, int age, double weight, MealExternalizable meal) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.weight = weight;
        this.meal = meal;
    }

    public AnimalExternalizable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age && Double.compare(that.weight, weight) == 0
                && name.equals(that.name) && type == that.type && meal.equals(that.meal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, age, weight, meal);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        writeUTF(out, name);
        writeUTF(out, type.toString());
        out.writeInt(age);
        out.writeDouble(weight);
        out.writeObject(meal);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = readUTF(in);
        String animalType = readUTF(in);
        type = animalType != null ? AnimalType.valueOf(animalType) : null;
        age = in.readInt();
        weight = in.readDouble();
        meal = (MealExternalizable) in.readObject();
    }

    private void writeUTF(ObjectOutput out, String string) throws IOException {
        if (string == null) {
            out.write(NULL_VALUE);
        } else {
            out.write(NOT_NULL_VALUE);
            out.writeUTF(string);
        }
    }

    private String readUTF(ObjectInput in) throws IOException {
        return in.readByte() == 1 ? in.readUTF() : null;
    }
}
