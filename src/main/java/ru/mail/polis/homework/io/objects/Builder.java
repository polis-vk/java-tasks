package ru.mail.polis.homework.io.objects;

import java.util.List;

public class Builder {
    protected String name;
    protected Breeds breed;
    protected int age;
    protected List<Eat> eat;
    protected boolean inWild;
    protected String location;
    protected Object mother;
    protected Object father;

    public Animal buildAnimal() {
        return new Animal(this);
    }

    public AnimalExternalizable buildAnimalExternalizable() {
        return new AnimalExternalizable(this);
    }

    public AnimalWithMethods buildAnimalWithMethods() {
        return new AnimalWithMethods(this);
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

    public Builder setMother(Object mother) {
        this.mother = mother;
        return this;
    }

    public Builder setFather(Object father) {
        this.father = father;
        return this;
    }
}