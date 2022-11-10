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
    private final String name;
    private int legs;
    private final boolean canSwim;
    private final boolean canJump;
    private final AnimalType animalType;
    private OwnerAnimal ownerAnimal;
    private Habitat habitat;

    public Animal(boolean canSwim, boolean canJump, String name, int legs, AnimalType animalType, OwnerAnimal ownerAnimal, Habitat habitat) {
        this.canSwim = canSwim;
        this.canJump = canJump;
        this.name = name;
        this.legs = legs;
        this.animalType = animalType;
        this.ownerAnimal = ownerAnimal;
        this.habitat = habitat;
    }

    public boolean isCanSwim() {
        return canSwim;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public String getName() {
        return name;
    }

    public int getLegs() {
        return legs;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public OwnerAnimal getOwnerAnimal() {
        return ownerAnimal;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, legs, canJump, canSwim, ownerAnimal, habitat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal animal = (Animal) obj;
        return Objects.equals(name, animal.name) && legs == animal.legs &&
        canJump == animal.canJump && canJump == animal.canSwim &&
                Objects.equals(ownerAnimal, animal.ownerAnimal) &&
                Objects.equals(habitat, animal.habitat);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", legs=" + legs +
                ", canJump=" + canJump +
                ", canSwim=" + canSwim +
                ", ownerAnimal=" + ownerAnimal +
                ", habitat=" + habitat +
                '}';
    }
}

