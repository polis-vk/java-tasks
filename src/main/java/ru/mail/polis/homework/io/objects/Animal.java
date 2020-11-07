package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;


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
    private Parents parents = new Parents();

    public static Animal getRandom(Random random) {
        return newBuilder().getRandom(random);
    }

    public static Builder newBuilder() {
        return new Animal().new Builder();
    }

    public AnimalKind getAnimalKind() {
        return animalKind;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public List<String> getLocationsList() {
        return locationsList;
    }

    public Colour getColour() {
        return colour;
    }

    public Parents getParents() {
        return parents;
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
                ", parents=" + parents +
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
                colour == animal.colour &&
                Objects.equals(parents, animal.parents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalKind, name, age, weight, locationsList, colour, parents);
    }

    public class Builder {

        private Animal getRandom(Random random) {
            setAnimalKind(AnimalKind.getRandom(random));
            setName(Utils.getRandomString(random, 10));
            setAge(random.nextInt(99));
            setWeight(random.nextInt(500));
            for (int i = 0; i < random.nextInt(20); i++)
                addLocations(Utils.getRandomString(random, 12));
            setColour(Colour.getRandom(random));
            setParents(new Parents(random.nextBoolean(), random.nextBoolean()));
            return build();
        }

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

        public Builder setParents(Parents parents) {
            Animal.this.parents = parents;
            return this;
        }

        public Animal build() {
            return Animal.this;
        }

    }
}
