package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    public enum Gender {MALE, FEMALE, NEUTRAL, MAYBE_MALE, MAYBE_FEMALE, OTHER}

    private List<String> habitat;
    private String species;
    private short age;
    private Gender gender;
    private boolean realExistence;
    private List<Animal> children;

    public Animal(List<String> habitat, String species, short age, Gender gender, boolean realExistence) {
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        this.habitat = habitat;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.realExistence = realExistence;
        this.children = new ArrayList<>();
    }

    public Animal(ArrayList<String> habitat, String species, short age, Gender gender, boolean realExistence, ArrayList<Animal> children) {
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        this.habitat = habitat;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.realExistence = realExistence;
        this.children = children;
    }

    public List<String> getHabitat() {
        return habitat;
    }

    public void setHabitat(List<String> habitat) {
        this.habitat = habitat;
    }

    public void addHabitat(String habitat) {
        this.habitat.add(habitat);
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public void increaseAgeOn(short diff) {
        if (diff < 0) {
            throw new IllegalArgumentException();
        }
        this.age += diff;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isRealExistence() {
        return realExistence;
    }

    public void setRealExistence(boolean realExistence) {
        this.realExistence = realExistence;
    }

    public List<Animal> getChildren() {
        return children;
    }

    public void setChildren(List<Animal> children) {
        this.children = children;
    }

    public void addChild(Animal child) {
        this.children.add(child);
    }
}
