package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.List;
import java.util.Date;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
enum animalType {bird, mammals,fish, insects, reptiles, amphibians }
class Food {
    private foodType ftype;
    private double weight;

    public Food(double weight, foodType ftype) {
        this.ftype = ftype;
        this.weight = weight;
    }

    public foodType getFtype() {
        return ftype;
    }

    public double getWeight() {
        return weight;
    }

    enum foodType {fish, meat, grains, milk}
}

public class Animal implements Serializable {
    private animalType type;
    private String name;
    private int age;
    private boolean sex;
    private List<Date> dateOfVaccination;
    private Food fplan;

    public Animal(animalType type, String name, int age, boolean sex, List<Date> dateOfVaccination, Food fplan) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.dateOfVaccination = dateOfVaccination;
        this.fplan = fplan;
    }

    public animalType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean getSex() {
        return sex;
    }

    public Food getfplan() {
        return fplan;
    }

    public List<Date> getDateOfVaccination() {
        return dateOfVaccination;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "type='" + type + '\'' +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", dateOfVaccination=" + dateOfVaccination +
                ", fplan='" + fplan + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Animal animal = (Animal) obj;

        return type.equals(animal.type) &&
                name.equals(animal.name) &&
                age == animal.age &&
                sex == animal.sex &&
                dateOfVaccination.equals(animal.dateOfVaccination) &&
                fplan.equals(animal.fplan);
    }
}
