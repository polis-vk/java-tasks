package ru.mail.polis.homework.io.objects;


import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */

enum Habitat {
    WATER,
    LAND,
    AIR
}

public class Animal {
    private String name;
    private Habitat habitat;
    private int weight;
    private int age;
    private boolean isPredator;
    private List<String> food;

    public Animal(){

    }

    public Animal(String name, Habitat habitat, int weight, int age, boolean isPredator, List<String> food){
        this.name = name;
        this.habitat = habitat;
        this.weight = weight;
        this.age = age;
        this.isPredator = isPredator;
        this.food = food;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFood(List<String> food) {
        this.food = food;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public int getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public List<String> getFood() {
        return food;
    }

    public boolean isPredator() {
        return isPredator;
    }

    @Override
    public String toString(){
        return "Animal{" +
                "name='" + name + '\'' +
                ", habitat=" + habitat +
                ", weight=" + weight  +
                ", age=" + age +
                ", isPredator=" + isPredator +
                ", food=" + food +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHabitat(), getWeight(), getAge(), isPredator(), getFood());
    }
}
