package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private String name;
    private int age;
    private boolean isWild;
    private boolean isAquaticAnimal;
    private AnimalType animalType;
    private LocalDate dateOfBirth;
    private Owner owner;

    public Animal(String name, int age, boolean isWild, boolean isAquaticAnimal, AnimalType animalType, LocalDate dateOfBirth) {
        this.name = name;
        this.age = age;
        this.animalType = animalType;
        this.isWild = isWild;
        this.isAquaticAnimal = isAquaticAnimal;
        this.dateOfBirth = dateOfBirth;
        this.owner = null;
    }

    public String getName() {
        return name;
    }

    public boolean isWild() {
        return isWild;
    }

    public int getAge() {
        return age;
    }

    public boolean isAquaticAnimal() {
        return isAquaticAnimal;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age && isWild == animal.isWild && isAquaticAnimal == animal.isAquaticAnimal
                && Objects.equals(name, animal.name) && animalType == animal.animalType
                && Objects.equals(dateOfBirth, animal.dateOfBirth) && Objects.equals(owner, animal.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isWild, isAquaticAnimal, animalType, dateOfBirth, owner);
    }


}
