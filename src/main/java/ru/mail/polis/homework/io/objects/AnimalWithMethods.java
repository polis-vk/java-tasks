package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;
    private String name;
    private int age;
    private double weight;
    private boolean alive;
    private AnimalType type;
    private boolean isPet;
    private PlaceOfResidence placeOfResidence;

    public AnimalWithMethods(String name, int age, double weight, boolean alive, AnimalType type, boolean isPet, PlaceOfResidence placeOfResidence) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.alive = alive;
        this.type = type;
        this.isPet = isPet;
        this.placeOfResidence = placeOfResidence;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public boolean isPet() {
        return isPet;
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public PlaceOfResidence getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(PlaceOfResidence placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    private void writeObject(ObjectOutputStream output) throws IOException {
        if (name == null) {
            output.writeByte(NULL_VALUE);
        } else {
            output.writeByte(NOT_NULL_VALUE);
            output.writeUTF(name);
        }
        output.writeInt(age);
        output.writeDouble(weight);
        output.writeBoolean(alive);
        if (type == null) {
            output.writeByte(NULL_VALUE);
        } else {
            output.writeByte(NOT_NULL_VALUE);
            output.writeUTF(type.toString());
        }
        output.writeBoolean(isPet);
        if (placeOfResidence == null) {
            output.writeByte(NULL_VALUE);
        } else {
            String country = placeOfResidence.getCountry();
            if (country == null) {
                output.writeByte(NULL_VALUE);
            } else {
                output.writeByte(NOT_NULL_VALUE);
                output.writeUTF(country);
            }
            String terrain = placeOfResidence.getTerrain();
            if (terrain == null) {
                output.writeByte(NULL_VALUE);
            } else {
                output.writeByte(NOT_NULL_VALUE);
                output.writeUTF(terrain);
            }
        }
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        if (input.readByte() == NULL_VALUE) {
            name = null;
        } else {
            name = input.readUTF();
        }
        age = input.readInt();
        weight = input.readDouble();
        alive = input.readBoolean();
        if (input.readByte() == NULL_VALUE) {
            type = null;
        } else {
            type = AnimalType.valueOf(input.readUTF());
        }
        isPet = input.readBoolean();
        if (input.readByte() == NULL_VALUE) {
            placeOfResidence = null;
        } else {
            PlaceOfResidence placeOfResidence = new PlaceOfResidence();
            if (input.readByte() == NULL_VALUE) {
                placeOfResidence.setCountry(null);
            } else {
                placeOfResidence.setCountry(input.readUTF());
            }
            if (input.readByte() == NULL_VALUE) {
                placeOfResidence.setTerrain(null);
            } else {
                placeOfResidence.setTerrain(input.readUTF());
            }
            this.placeOfResidence = placeOfResidence;
        }
    }


    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", alive=" + alive +
                ", type=" + type +
                ", isPet=" + isPet +
                ", placeOfResidence=" + placeOfResidence +
                '}';
    }
}
