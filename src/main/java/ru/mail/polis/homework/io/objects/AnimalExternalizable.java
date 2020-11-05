package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {

    private double weight;
    private int age;
    private String name;
    private CharacteristicExternalizable characteristic;
    private List<String> nameChildren;

    public AnimalExternalizable() {

    }
    public AnimalExternalizable(String name, int age, double weight, CharacteristicExternalizable characteristic, List<String> nameChildren) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.characteristic = characteristic;
        this.nameChildren = new ArrayList<>(nameChildren);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
        out.writeDouble(weight);
        out.writeObject(characteristic);
        out.writeObject(nameChildren);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        weight = in.readDouble();
        characteristic = (CharacteristicExternalizable) in.readObject();
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

        AnimalExternalizable guest = (AnimalExternalizable) obj;
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

    public CharacteristicExternalizable getCharacteristic() {
        return characteristic;
    }

    public List<String> getNameChildren() {
        return nameChildren;
    }
}
