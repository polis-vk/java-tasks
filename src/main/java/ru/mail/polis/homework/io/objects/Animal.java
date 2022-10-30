package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 тугрик
 */
public class Animal implements Serializable {
    private boolean isAggressive;
    private boolean isVegetarian;
    private int numOfLegs;
    private double maxVelocity;
    private String name;
    private TypeOfAnimal type;
    private OwnerOfAnimal owner;


    public Animal(boolean isAggressive, boolean isVegetarian, int numOfLegs, double maxVelocity,
                  String name, TypeOfAnimal type, OwnerOfAnimal owner) {
        this.isAggressive = isAggressive;
        this.isVegetarian = isVegetarian;
        this.numOfLegs = numOfLegs;
        this.maxVelocity = maxVelocity;
        this.name = name;
        this.type = type;
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "Animal{" +
                "isAggressive=" + isAggressive + '\n' +
                ", isVegetarian=" + isVegetarian + '\n' +
                ", numOfLegs=" + numOfLegs + '\n' +
                ", maxVelocity=" + maxVelocity + '\n' +
                ", name='" + name + '\'' + '\n' +
                ", type=" + type + '\n' +
                ", owner=" + owner + '\n' +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return isAggressive == animal.isAggressive &&
                isVegetarian == animal.isVegetarian &&
                numOfLegs == animal.numOfLegs &&
                Math.abs(animal.maxVelocity - maxVelocity) <= 1e-6 &&
                name.equals(animal.name) &&
                type == animal.type &&
                owner.equals(animal.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAggressive, isVegetarian, numOfLegs, maxVelocity, name, type, owner);
    }

    public boolean isAggressive() {
        return isAggressive;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public int getNumOfLegs() {
        return numOfLegs;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public String getName() {
        return name;
    }

    public TypeOfAnimal getType() {
        return type;
    }

    public OwnerOfAnimal getOwner() {
        return owner;
    }
}

enum TypeOfAnimal {
    INSECT,
    MAMMAL,
    FISH,
    BIRD,
    ALIEN
}

class OwnerOfAnimal implements Serializable {
    private String name;
    private String phoneNumber;
    private Gender gender;
    private Address homeAddress;

    public OwnerOfAnimal(String name, String phoneNumber, Gender gender, Address address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.homeAddress = address;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("Владелец: ");
        ans.append(name).append("\n");
        ans.append("Номер телефона - ").append(phoneNumber).append("\n");
        ans.append("Пол - ").append(gender.toString()).append("\n");
        ans.append("Домашний адрес - ").append(homeAddress.getFullAddress()).append("\n");
        return ans.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerOfAnimal that = (OwnerOfAnimal) o;
        return name.equals(that.name) &&
                phoneNumber.equals(that.phoneNumber) &&
                gender == that.gender &&
                homeAddress.equals(that.homeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, gender, homeAddress);
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }
}

enum Gender {
    MALE("Мужской"),
    FEMALE("Женский"),
    OTHER("Другой");
    private final String name;

    Gender(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Address implements Serializable {
    private String country;
    private String city;
    private String street;
    private int numOfHouse;
    private int numOfBuilding;
    private String liter;
    private int room;

    public Address(String country, String city, String street, int numOfHouse,
                   int numOfBuilding, String liter, int room) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.numOfHouse = numOfHouse;
        this.numOfBuilding = numOfBuilding;
        this.liter = liter;
        this.room = room;
    }

    public String getFullAddress() {
        StringBuilder ans = new StringBuilder();
        ans.append(country).append(", ");
        ans.append(city).append(", ");
        ans.append(street).append(", ");
        if (numOfHouse > 0) {
            ans.append("дом ").append(numOfHouse).append(", ");
        }

        if (numOfBuilding > 0) {
            ans.append("корпус ").append(numOfBuilding).append(", ");
        }

        if (!liter.isEmpty()) {
            ans.append("литера ").append(liter).append(", ");
        }

        if (room > 0) {
            ans.append("квартира ").append(room);
        }
        return ans.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return numOfHouse == address.numOfHouse &&
                numOfBuilding == address.numOfBuilding &&
                room == address.room &&
                country.equals(address.country) &&
                city.equals(address.city) &&
                street.equals(address.street) &&
                liter.equals(address.liter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, numOfHouse, numOfBuilding, liter, room);
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getNumOfHouse() {
        return numOfHouse;
    }

    public int getNumOfBuilding() {
        return numOfBuilding;
    }

    public String getLiter() {
        return liter;
    }

    public int getRoom() {
        return room;
    }
}