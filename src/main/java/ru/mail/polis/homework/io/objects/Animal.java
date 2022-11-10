package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {

    private String name;
    private int legs;
    private double weight;
    private boolean isChild;
    private boolean isWild;
    private AnimalType type;
    private Organization organization;

    public Animal(
        String name,
        int legs,
        double weight,
        boolean isChild,
        boolean isWild,
        AnimalType type,
        Organization organization
    ) {
        this.name = name;
        this.legs = legs;
        this.weight = weight;
        this.isChild = isChild;
        this.isWild = isWild;
        this.type = type;
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public int getLegs() {
        return legs;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isChild() {
        return isChild;
    }

    public boolean isWild() {
        return isWild;
    }

    public AnimalType getType() {
        return type;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setIsChild(boolean isChild) {
        this.isChild = isChild;
    }

    public void setIsWild(boolean isWild) {
        this.isWild = isWild;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, legs, weight, isChild, isWild, type, organization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(this.name, animal.name) && this.legs == animal.legs && this.weight == animal.weight &&
            this.isChild == animal.isChild && this.isWild == animal.isWild &&Objects.equals(this.type, animal.type) &&
            Objects.equals(this.organization, animal.organization);
    }

    @Override
    public String toString() {
        return "Animal{" +
            "name='" + name + '\'' +
            ", legs=" + legs +
            ", weight=" + weight +
            ", isChild=" + isChild +
            ", isWild=" + isWild +
            ", type=" + type +
            ", organization=" + organization +
            '}';
    }
}
