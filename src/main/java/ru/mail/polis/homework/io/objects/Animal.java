package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */

public class Animal implements Serializable {
    private AnimalsType type;
    private String name;
    private List<String> food;
    private int speed;
    private int health;
    private boolean orientation;
    private Mind mind;

    public Animal() {

    }

    public Animal(AnimalsType type, String name, List<String> food, int speed, int health, boolean orientation, Mind mind) {
        this.type = type;
        this.name = name;
        this.food = food;
        this.speed = speed;
        this.health = health;
        this.orientation = orientation;
        this.mind = mind;
    }

    public AnimalsType getType() {
        return type;
    }

    public void setType(AnimalsType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFood() {
        return food;
    }

    public void setFood(List<String> food) {
        this.food = food;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean getOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public Mind getMind() {
        return mind;
    }

    public void setMind(Mind mind) {
        this.mind = mind;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", food=" + food +
                ", speed=" + speed +
                ", health=" + health +
                ", attack=" + orientation +
                ", mind=" + mind +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return getSpeed() == animal.getSpeed() &&
                getHealth() == animal.getHealth() &&
                getOrientation() == animal.getOrientation() &&
                getType() == animal.getType() &&
                Objects.equals(getName(), animal.getName()) &&
                Objects.equals(getFood(), animal.getFood()) &&
                Objects.equals(getMind(), animal.getMind());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getName(), getFood(), getSpeed(), getHealth(), getOrientation(), getMind());
    }
}
