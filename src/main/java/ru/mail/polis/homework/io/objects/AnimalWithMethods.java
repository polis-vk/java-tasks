package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {

    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;

    private String name;
    private AnimalType type;
    private int age;
    private double weight;
    private MealWithMethods meal;

    public AnimalWithMethods(String name, AnimalType type, int age, double weight, MealWithMethods meal) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.weight = weight;
        this.meal = meal;
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
        AnimalWithMethods that = (AnimalWithMethods) o;
        return age == that.age && Double.compare(that.weight, weight) == 0
                && name.equals(that.name) && type == that.type && meal.equals(that.meal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, age, weight, meal);
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

    private void writeObject(ObjectOutputStream out) {
        try {
            writeUTF(out, name);
            writeObject(out, type);
            out.writeInt(age);
            out.writeDouble(weight);
            out.writeObject(meal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream in) {
        try {
            name = readUTF(in);
            type = (AnimalType) readObjectorNull(in);
            age = in.readInt();
            weight = in.readDouble();
            meal = (MealWithMethods) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeUTF(ObjectOutputStream out, String string) throws IOException {
        if (string == null) {
            out.write(NULL_VALUE);
        } else {
            out.write(NOT_NULL_VALUE);
            out.writeUTF(string);
        }
    }

    private void writeObject(ObjectOutputStream out, Object object) throws IOException {
        if (object == null) {
            out.write(NULL_VALUE);
        } else {
            out.write(NOT_NULL_VALUE);
            out.writeObject(object);
        }
    }

    private String readUTF(ObjectInputStream in) throws IOException {
        return in.readByte() == 1 ? in.readUTF() : null;
    }

    private Object readObjectorNull(ObjectInputStream in) throws IOException, ClassNotFoundException {
        return in.readByte() == 1 ? in.readObject() : null;
    }
}
