package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private AnimalType animalType;
    private boolean male;
    private AnimalOwner animalOwner;
    private String name;
    private int age;

    public Animal(AnimalType animalType, boolean male, AnimalOwner animalOwner, String name, int age) {
        this.animalType = animalType;
        this.male = male;
        this.animalOwner = animalOwner;
        this.name = name;
        this.age = age;
    }

    public Animal() {
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public AnimalOwner getAnimalOwner() {
        return animalOwner;
    }

    public void setAnimalOwner(AnimalOwner animalOwner) {
        this.animalOwner = animalOwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalType, male, animalOwner, name, age);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Animal)) {
            return false;
        }

        Animal that = (Animal) obj;

        return animalType.equals(that.animalType) && male == that.male && animalOwner.equals(that.animalOwner)
                && name.equals(that.name) && age == that.age;
    }
}
