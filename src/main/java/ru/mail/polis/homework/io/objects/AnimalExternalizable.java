package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {

    private double weight;
    private int age;
    private String name;
    private Characteristic characteristic;
    private List<String> nameChildren;
    private AnimalInfo animalInfo;

    public AnimalExternalizable() {

    }

    public AnimalExternalizable(String name, int age, double weight, Characteristic characteristic, List<String> nameChildren, AnimalInfo animalInfo) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.characteristic = characteristic;
        this.nameChildren = new ArrayList<>(nameChildren);
        this.animalInfo = animalInfo;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeDouble(weight);
        out.writeObject(characteristic);
        out.writeObject(nameChildren);
        out.writeObject(animalInfo);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        weight = in.readDouble();
        characteristic = (Characteristic) in.readObject();
        nameChildren = (List<String>)in.readObject();
        animalInfo = (AnimalInfo)in.readObject();
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "weight=" + weight +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", characteristic=" + characteristic +
                ", nameChildren=" + nameChildren +
                ", animalInfo=" + animalInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return Double.compare(that.weight, weight) == 0 &&
                age == that.age &&
                Objects.equals(name, that.name) &&
                characteristic == that.characteristic &&
                Objects.equals(nameChildren, that.nameChildren) &&
                Objects.equals(animalInfo, that.animalInfo);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public List<String> getNameChildren() {
        return nameChildren;
    }

    public void setNameChildren(List<String> nameChildren) {
        this.nameChildren = nameChildren;
    }

    public AnimalInfo getAnimalInfo() {
        return animalInfo;
    }

    public void setAnimalInfo(AnimalInfo animalInfo) {
        this.animalInfo = animalInfo;
    }
}
