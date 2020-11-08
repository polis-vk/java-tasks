package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    protected String name;
    protected Breeds breed;
    protected int age;
    protected List<Eat> eat;
    protected boolean inWild;
    protected String location;
    protected Animal mother;
    protected Animal father;

    public Animal() { }

    public Animal(ru.mail.polis.homework.io.objects.Builder builder) {
        this.name = builder.name;
        this.breed = builder.breed;
        this.age = builder.age;
        this.eat = builder.eat;
        this.inWild = builder.inWild;
        this.location = builder.location;
        this.mother = (Animal) builder.mother;
        this.father = (Animal) builder.father;
    }

    public String getName() {
        return name;
    }

    public Breeds getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public List<Eat> getEat() {
        return eat;
    }

    public boolean getInWild() {
        return inWild;
    }

    public String getLocation() {
        return location;
    }

    public Animal getMother() {
        return mother;
    }

    public Animal getFather() {
        return father;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", breed=" + breed +
                ", age=" + age +
                ", eat=" + eat +
                ", inWild=" + inWild +
                ", location='" + location + '\'' +
                ", mother=" + mother +
                ", father=" + father +
                '}';
    }

    public static class Builder extends ru.mail.polis.homework.io.objects.Builder { }
}
