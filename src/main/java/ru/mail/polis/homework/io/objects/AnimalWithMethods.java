package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods extends Animal {

    public AnimalWithMethods(String name, boolean isPredator, AnimalType type, List<String> food, Habitat habitat, int speed) {
        super(name, isPredator, type, food, habitat, speed);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(getName());
        out.writeBoolean(isPredator());
        out.writeObject(getType());
        out.writeInt(getFood().size());
        for (String food : getFood()) {
            out.writeUTF(food);
        }
        out.writeObject(getHabitat());
        out.writeInt(getSpeed());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        setName(in.readUTF());
        setPredator(in.readBoolean());
        setType((AnimalType) in.readObject());
        int size = in.readInt();
        List<String> food = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            food.add(in.readUTF());
        }
        setFood(food);
        setHabitat((Habitat) in.readObject());
        setSpeed(in.readInt());
    }
}
