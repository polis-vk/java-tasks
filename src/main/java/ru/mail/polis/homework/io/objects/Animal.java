package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми самописными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private int legs;
    private boolean isDomesticated;
    private boolean isWarmBlooded;
    private String name;
    private Diet diet;
    private Person discoveredBy;

    public Animal(int legs,
                  boolean isDomesticated,
                  boolean isWarmBlooded,
                  String name,
                  Diet diet,
                  Person discoveredBy) {
        this.legs = legs;
        this.isDomesticated = isDomesticated;
        this.isWarmBlooded = isWarmBlooded;
        this.name = name;
        this.diet = diet;
        this.discoveredBy = discoveredBy;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public boolean isDomesticated() {
        return isDomesticated;
    }

    public void setDomesticated(boolean domesticated) {
        isDomesticated = domesticated;
    }

    public boolean isWarmBlooded() {
        return isWarmBlooded;
    }

    public void setWarmBlooded(boolean warmBlooded) {
        isWarmBlooded = warmBlooded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Diet getDiet() {
        return diet;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public Person getDiscoveredBy() {
        return discoveredBy;
    }

    public void setDiscoveredBy(Person discoveredBy) {
        this.discoveredBy = discoveredBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return legs == animal.legs &&
                isDomesticated == animal.isDomesticated &&
                isWarmBlooded == animal.isWarmBlooded &&
                name.equals(animal.name) &&
                diet == animal.diet &&
                Objects.equals(discoveredBy, animal.discoveredBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legs, isDomesticated, isWarmBlooded, name, diet, discoveredBy);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "legs=" + legs +
                ", isDomesticated=" + isDomesticated +
                ", isWarmBlooded=" + isWarmBlooded +
                ", name='" + name + '\'' +
                ", diet=" + diet +
                ", discoveredBy=" + discoveredBy +
                '}';
    }
}

class Person implements Serializable {
    private String name;
    private String shortBio;
    private LocalDate born;
    private LocalDate died;

    public Person(String name, String shortBio, LocalDate born, LocalDate died) {
        this.name = name;
        this.shortBio = shortBio;
        this.born = born;
        this.died = died;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public LocalDate getDied() {
        return died;
    }

    public void setDied(LocalDate died) {
        this.died = died;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && shortBio.equals(person.shortBio) && born.equals(person.born) && Objects.equals(died, person.died);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shortBio, born, died);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", shortBio='" + shortBio + '\'' +
                ", born=" + born +
                ", died=" + died +
                '}';
    }
}

enum Diet {
    CARNIVORE,
    OMNIVORE,
    HERBIVORE
}