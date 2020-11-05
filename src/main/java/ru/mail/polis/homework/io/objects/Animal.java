package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
enum Eat {
    MEAT, HAY, MILK, CARROT, FISH
}

enum Breeds {
    LION, HORSE
}

public class Animal implements Serializable {
    protected String name;
    protected Breeds breed;
    protected int age;
    protected List<Eat> eat;
    protected boolean inWild;
    protected String location;
    protected Animal mother;
    protected Animal father;

    public Animal() {

    }

    public Animal(Builder builder) {
        this.name = builder.name;
        this.breed = builder.breed;
        this.age = builder.age;
        this.eat = builder.eat;
        this.inWild = builder.inWild;
        this.location = builder.location;
        this.mother = builder.mother;
        this.father = builder.father;
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

    public Animal getMother() {
        return mother;
    }

    public Animal getFather() {
        return father;
    }

    public static class Builder {
        protected String name;
        protected Breeds breed;
        protected int age;
        protected List<Eat> eat;
        protected boolean inWild;
        protected String location;
        protected Animal mother;
        protected Animal father;

        public Animal build() {
            return new Animal(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBreed(Breeds breed) {
            this.breed = breed;
            return this;
        }

        public Builder setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException();
            }
            this.age = age;
            return this;
        }

        public Builder setEat(List<Eat> eat) {
            this.eat = eat;
            return this;
        }

        public Builder setInWild(boolean inWild) {
            this.inWild = inWild;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setMother(Animal mother) {
            this.mother = mother;
            return this;
        }

        public Builder setFather(Animal father) {
            this.father = father;
            return this;
        }
    }
}
