package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private String name;
    private int age;
    private boolean friendly;
    private boolean warmBlooded;
    private AnimalType animalType;
    private Population population;

    public Animal(String name, int age, boolean friendly, boolean warmBlooded, AnimalType animalType, Population population) {
        this.name = name;
        this.age = age;
        this.friendly = friendly;
        this.warmBlooded = warmBlooded;
        this.animalType = animalType;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public boolean isWarmBlooded() {
        return warmBlooded;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public Population getPopulation() {
        return population;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWarmBlooded(boolean warmBlooded) {
        this.warmBlooded = warmBlooded;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return animal.getName().equals(getName()) && animal.getAge() == getAge()
                && animal.isFriendly() == isFriendly() && animal.isWarmBlooded() == isWarmBlooded()
                && animal.getAnimalType() == getAnimalType() && animal.getPopulation() == getPopulation();
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", friendly=" + friendly + '\'' +
                ", warm-blooded=" + warmBlooded + '\'' +
                ", population=" + population + '\'' +
                ", animalType=" + animalType +
                '}';
    }

}
