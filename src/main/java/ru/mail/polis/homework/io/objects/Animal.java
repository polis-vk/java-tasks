package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    public enum Diet {
        PREDATOR,
        SCAVENGER,
        HERBIVORE,
        OMNIVORE
    }

    private String name;
    private final Diet diet;
    private final Genotype genotype;
    private final int speciesId;
    private final List<Integer> scaredOf;
    private final boolean singleCell;

    public Animal(String name,
                  Diet diet,
                  Genotype genotype,
                  int speciesId,
                  List<Integer> scaredOf,
                  boolean singleCell) {
        this.name = name;
        this.diet = diet;
        this.genotype = genotype;
        this.speciesId = speciesId;
        this.scaredOf = new ArrayList<>(scaredOf);
        this.singleCell = singleCell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public Diet getDiet() {
        return diet;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public List<Integer> getScaredOf() {
        return Collections.unmodifiableList(scaredOf);
    }

    public void scareOf(int speciesId) {
        scaredOf.add(speciesId);
    }

    public void notScareOf(int speciesId) {
        scaredOf.remove(Integer.valueOf(speciesId));
    }

    public boolean isSingleCell() {
        return singleCell;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", diet=" + diet +
                ", genotype=" + genotype +
                ", speciesId=" + speciesId +
                ", scaredOf=" + scaredOf +
                ", singleCell=" + singleCell +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return speciesId == animal.speciesId &&
                singleCell == animal.singleCell &&
                Objects.equals(name, animal.name) &&
                diet == animal.diet &&
                Objects.equals(genotype, animal.genotype) &&
                Objects.equals(scaredOf, animal.scaredOf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, diet, genotype, speciesId, scaredOf, singleCell);
    }
}
