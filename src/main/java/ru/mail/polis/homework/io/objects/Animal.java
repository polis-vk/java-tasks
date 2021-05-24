package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.*;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    private String name;
    private int age;
    private int energy;
    private PhylumOfAnimals phylumOfAnimals;
    private List<Food> foods;

    public Animal(String name, int age, PhylumOfAnimals phylumOfAnimals) {
        this.name = name;
        this.age = age;
        this.phylumOfAnimals = phylumOfAnimals;
        new Animal();
    }

    public Animal() {
        energy = 100;
        foods = new ArrayList<>();
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

    public void setPhylumOfAnimals(PhylumOfAnimals phylumOfAnimals) {
        this.phylumOfAnimals = phylumOfAnimals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return age == animal.age && Objects.equals(name, animal.name) && phylumOfAnimals == animal.phylumOfAnimals &&
                Objects.equals(foods, animal.foods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, phylumOfAnimals, foods);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phylumOfAnimals=" + phylumOfAnimals +
                ", food=" + foods +
                '}';
    }

    public PhylumOfAnimals getPhylumOfAnimals() {
        return phylumOfAnimals;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> food) {
        this.foods = food;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        if (energy > 100) {
            energy = 100;
        } else if (energy < 0) {
            energy = 0;
        }

        this.energy = energy;
    }

    public void eat(int countOfFood) {
        if (countOfFood < 0) {
            return;
        }

        int count  = Math.min(countOfFood, foods.size());
        for (int i = count - 1; i >= 0; i--) {
            energy += foods.get(i).getEnergy();
            foods.remove(i);
            if (energy >= 100) {
                energy = 100;
                return;
            }
        }
    }

    public void doSomething(int consumedEnergy) {
        if (consumedEnergy > energy) {
            System.out.println("I will die, if i do this!");
            return;
        }

        energy -= consumedEnergy;
        System.out.println("I am doing this now!");
    }

}
