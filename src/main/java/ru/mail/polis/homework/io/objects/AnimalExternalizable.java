package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */

public class AnimalExternalizable extends Animal implements Externalizable {
    public AnimalExternalizable() {

    }

    public AnimalExternalizable(Builder builder) {
        super(builder);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeObject(breed);
        out.writeInt(age);
        out.writeInt(eat.size());
        for (Eat e : eat) {
            out.writeObject(e);
        }
        out.writeBoolean(inWild);
        out.writeUTF(location);
        out.writeObject(mother);
        out.writeObject(father);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
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
        eat = incomingEat;
        inWild = in.readBoolean();
        location = in.readUTF();
        mother = (Animal) in.readObject();
        father = (Animal) in.readObject();
    }

    public static class Builder extends Animal.Builder {
        @Override
        public AnimalExternalizable build() {
            return new AnimalExternalizable(this);
        }

        @Override
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder setBreed(Breeds breed) {
            this.breed = breed;
            return this;
        }

        @Override
        public Builder setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException();
            }
            this.age = age;
            return this;
        }

        @Override
        public Builder setEat(List<Eat> eat) {
            this.eat = eat;
            return this;
        }

        @Override
        public Builder setInWild(boolean inWild) {
            this.inWild = inWild;
            return this;
        }

        @Override
        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        @Override
        public Builder setMother(Animal mother) {
            this.mother = mother;
            return this;
        }

        @Override
        public Builder setFather(Animal father) {
            this.father = father;
            return this;
        }
    }
}
