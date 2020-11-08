package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */

public class AnimalExternalizable implements Externalizable {
    private String name;
    private Breeds breed;
    private int age;
    private List<Eat> eat;
    private boolean inWild;
    private String location;
    private AnimalExternalizable mother;
    private AnimalExternalizable father;

    public AnimalExternalizable() { }

    public AnimalExternalizable(ru.mail.polis.homework.io.objects.Builder builder) {
        this.name = builder.name;
        this.breed = builder.breed;
        this.age = builder.age;
        this.eat = builder.eat;
        this.inWild = builder.inWild;
        this.location = builder.location;
        this.mother = (AnimalExternalizable) builder.mother;
        this.father = (AnimalExternalizable) builder.father;
    }

    public String getName() {
        return name;
    }

    public Breeds getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public List<Eat> getEat() {
        return eat;
    }

    public boolean getInWild() {
        return inWild;
    }

    public String getLocation() {
        return location;
    }

    public AnimalExternalizable getMother() {
        return mother;
    }

    public AnimalExternalizable getFather() {
        return father;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", breed=" + breed +
                ", age=" + age +
                ", eat=" + eat +
                ", inWild=" + inWild +
                ", location='" + location + '\'' +
                ", mother=" + mother +
                ", father=" + father +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
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

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
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
        mother = (AnimalExternalizable) in.readObject();
        father = (AnimalExternalizable) in.readObject();
    }

    public static class Builder extends ru.mail.polis.homework.io.objects.Builder { }
}
