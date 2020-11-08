package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private final String name;
    private final int age;
    private final double weight;
    private final AnimalType type;
    private final Person owner;
    private final List<Food> nutrition;

    public Animal(Animal animal) {
        this(animal.name, animal.age, animal.weight, animal.type, animal.owner, animal.nutrition);
    }

    public Animal(String name, int age, double weight, AnimalType type, Person owner, List<Food> nutrition) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.type = type;
        this.owner = owner;
        this.nutrition = nutrition;
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

    public AnimalType getType() {
        return type;
    }

    public Person getOwner() {
        return owner;
    }

    public List<Food> getNutrition() {
        return nutrition;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", type=" + type +
                ", owner=" + owner +
                ", nutrition=" + nutrition +
                '}';
    }
}
