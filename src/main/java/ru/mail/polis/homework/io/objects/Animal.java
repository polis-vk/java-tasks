package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и
 * некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 тугрик
 */
public class Animal implements Serializable {

    private String name;
    private boolean isDomestic;
    private boolean haveClaws;
    private int legsCount;
    private AnimalType animalType;
    private Organization organization;

    public Animal(String name, boolean isDomestic, boolean haveClaws, int legsCount, AnimalType animalType,
                  Organization organization) {
        this.name = name;
        this.isDomestic = isDomestic;
        this.haveClaws = haveClaws;
        this.legsCount = legsCount;
        this.animalType = animalType;
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return isDomestic == animal.isDomestic && haveClaws == animal.haveClaws && legsCount == animal.legsCount && Objects.equals(name, animal.name) && animalType == animal.animalType && Objects.equals(organization, animal.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isDomestic, haveClaws, legsCount, animalType, organization);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", isDomestic=" + isDomestic +
                ", haveClaws=" + haveClaws +
                ", legsCount=" + legsCount +
                ", animalType=" + animalType +
                ", organization=" + organization +
                '}';
    }
}

enum AnimalType {

    FISH("Fish"),
    BIRD("Bird"),
    MAMMAL("Mammal"),
    REPTILE("Reptile"),
    AMPHIBIAN("Amphibian"),
    INVERTEBRATE("Invertebrate");

    private final String title;

    AnimalType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AnimalType{" +
                "title='" + title + '\'' +
                '}';
    }
}

class Organization implements Serializable {

    private String title;
    private boolean isCommercial;
    private int animalsCount;

    public Organization(String title, boolean isCommercial, int animalsCount) {
        this.title = title;
        this.isCommercial = isCommercial;
        this.animalsCount = animalsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return isCommercial == that.isCommercial && animalsCount == that.animalsCount && Objects.equals(title,
                that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isCommercial, animalsCount);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", isCommercial=" + isCommercial +
                ", animalsCount=" + animalsCount +
                '}';
    }
}
