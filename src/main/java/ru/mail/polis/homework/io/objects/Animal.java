package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */

public class Animal implements Serializable {
    public Animal(Animal a) {
        this(a.name, a.species, a.admissionDate, a.group, a.disease, a.favouriteFood, a.age, a.length, a.height);
    }

    enum AnimalGroup{
        MAMMAL,
        BIRD,
        FISH,
        REPTILE
    }

    private String name;
    private String species;
    private Date admissionDate;
    private AnimalGroup group;

    public Disease getDisease() {
        return disease;
    }

    public List<String> getFavouriteFood() {
        return favouriteFood;
    }

    private Disease disease;
    private List<String> favouriteFood;

    private int age;

    private double length;
    private double height;

    public Animal(){}

    public Animal(String name, String species, Date admissionDate, AnimalGroup group, Disease disease, List<String> favouriteFood, int age, double length, double height) {
        this.name = name;
        this.species = species;
        this.admissionDate = admissionDate;
        this.group = group;
        this.disease = disease;
        this.favouriteFood = favouriteFood;
        this.age = age;
        this.length = length;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public AnimalGroup getGroup() {
        return group;
    }

    public int getAge() {
        return age;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public void setGroup(AnimalGroup group) {
        this.group = group;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public void setFavouriteFood(List<String> favouriteFood) {
        this.favouriteFood = favouriteFood;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", admissionDate=" + admissionDate +
                ", group=" + group +
                ", disease=" + disease +
                ", favouriteFood=" + favouriteFood +
                ", age=" + age +
                ", length=" + length +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Double.compare(animal.length, length) == 0 &&
                Double.compare(animal.height, height) == 0 &&
                name.equals(animal.name) &&
                species.equals(animal.species) &&
                admissionDate.equals(animal.admissionDate) &&
                group == animal.group &&
                disease.equals(animal.disease) &&
                favouriteFood.equals(animal.favouriteFood);
    }
}
