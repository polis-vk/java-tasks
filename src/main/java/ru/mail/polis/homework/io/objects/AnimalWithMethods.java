package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods extends Animal implements Serializable {
    public AnimalWithMethods() {
    }

    public AnimalWithMethods(String name, boolean isPredator, AnimalType type, List<String> food, Habitat habitat, int speed) {
        super(name, isPredator, type, food, habitat, speed);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
    }
}
