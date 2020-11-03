package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {

    private AnimalKind animalKind;
    private String name;
    private int age;
    private int weight;
    private List<String> locationsList = new ArrayList<>();
    private Colour colour;

    private AnimalWithMethods() {
    }

    public static Builder newBuilder() {
        return new AnimalWithMethods().new Builder();
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "animalKind=" + animalKind +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", locationsList=" + locationsList +
                ", colour=" + colour +
                '}';
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setAnimalKind(AnimalKind animalKind) {
            AnimalWithMethods.this.animalKind = animalKind;
            return this;
        }

        public Builder setName(String name) {
            AnimalWithMethods.this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            AnimalWithMethods.this.age = age;
            return this;
        }

        public Builder setWeight(int weight) {
            AnimalWithMethods.this.weight = weight;
            return this;
        }

        public Builder addLocations(String... locations) {
            AnimalWithMethods.this.locationsList.addAll(Arrays.asList(locations));
            return this;
        }

        public Builder setLocationList(List<String> locationList) {
            AnimalWithMethods.this.locationsList = locationList;
            return this;
        }

        public Builder setColour(Colour colour) {
            AnimalWithMethods.this.colour = colour;
            return this;
        }

        public AnimalWithMethods build() {
            return AnimalWithMethods.this;
        }

    }
}
