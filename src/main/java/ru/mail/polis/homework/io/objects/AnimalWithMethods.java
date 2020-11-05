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

    public AnimalWithMethods() {
    }

    public AnimalWithMethods(String name, BioTag tag, int age, List<String> habitat, Animal.Size size, double weight, boolean canFly) {
        super(name, tag, age, habitat, size, weight, canFly);
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(getName());
        out.writeLong(getTag().getId());
        out.writeUTF(getTag().getSeries());
        out.writeInt(getAge());

        List<String> temp = getHabitat();
        out.writeInt(temp.size());
        for (String habitat : temp) {
            out.writeUTF(habitat);
        }

        out.writeObject(getSize());
        out.writeDouble(getWeight());
        out.writeBoolean(isCanFly());
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        setName(in.readUTF());
        setTag(new BioTag(in.readLong(), in.readUTF()));
        setAge(in.readInt());

        int length = in.readInt();
        List<String> temp = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            temp.add(in.readUTF());
        }
        setHabitat(temp);

        setSize((Size) in.readObject());
        setWeight(in.readDouble());
        setCanFly(in.readBoolean());
    }

}
