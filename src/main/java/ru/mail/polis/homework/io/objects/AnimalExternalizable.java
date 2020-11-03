package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable extends Animal implements Externalizable {
    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, boolean isPredator, AnimalType type, List<String> food, Habitat habitat, int speed) {
        super(name, isPredator, type, food, habitat, speed);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(getName());
        out.writeBoolean(isPredator());
        out.writeObject(getType());
        out.writeObject(getFood());
        out.writeObject(getHabitat());
        out.writeInt(getSpeed());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName(in.readUTF());
        setPredator(in.readBoolean());
        setType((AnimalType) in.readObject());
        setFood((List<String>) in.readObject());
        setHabitat((Habitat) in.readObject());
        setSpeed(in.readInt());
    }
}
