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
    public enum MoveType {
        RUN, FLY, CRAWL
    }

    public static class Address implements Serializable {
        private final String street;
        private final int house;
        private final String phoneNumber;

        public Address(String street, int house, String phoneNumber) {
            this.street = street;
            this.house = house;
            this.phoneNumber = phoneNumber;
        }

        public String getStreet() {
            return street;
        }

        public int getHouse() {
            return house;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return house == address.house && Objects.equals(street, address.street) && Objects.equals(phoneNumber, address.phoneNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(street, house, phoneNumber);
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", house=" + house +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }
    }

    private final boolean isHappy;
    private final boolean isAngry;
    private final int legs;
    private final String name;
    private final MoveType moveType;
    private final Address homeAddress;

    public Animal(boolean isHappy, boolean isAngry, int legs, String name, MoveType moveType, Address homeAddress) {
        this.isHappy = isHappy;
        this.isAngry = isAngry;
        this.legs = legs;
        this.name = name;
        this.moveType = moveType;
        this.homeAddress = homeAddress;
    }

    public boolean isHappy() {
        return isHappy;
    }

    public boolean isAngry() {
        return isAngry;
    }

    public int getLegs() {
        return legs;
    }

    public String getName() {
        return name;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return isHappy == animal.isHappy && isAngry == animal.isAngry && legs == animal.legs && Objects.equals(name, animal.name) && moveType == animal.moveType && Objects.equals(homeAddress, animal.homeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isHappy, isAngry, legs, name, moveType, homeAddress);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "isHappy=" + isHappy +
                ", isAngry=" + isAngry +
                ", legs=" + legs +
                ", name='" + name + '\'' +
                ", moveType=" + moveType +
                ", homeAddress=" + homeAddress +
                '}';
    }
}
