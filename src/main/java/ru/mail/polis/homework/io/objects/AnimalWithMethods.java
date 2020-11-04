package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {

    private int age;
    private String name;
    private Habitat habitat;
    private List<String> food;
    private boolean sexIsMale;
    private double height;
    private Heart heart;

    public enum Habitat {
        WATER,
        LAND,
        AIR
    }

    public AnimalWithMethods(int age, String name, Habitat habitat, List<String> food,
                  boolean gender, double height, Heart heart) {
        this.age = age;
        this.name = name;
        this.habitat = habitat;
        this.food = food;
        this.sexIsMale = gender;
        this.height = height;
        this.heart = heart;
    }

    public AnimalWithMethods() {

    }

    public void myWriteObject(ObjectOutputStream oos) throws IOException {
        oos.writeInt(age);
        oos.writeUTF(name);
        oos.writeObject(habitat);
        oos.writeObject(food);
        oos.writeBoolean(sexIsMale);
        oos.writeDouble(height);
        oos.writeObject(heart);
    }

    public void myReadObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        age = ois.readInt();
        name = ois.readUTF();
        habitat = (Habitat) ois.readObject();
        food = (List<String>) ois.readObject();
        sexIsMale = ois.readBoolean();
        height = ois.readDouble();
        heart = (Heart) ois.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return age == that.age &&
                sexIsMale == that.sexIsMale &&
                Double.compare(that.height, height) == 0 &&
                Objects.equals(name, that.name) &&
                habitat == that.habitat &&
                Objects.equals(food, that.food) &&
                Objects.equals(heart, that.heart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, habitat, food, sexIsMale, height, heart);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", habitat=" + habitat +
                ", food=" + food +
                ", sexIsMale=" + sexIsMale +
                ", height=" + height +
                ", heart=" + heart +
                '}';
    }
}
