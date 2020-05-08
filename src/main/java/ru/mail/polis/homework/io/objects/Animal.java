package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.ArrayList;
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
    private final String birthdate;
    private final AnimalTypes type;
    private final Animal mother;
    private final Animal father;
    private List<Vaccination> vaccinations;
    private List<Animal> children;
    private String area;

    public Animal(String name, String birthdate, AnimalTypes type) {
        this.name = name;
        this.birthdate = birthdate;
        this.type = type;
        this.mother = null;
        this.father = null;
    }

    public Animal(String name, String birthdate, AnimalTypes type, Animal mother, Animal father, List<Vaccination> vaccinations, List<Animal> children, String area) {
        this.name = name;
        this.birthdate = birthdate;
        this.type = type;
        this.mother = mother;
        this.father = father;
        this.vaccinations = vaccinations;
        this.children = children;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public AnimalTypes getType() {
        return type;
    }

    public Animal getMother() {
        return mother;
    }

    public Animal getFather() {
        return father;
    }

    public List<Vaccination> getVaccinations() {
        return new ArrayList<Vaccination>(vaccinations);
    }

    public String getArea() {
        return area;
    }

    public List<Animal> getChildren() {
        return new ArrayList<Animal>(children) {
        };
    }

    public void setChildren(List<Animal> children) {
        for (Animal child : children) {
            this.children.add(child);
        }
    }

    public void setVaccinations(List<Vaccination> vaccinations) {
        for (Vaccination vaccination : vaccinations) {
            this.vaccinations.add(vaccination);
        }
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Name - " + name +
                ", birthdate - " + birthdate +
                ", type - " + type +
                ", mother - " + mother +
                ", father - " + father +
                ", vaccinations - " + vaccinations +
                ", area - " + area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return name.equals(animal.name) &&
                birthdate.equals(animal.birthdate) &&
                type == animal.type &&
                Objects.equals(mother, animal.mother) &&
                Objects.equals(father, animal.father) &&
                Objects.equals(vaccinations, animal.vaccinations) &&
                Objects.equals(children, animal.children) &&
                Objects.equals(area, animal.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(father);
    }
}
