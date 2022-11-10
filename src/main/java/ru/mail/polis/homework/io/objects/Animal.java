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

    private boolean isAlive;
    private boolean isExotic;
    private short legs;
    private String voice;
    private AnimalSex sex;
    private Zoo zoo;

    public Animal() {

    }

    public Animal(boolean isAlive, boolean isExotic, short legs, String voice, AnimalSex sex, Zoo zoo) {
        this.isAlive = isAlive;
        this.isExotic = isExotic;
        this.legs = legs;
        this.voice = voice;
        this.sex = sex;
        this.zoo = zoo;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isExotic() {
        return isExotic;
    }

    public void setExotic(boolean exotic) {
        isExotic = exotic;
    }

    public short getLegs() {
        return legs;
    }

    public void setLegs(short legs) {
        this.legs = legs;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public AnimalSex getSex() {
        return sex;
    }

    public void setSex(AnimalSex sex) {
        this.sex = sex;
    }

    public Zoo getZoo() {
        return zoo;
    }

    public void setZoo(Zoo zoo) {
        this.zoo = zoo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return isAlive == animal.isAlive && isExotic == animal.isExotic && legs == animal.legs && Objects.equals(voice, animal.voice) && sex == animal.sex && Objects.equals(zoo, animal.zoo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAlive, isExotic, legs, voice, sex, zoo);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "isAlive=" + isAlive +
                ", isExotic=" + isExotic +
                ", legs=" + legs +
                ", voice='" + voice + '\'' +
                ", sex=" + sex +
                ", zoo=" + zoo +
                '}';
    }
}
