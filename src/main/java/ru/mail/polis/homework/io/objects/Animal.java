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


    public Animal() {
    }

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
                Objects.equals(name, animal.name) &&
                Objects.equals(type, animal.type) &&
                Objects.equals(owner, animal.owner);
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

    public void setAggressive(boolean aggressive) {
        isAggressive = aggressive;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public void setNumOfLegs(int numOfLegs) {
        this.numOfLegs = numOfLegs;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(TypeOfAnimal type) {
        this.type = type;
    }

    public void setOwner(OwnerOfAnimal owner) {
        this.owner = owner;
    }
}

enum TypeOfAnimal {
    INSECT((byte) 1),
    MAMMAL((byte) 2),
    FISH((byte) 3),
    BIRD((byte) 4),
    ALIEN((byte) 5);

    private final byte ord;

    TypeOfAnimal(byte b) {
        this.ord = b;
    }

    public byte getOrd() {
        return ord;
    }

    public static TypeOfAnimal getByOrd(byte b) {
        for (TypeOfAnimal animal : TypeOfAnimal.values()) {
            if (animal.ord == b) {
                return animal;
            }
        }
        return null;
    }
}

class OwnerOfAnimal implements Serializable {
    private String name;
    private String phoneNumber;
    private Gender gender;
    private Address homeAddress;

    public OwnerOfAnimal() {
    }

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
        ans.append("Пол - ").append(gender == null ? "Отсутствует" : gender.toString()).append("\n");
        ans.append("Домашний адрес - ").append(homeAddress == null ? "Отсутствует" : homeAddress.getFullAddress()).append("\n");
        return ans.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerOfAnimal that = (OwnerOfAnimal) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(homeAddress, that.homeAddress);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
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

    public Address() {

    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumOfHouse(int numOfHouse) {
        this.numOfHouse = numOfHouse;
    }

    public void setNumOfBuilding(int numOfBuilding) {
        this.numOfBuilding = numOfBuilding;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }

    public void setRoom(int room) {
        this.room = room;
    }

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

        if (liter != null && !liter.isEmpty()) {
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
                Objects.equals(country, address.country) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(liter, address.liter);
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