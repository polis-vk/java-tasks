package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private final String name;
    private final int age;
    private final boolean isAggressive;
    private final boolean isInvertebrate;
    private final AnimalType animalType;
    private final GeneralInformation information;

    public Animal(String name, int age, boolean isAggressive, boolean isInvertebrate, AnimalType animalType, GeneralInformation information) {
        this.name = name;
        this.age = age;
        this.isAggressive = isAggressive;
        this.isInvertebrate = isInvertebrate;
        this.animalType = animalType;
        this.information = information;
    }

    public GeneralInformation getInformation() {
        return information;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public boolean isAggressive() {
        return isAggressive;
    }

    public boolean isInvertebrate() {
        return isInvertebrate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal anotherAnimal = (Animal) o;
        return Objects.equals(name, anotherAnimal.getName())
                && age == anotherAnimal.getAge()
                && isAggressive == anotherAnimal.isAggressive()
                && isInvertebrate == anotherAnimal.isInvertebrate()
                && Objects.equals(animalType, anotherAnimal.getAnimalType())
                && Objects.equals(information, anotherAnimal.getInformation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isAggressive, isInvertebrate, animalType, information);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name:" + name +
                ", age:" + age +
                ", isAggressive:" + isAggressive +
                ", isInvertebrate:" + isInvertebrate +
                ", animalType:" + animalType +
                ", information:" + information + '}';
    }
}
