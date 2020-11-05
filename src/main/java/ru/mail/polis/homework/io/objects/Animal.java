package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */

public class Animal implements Serializable {
    private String name;
    private int age;
    private int weight;
    private List<String> todosList; // Важные дела, которые данное животное должно выполнить
    private Species animalKind;
    private Clothes clothes;

    public Animal(String name, int age, int weight,
                  List<String> todosList, Species animalKind, Clothes clothes) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.todosList = todosList;
        this.animalKind = animalKind;
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
    public Species getAnimalKind() {
        return animalKind;
    }
    public Clothes getClothes() {
        return clothes;
    }
}
