package ru.mail.polis.homework.io.objects;


import com.sun.org.apache.xml.internal.serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {

    private double weight;
    private int age;
    private String name;
    private CharacteristicWithMethods characteristic;
    private List<String> nameChildren;
    private AnimalInfoWithMethods animalInfo;

    public AnimalWithMethods(String name, int age, double weight, CharacteristicWithMethods characteristic, List<String> nameChildren, AnimalInfoWithMethods animalInfo) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.characteristic = characteristic;
        this.nameChildren = new ArrayList<>(nameChildren);
        this.animalInfo = animalInfo;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeDouble(weight);
        out.writeObject(characteristic);
        out.writeObject(nameChildren);
        out.writeObject(animalInfo);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        weight = in.readDouble();
        characteristic = (CharacteristicWithMethods) in.readObject();
        nameChildren = (List<String>)in.readObject();
        animalInfo = (AnimalInfoWithMethods)in.readObject();
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
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
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return Double.compare(animal.weight, weight) == 0 &&
                age == animal.age &&
                Objects.equals(name, animal.name) &&
                characteristic == animal.characteristic &&
                Objects.equals(nameChildren, animal.nameChildren) &&
                Objects.equals(animalInfo, animal.animalInfo);
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

    public CharacteristicWithMethods getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(CharacteristicWithMethods characteristic) {
        this.characteristic = characteristic;
    }

    public List<String> getNameChildren() {
        return nameChildren;
    }

    public void setNameChildren(List<String> nameChildren) {
        this.nameChildren = nameChildren;
    }

    public AnimalInfoWithMethods getAnimalInfoWithMethods() {
        return animalInfo;
    }

    public void setAnimalInfo(AnimalInfoWithMethods animalInfo) {
        this.animalInfo = animalInfo;
    }
}
