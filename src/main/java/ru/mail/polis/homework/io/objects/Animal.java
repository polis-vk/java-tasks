package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private AnimalKind animalKind;
    private String name;
    private int age;
    private int weight;
    private List<String> locationsList = new ArrayList<>();
    private Colour colour;

    private Animal() {
    }

    public static Builder newBuilder() {
        return new Animal().new Builder();
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
