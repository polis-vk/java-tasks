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
    private String name;
    private boolean isPredator;
    private AnimalType type;
    private List<String> food;
    private Habitat habitat;
    private int speed;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, boolean isPredator, AnimalType type, List<String> food, Habitat habitat, int speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.isPredator = isPredator;
        this.type = type;
        this.food = food;
        this.habitat = habitat;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public boolean isPredator() {
        return isPredator;
    }

    public AnimalType getType() {
        return type;
    }

    public List<String> getFood() {
        return food;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeBoolean(isPredator);
        out.writeObject(type);
        out.writeInt(food.size());
        for (String f : food) {
            out.writeUTF(f);
        }
        out.writeObject(habitat);
        out.writeInt(speed);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        isPredator = in.readBoolean();
        type = (AnimalType) in.readObject();
        int size = in.readInt();
        food = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            food.add(in.readUTF());
        }
        habitat = (Habitat) in.readObject();
        speed = in.readInt();
        if (speed <= 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", isPredator=" + isPredator +
                ", type=" + type +
                ", food=" + food +
                ", habitat=" + habitat +
                ", speed=" + speed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return isPredator() == that.isPredator() &&
                getSpeed() == that.getSpeed() &&
                Objects.equals(getName(), that.getName()) &&
                getType() == that.getType() &&
                Objects.equals(getFood(), that.getFood()) &&
                Objects.equals(getHabitat(), that.getHabitat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), isPredator(), getType(), getFood(), getHabitat(), getSpeed());
    }
}
