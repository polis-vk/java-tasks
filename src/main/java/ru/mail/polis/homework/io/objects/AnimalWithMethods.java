package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */

public class AnimalWithMethods extends Animal {
    public AnimalWithMethods() {

    }

    public AnimalWithMethods(Builder builder) {
        super(builder);
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
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

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
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
        public AnimalWithMethods build() {
            return new AnimalWithMethods(this);
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
        public Animal.Builder setFather(Animal father) {
            this.father = father;
            return this;
        }
    }
}
