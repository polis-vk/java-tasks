package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    private final String nickname;
    private final boolean isTrained;
    private final Breed breed;
    private final Cattery cattery;

    public Animal(String nickname, boolean isTrained, Breed breed, Cattery cattery) {
        this.nickname = nickname;
        this.isTrained = isTrained;
        this.breed = breed;
        this.cattery = cattery;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isTrained() {
        return isTrained;
    }

    public Breed getBreed() {
        return breed;
    }

    public Cattery getCattery() {
        return cattery;
    }

    @Override
    public boolean equals (Object o) {
        if(o == this){
            return true;
        }

        if(o.getClass() != getClass() || o == null){
            return false;
        }
        Animal animal = (Animal)o;
        if (this.nickname.equals(animal.nickname)
                && this.isTrained == animal.isTrained
                && this.breed.equals(animal.breed)
                && this.cattery.equals(animal.cattery)) {
            return true;
        }
        return false;
    }
}