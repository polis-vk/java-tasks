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
    private String name;
    private int age;
    private double weight;
    private boolean alive;
    private AnimalType type;
    private boolean isPet;
    private ResidencePlace residencePlace;

    public Animal(String name, int age, double weight, boolean alive, AnimalType type, boolean isPet, ResidencePlace residencePlace) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.alive = alive;
        this.type = type;
        this.isPet = isPet;
        this.residencePlace = residencePlace;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isAlive() {
        return alive;
    }

    public AnimalType getType() {
        return type;
    }

    public boolean isPet() {
        return isPet;
    }

    public ResidencePlace getResidencePlace() {
        return residencePlace;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public void setResidencePlace(ResidencePlace residencePlace) {
        this.residencePlace = residencePlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Animal animal = (Animal) o;
        return age == animal.age
                && Double.compare(animal.weight, weight) == 0
                && alive == animal.alive && isPet == animal.isPet
                && Objects.equals(name, animal.name)
                && type == animal.type
                && Objects.equals(residencePlace, animal.residencePlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, alive, type, isPet, residencePlace);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", alive=" + alive +
                ", type=" + type +
                ", isPet=" + isPet +
                ", residencePlace=" + residencePlace +
                '}';
    }
}
