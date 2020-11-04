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
    private AnimalsType type;
    private String name;
    private List<String> food;
    private int speed;
    private int health;
    private boolean orientation;
    private Mind mind;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(AnimalsType type, String name, List<String> food, int speed, int health, boolean orientation, Mind mind) {
        this.type = type;
        this.name = name;
        this.food = food;
        this.speed = speed;
        this.health = health;
        this.orientation = orientation;
        this.mind = mind;
    }

    public AnimalsType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<String> getFood() {
        return food;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public boolean getOrientation() {
        return orientation;
    }

    public Mind getMind() {
        return mind;
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(type);
        objectOutput.writeUTF(name);
        objectOutput.writeInt(food.size());
        /*for (String f : food) {
            objectOutput.writeUTF(f);
        }*/
        for (int i = 0; i < food.size(); i++) {
            objectOutput.writeUTF(food.get(i));
        }
        objectOutput.writeInt(speed);
        objectOutput.writeInt(health);
        objectOutput.writeBoolean(orientation);
        objectOutput.writeObject(mind);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        type = (AnimalsType) objectInput.readObject();
        name = objectInput.readUTF();
        int size = objectInput.readInt();
        food = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            food.add(objectInput.readUTF());
        }
        speed = objectInput.readInt();
        health = objectInput.readInt();
        orientation = objectInput.readBoolean();
        mind = (Mind) objectInput.readObject();
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", food=" + food +
                ", speed=" + speed +
                ", health=" + health +
                ", attack=" + orientation +
                ", mind=" + mind +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return getSpeed() == that.getSpeed() &&
                getHealth() == that.getHealth() &&
                getOrientation() == that.getOrientation() &&
                getType() == that.getType() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getFood(), that.getFood()) &&
                Objects.equals(getMind(), that.getMind());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getName(), getFood(), getSpeed(), getHealth(), getOrientation(), getMind());
    }
}
