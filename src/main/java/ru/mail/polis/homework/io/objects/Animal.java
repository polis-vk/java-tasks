package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

    private String name;
    private int age;
    private double weight;
    private Characteristic characteristic;
    private List<String> nameChildren;
    private AnimalInfo animalInfo;

    public Animal(String name, int age, double weight, Characteristic characteristic, List<String> nameChildren, AnimalInfo animalInfo) {
       this.name = name;
       this.age = age;
       this.weight = weight;
       this.characteristic = characteristic;
       this.nameChildren = new ArrayList<>(nameChildren);
       this.animalInfo = animalInfo;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", characteristic=" + characteristic +
                ", nameChildren=" + nameChildren +
                ", animalInfo=" + animalInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Double.compare(animal.weight, weight) == 0 &&
                Objects.equals(name, animal.name) &&
                characteristic == animal.characteristic &&
                Objects.equals(nameChildren, animal.nameChildren) &&
                Objects.equals(animalInfo, animal.animalInfo);
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public List<String> getNameChildren() {
        return nameChildren;
    }

    public void setNameChildren(List<String> nameChildren) {
        this.nameChildren = nameChildren;
    }

    public AnimalInfo getAnimalInfo() {
        return animalInfo;
    }

    public void setAnimalInfo(AnimalInfo animalInfo) {
        this.animalInfo = animalInfo;
    }
}
