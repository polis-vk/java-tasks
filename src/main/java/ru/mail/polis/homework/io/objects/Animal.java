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

    private String say;
    private int legs;
    private Boolean isWild;
    private boolean isHerbivorous;
    private Gender gender;
    private Сlassification classification;

    public Animal(String say, int legs, boolean isWild, boolean isHerbivorous,
                  Gender gender, Сlassification classification) {
        this.say = say;
        this.legs = legs;
        this.isWild = isWild;
        this.isHerbivorous = isHerbivorous;
        this.gender = gender;
        this.classification = classification;
    }

    public Animal() {

    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public boolean getIsWild() {
        return isWild;
    }

    public void setIsWild(boolean isWild) {
        this.isWild = isWild;
    }

    public boolean getIsHerbivorous() {
        return isHerbivorous;
    }

    public void setIsHerbivorous(boolean isHerbivorous) {
        this.isHerbivorous = isHerbivorous;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Сlassification getClassification() {
        return classification;
    }

    public void setClassification(Сlassification classification) {
        this.classification = classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return say.equals(animal.say) &&
                legs == animal.legs &&
                isWild == animal.isWild &&
                isHerbivorous == animal.isHerbivorous &&
                gender.equals(animal.gender) &&
                classification.equals(animal.classification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(say, legs, isWild, isHerbivorous,
                gender, classification);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "say = " + say +
                ", legs = " + legs +
                ", isWild = " + isWild +
                ", isHerbivorous = " + isHerbivorous +
                ", gender = " + gender +
                ", classification = " + classification +
                '}' + '\n';
    }

}
