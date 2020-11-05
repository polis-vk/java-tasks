package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public Animal() {

    }
    public Animal(String name, int age, double weight, Characteristic characteristic, List<String> nameChildren) {
       this.name = name;
       this.age = age;
       this.weight = weight;
       this.characteristic = characteristic;
       this.nameChildren = new ArrayList<>(nameChildren);
    }

    @Override
    public String toString() {
        return "Animal{ " + "name=" + name +
                ", age=" + age +
                ", weight=" + weight +
                ", characteristic=" + characteristic +
                ", nameChildren=" + nameChildren + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Animal guest = (Animal) obj;
        return  name.equals(guest.getName()) &&
                age == guest.getAge() &&
                weight == guest.getWeight() &&
                characteristic.equals(guest.getCharacteristic()) &&
                nameChildren.equals(guest.getNameChildren());

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public void setNameChildren(List<String> nameChildren) {
        this.nameChildren = nameChildren;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public List<String> getNameChildren() {
        return nameChildren;
    }
}
