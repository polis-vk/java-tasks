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

    public AnimalWithMethods(ru.mail.polis.homework.io.objects.Builder builder) {
        this.name = builder.name;
        this.breed = builder.breed;
        this.age = builder.age;
        this.eat = builder.eat;
        this.inWild = builder.inWild;
        this.location = builder.location;
        this.mother = (AnimalWithMethods) builder.mother;
        this.father = (AnimalWithMethods) builder.father;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        if (name != null) {
            out.writeBoolean(true);
            out.writeUTF(name);
        } else {
            out.writeBoolean(false);
        }
        out.writeObject(breed);
        out.writeInt(age);
        if (eat != null) {
            out.writeInt(eat.size());
            for (Eat e : eat) {
                out.writeObject(e);
            }
        } else {
            out.writeInt(0);
        }
        out.writeBoolean(inWild);
        if (location != null) {
            out.writeBoolean(true);
            out.writeUTF(location);
        } else {
            out.writeBoolean(false);
        }
        out.writeObject(mother);
        out.writeObject(father);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readBoolean() ? in.readUTF() : null;
        breed = (Breeds) in.readObject();
        age = in.readInt();
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        int eatSize = in.readInt();
        List<Eat> incomingEat = new ArrayList<>(eatSize);
        for (int eatItem = 0; eatItem < eatSize; eatItem++) {
            incomingEat.add((Eat) in.readObject());
        }
        eat = eatSize != 0 ? incomingEat : null;
        inWild = in.readBoolean();
        location = in.readBoolean() ? in.readUTF() : null;
        mother = (AnimalWithMethods) in.readObject();
        father = (AnimalWithMethods) in.readObject();
    }

    public static class Builder extends ru.mail.polis.homework.io.objects.Builder { }
}
