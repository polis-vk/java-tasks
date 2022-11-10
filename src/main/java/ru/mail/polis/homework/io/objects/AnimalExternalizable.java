package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private String name;
    private int age;
    private double weight;
    private boolean tail;
    private AnimalType type;
    private boolean predator;

    public AnimalExternalizable(String name, int age, double weight, boolean tail, AnimalType type, boolean predator) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.tail = tail;
        this.type = type;
        this.predator = predator;
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

    public boolean isTail() {
        return tail;
    }

    public AnimalType getType() {
        return type;
    }

    public boolean isPredator() {
        return predator;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", tail=" + tail +
                ", type=" + type +
                ", isPredator=" + predator +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalExternalizable externalizable = (AnimalExternalizable) o;
        return Objects.equals(name, externalizable.getName())
                && age == externalizable.getAge()
                && weight == externalizable.getWeight()
                && tail == externalizable.isTail()
                && Objects.equals(type, externalizable.getType())
                && predator == externalizable.isPredator();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (name != null){
            out.writeUTF(name);
        }
        out.writeInt(getAge());
        out.writeDouble(getWeight());
        out.writeBoolean(isTail());
        if (type != null){
            out.writeObject(getType());
        }
        out.writeBoolean(isPredator());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
        weight = in.readInt();
        tail = in.readBoolean();
        type = (AnimalType) in.readObject();
        predator = in.readBoolean();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, tail, type, predator);
    }
}
