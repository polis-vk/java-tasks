package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    private final String name;
    private final Eats eats;
    private final int personalNumber;
    private int age;
    private Owner owner;
    private final List<Food> eaten;
    private static int id;

    public Animal(String name, int age, Owner owner, Eats eats) {
        this.name = name;
        this.age = age;
        this.eats = eats;
        this.owner = owner;
        this.eaten = new ArrayList<>();
        personalNumber = id++;
    }

    public String getName() {
        return name;
    }

    public Eats getEats() {
        return eats;
    }

    public int getPersonalNumber() {
        return personalNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        try {
            if (age < this.age) {
                throw new IllegalArgumentException("Age lower");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
            age = this.age;
        }
        this.age = age;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        if (owner != null) {
            this.owner = owner;
        }
    }

    public List<Food> getEaten() {
        if (eaten.isEmpty()) {
            System.out.println("Empty list");
            return eaten;
        }
        return eaten;
    }

    public void addEaten(Food eaten) {
        this.eaten.add(eaten);
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
        return this.name.equals(animal.name)
          && this.eats.equals(animal.eats)
          && this.eaten.equals(animal.eaten)
          && this.owner.equals(animal.owner)
          && this.age == animal.age
          && this.personalNumber == animal.personalNumber;
    }

    @Override
    public String toString() {
        return "Animal{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", eats=" + eats +
          ", personalNumber=" + personalNumber +
          ", owner=" + this.owner +
          '}';
    }
}

