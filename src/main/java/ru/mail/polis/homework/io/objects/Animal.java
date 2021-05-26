package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    public enum Colour {
        BLACK,
        WHITE,
        BROWN,
        GRAY
    }

    private Zoo livingPlace;

    private String name;

    private byte age;

    private Colour colour;

    public Animal() {

    }

    public Animal(Zoo livingPlace, String name, byte age, Colour colour) {
        this.livingPlace = livingPlace;
        this.name = name;
        this.age = age;
        this.colour = colour;
    }

    public Zoo getLivingPlace() {
        return livingPlace;
    }

    public void setLivingPlace(Zoo livingPlace) {
        this.livingPlace = livingPlace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal testObj =(Animal) obj;
        return testObj.age == age
                && testObj.name.equals(name)
                && testObj.colour == colour
                && testObj.livingPlace == livingPlace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(livingPlace, name, age, colour);
    }
}
