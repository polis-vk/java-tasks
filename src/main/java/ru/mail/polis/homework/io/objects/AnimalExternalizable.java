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

    public AnimalExternalizable(AnimalExternalizable animal) {
        this(animal.name, animal.age, animal.weight, animal.type, animal.owner, animal.nutrition);
    }

    public AnimalExternalizable(String name, int age, double weight, AnimalType type, Person owner, List<Food> nutrition) {
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

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeUTF(name);
            objectOutput.writeInt(age);
            objectOutput.writeDouble(weight);
            objectOutput.writeObject(type);
            objectOutput.writeObject(owner);
            objectOutput.writeObject(nutrition);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            this.name = objectInput.readUTF();
            this.age = objectInput.readInt();
            this.weight = objectInput.readDouble();
            this.type = (AnimalType) objectInput.readObject();
            this.owner = (Person) objectInput.readObject();
            this.nutrition = (List<Food>) objectInput.readObject();
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
