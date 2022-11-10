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
    private int legs;
    private boolean isWild;
    private boolean isDomesticated;
    private String nickname;
    private MoveType moveType;
    private AnimalDescription characteristic;

    public Animal(int legs, boolean isWild, boolean isDomesticated, String nickname, MoveType moveType, AnimalDescription characteristic) {
        this.legs = legs;
        this.isWild = isWild;
        this.isDomesticated = isDomesticated;
        this.nickname = nickname;
        this.moveType = moveType;
        this.characteristic = characteristic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return legs == animal.legs && isWild == animal.isWild && isDomesticated == animal.isDomesticated && Objects.equals(nickname, animal.nickname) && moveType == animal.moveType && Objects.equals(characteristic, animal.characteristic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legs, isWild, isDomesticated, nickname, moveType, characteristic);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "legs=" + legs +
                ", isWild=" + isWild +
                ", isDomesticated=" + isDomesticated +
                ", nickname='" + nickname + '\'' +
                ", moveType=" + moveType +
                ", characteristic=" + characteristic +
                '}';
    }

    static class AnimalDescription implements Serializable{
        private int age;
        private boolean isFriendly;
        private String ownerName;

        public AnimalDescription(int age, boolean isFriendly, String ownerName) {
            this.age = age;
            this.isFriendly = isFriendly;
            this.ownerName = ownerName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AnimalDescription that = (AnimalDescription) o;
            return age == that.age && isFriendly == that.isFriendly && Objects.equals(ownerName, that.ownerName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(age, isFriendly, ownerName);
        }

        @Override
        public String toString() {
            return "AnimalDescription{" +
                    "age=" + age +
                    ", isFriendly=" + isFriendly +
                    ", ownerName='" + ownerName + '\'' +
                    '}';
        }
    }
}
