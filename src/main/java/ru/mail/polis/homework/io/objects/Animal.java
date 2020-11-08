package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */

public class Animal implements Serializable {
    private final String name;
    private final int age;
    private final int weight;
    private final List<String> todosList; // Важные дела, которые данное животное должно выполнить
    private final Species species;
    private final Clothes clothes;

    public Animal(String name, int age, int weight,
                  List<String> todosList, Species species, Clothes clothes) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.todosList = todosList;
        this.species = species;
        this.clothes = clothes;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getWeight() {
        return weight;
    }
    public List<String> getTodosList() {
        return todosList;
    }
    public Species getSpecies() {
        return species;
    }
    public Clothes getClothes() {
        return clothes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                weight == animal.weight &&
                name.equals(animal.name) &&
                todosList.equals(animal.todosList) &&
                species == animal.species &&
                clothes.equals(animal.clothes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, todosList, species, clothes);
    }
}
