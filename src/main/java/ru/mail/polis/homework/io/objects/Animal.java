package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    private final String name;
    private final Date birthdate;
    private final AnimalTypes type;
    private final Animal mother;
    private final Animal father;
    private final List<Vaccination> vaccinations;
    private String area;

    public Animal(String name, Date birthdate, AnimalTypes type, Animal mother, Animal father, List<Vaccination> vaccinations, String area) {
        this.name = name;
        this.birthdate = birthdate;
        this.type = type;
        this.mother = mother;
        this.father = father;
        this.vaccinations = vaccinations;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public Date getBirthdate() {
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
        return vaccinations;
    }

    public String getArea() {
        return area;
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
}
