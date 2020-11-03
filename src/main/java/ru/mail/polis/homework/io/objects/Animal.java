package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private AnimalKind animalKind = AnimalKind.UNKNOWN;
    private String name = "unknown";
    private int age;
    private int weight;
    private List<String> locationsList = new ArrayList<>();
    private Colour colour = Colour.UNKNOWN;

    Animal() {
    }

    void writeObject(ObjectOutputStream out) throws IOException {
        animalKind.writeObject(out);
        out.writeUTF(name);
        out.writeInt(age);
        out.writeInt(weight);
        colour.writeObject(out);
    }

    void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        animalKind = animalKind.readObject(in);
        name = in.readUTF();
        age = in.readInt();
        weight = in.readInt();
        colour = colour.readObject(in);
    }

    public static Builder newBuilder() {
        return new Animal().new Builder();
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalKind=" + animalKind +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", locationsList=" + locationsList +
                ", colour=" + colour +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                weight == animal.weight &&
                animalKind == animal.animalKind &&
                Objects.equals(name, animal.name) &&
                Objects.equals(locationsList, animal.locationsList) &&
                colour == animal.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalKind, name, age, weight, locationsList, colour);
    }

    public class Builder {

        private Builder() {
        }

        public Builder setAnimalKind(AnimalKind animalKind) {
            Animal.this.animalKind = animalKind;
            return this;
        }

        public Builder setName(String name) {
            Animal.this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            Animal.this.age = age;
            return this;
        }

        public Builder setWeight(int weight) {
            Animal.this.weight = weight;
            return this;
        }

        public Builder addLocations(String... locations) {
            Animal.this.locationsList.addAll(Arrays.asList(locations));
            return this;
        }

        public Builder setLocationList(List<String> locationList) {
            Animal.this.locationsList = locationList;
            return this;
        }

        public Builder setColour(Colour colour) {
            Animal.this.colour = colour;
            return this;
        }

        public Animal build() {
            return Animal.this;
        }

    }
}
