package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    private String name;
    private int age;
    private double weight;
    private AnimalType type;
    private Person owner;
    private List<Food> nutrition;

    public AnimalWithMethods(AnimalWithMethods animal) {
        this(animal.name, animal.age, animal.weight, animal.type, animal.owner, animal.nutrition);
    }

    public AnimalWithMethods(String name, int age, double weight, AnimalType type, Person owner, List<Food> nutrition) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.type = type;
        this.owner = owner;
        this.nutrition = nutrition;
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

    public AnimalType getType() {
        return type;
    }

    public Person getOwner() {
        return owner;
    }

    public List<Food> getNutrition() {
        return nutrition;
    }

    private void writeObject(ObjectOutputStream output) throws IOException {
            output.writeUTF(name);
            output.writeInt(age);
            output.writeDouble(weight);
            output.writeObject(type);
            output.writeObject(owner);
            output.writeObject(nutrition);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
            this.name = input.readUTF();
            this.age = input.readInt();
            this.weight = input.readDouble();
            this.type = (AnimalType) input.readObject();
            this.owner = (Person) input.readObject();
            this.nutrition = (List<Food>) input.readObject();
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
