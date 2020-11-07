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

enum animalType {
    cat,
    dog,
    turtle,
    rabbit,
    fish,
    bird
}

public class Animal implements Serializable {
    private int age;
    private double weight;
    private boolean gender;
    private String name;
    private animalType kindOfAnimal;
    private List<String> food;
    private Colour animalColor;

    public Animal(int a, double w, boolean s, String n, animalType t, List<String> f, Colour ac) {
        this.age = a;
        this.weight = w;
        this.gender = s;
        this.name = n;
        this.kindOfAnimal = t;
        this.food = f;
        this.animalColor = ac;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", kind of animal='" + kindOfAnimal + '\'' +
                ", age=" + age + '\'' +
                ", gender=" + gender + '\'' +
                ", food=" + food +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal animal = (Animal) obj;
        return this.name.equals(animal.name) &&
                this.age == animal.age &&
                this.weight == animal.weight &&
                this.gender == animal.gender &&
                this.kindOfAnimal == animal.kindOfAnimal &&
                this.food.equals(animal.food) &&
                Objects.equals(this.animalColor, animal.animalColor);
    }


    public animalType getKindOfAnimal() {
        return kindOfAnimal;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public List<String> getFood() {
        return food;
    }

    public String getName() {
        return name;
    }
    public boolean getGender() {
        return gender;
    }

    public Colour getAnimalColor() {
        return animalColor;
    }
}
