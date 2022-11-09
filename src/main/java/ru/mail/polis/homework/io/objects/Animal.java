package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми самописными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private final int legs;
    private final boolean hair;
    private final boolean vertebrate;
    private final String name;
    private final AnimalType type;
    private final AnimalOwner owner;

    public Animal(int legs, boolean hair, boolean vertebrate, String name, AnimalType type, AnimalOwner owner) {
        this.legs = legs;
        this.hair = hair;
        this.vertebrate = vertebrate;
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public int getLegs() {
        return legs;
    }

    public boolean isHair() {
        return hair;
    }

    public boolean isVertebrate() {
        return vertebrate;
    }

    public String getName() {
        return name;
    }

    public AnimalType getType() {
        return type;
    }

    public AnimalOwner getOwner() {
        return owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(legs, hair, vertebrate, name, type, owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Animal)) {
            return false;
        }

        Animal animal = (Animal) obj;
        return this.legs == animal.legs && this.hair == animal.hair && this.vertebrate == animal.vertebrate &&
                Objects.equals(this.name, animal.name) && Objects.equals(this.type, animal.type) &&
                Objects.equals(this.owner, animal.owner);
    }
}
