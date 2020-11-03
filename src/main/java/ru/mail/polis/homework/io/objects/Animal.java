package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */

enum AnimalType {
    Bird,
    Fish,
    Mammal,
    Insect
}

public class Animal implements Serializable {
    private String name;
    private boolean isPredator;
    private AnimalType type;
    private List<String> food;
    private Habitat habitat;
    private int speed;

    public Animal() {
    }

    public Animal(String name, boolean isPredator, AnimalType type, List<String> food, Habitat habitat, int speed) {
        this.name = name;
        this.isPredator = isPredator;
        this.type = type;
        this.food = food;
        this.habitat = habitat;
        this.speed = speed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public void setFood(List<String> food) {
        this.food = food;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public boolean isPredator() {
        return isPredator;
    }

    public AnimalType getType() {
        return type;
    }

    public List<String> getFood() {
        return food;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", isPredator=" + isPredator +
                ", type=" + type +
                ", food=" + food +
                ", habitat=" + habitat +
                ", speed=" + speed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return isPredator() == animal.isPredator() &&
                animal.getSpeed() == getSpeed() &&
                getName().equals(animal.getName()) &&
                getType() == animal.getType() &&
                getFood().equals(animal.getFood()) &&
                getHabitat().equals(animal.getHabitat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), isPredator(), getType(), getFood(), getHabitat(), getSpeed());
    }
}
