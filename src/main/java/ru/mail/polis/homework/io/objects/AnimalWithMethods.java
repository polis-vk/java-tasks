package ru.mail.polis.homework.io.objects;


import com.sun.org.apache.xml.internal.serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public AnimalWithMethods() {

    }
    public AnimalWithMethods(String name, int age, double weight, CharacteristicWithMethods characteristic, List<String> nameChildren) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.characteristic = characteristic;
        this.nameChildren = new ArrayList<>(nameChildren);
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeDouble(weight);
        out.writeObject(characteristic);
        out.writeObject(nameChildren);
    }


    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        weight = in.readDouble();
        characteristic = (CharacteristicWithMethods) in.readObject();
        nameChildren = (List<String>)in.readObject();
    }

    @Override
    public String toString() {
        return "Animal{ " + "name=" + name +
                ", age=" + age +
                ", weight=" + weight +
                ", characteristic=" + characteristic +
                ", nameChildren=" + nameChildren + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AnimalWithMethods guest = (AnimalWithMethods) obj;
        return  name.equals(guest.getName()) &&
                age == guest.getAge() &&
                weight == guest.getWeight() &&
                characteristic.equals(guest.getCharacteristic()) &&
                nameChildren.equals(guest.getNameChildren());

    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public CharacteristicWithMethods getCharacteristic() {
        return characteristic;
    }

    public List<String> getNameChildren() {
        return nameChildren;
    }
}
