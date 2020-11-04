package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods extends Animal implements Serializable {

    public AnimalWithMethods() {

    }

    public AnimalWithMethods(AnimalsType type, String name, List<String> food, int speed, int health, boolean orientation, Mind mind) {
        super(type, name, food, speed, health, orientation, mind);
    }

    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(getType());
        objectOutput.writeUTF(getName());
        objectOutput.writeInt(getFood().size());
        for (String f : getFood()) {
            objectOutput.writeUTF(f);
        }
        objectOutput.writeInt(getSpeed());
        objectOutput.writeInt(getHealth());
        objectOutput.writeBoolean(getOrientation());
        objectOutput.writeObject(getMind());
    }

    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        setType((AnimalsType) objectInput.readObject());
        setName(objectInput.readUTF());
        int size = objectInput.readInt();
        List<String> food = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            food.add(objectInput.readUTF());
        }
        setFood(food);
        setSpeed(objectInput.readInt());
        setHealth(objectInput.readInt());
        setOrientation(objectInput.readBoolean());
        setMind((Mind) objectInput.readObject());
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{}";
    }


}
