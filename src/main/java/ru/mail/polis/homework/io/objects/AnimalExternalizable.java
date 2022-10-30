package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;


/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private boolean isAggressive;
    private boolean isVegetarian;
    private int numOfLegs;
    private double maxVelocity;
    private String name;
    private TypeOfAnimal type;
    private OwnerOfAnimalExternalizable owner;

    public AnimalExternalizable(boolean isAggressive, boolean isVegetarian, int numOfLegs, double maxVelocity, String name, TypeOfAnimal type, OwnerOfAnimalExternalizable owner) {
        this.isAggressive = isAggressive;
        this.isVegetarian = isVegetarian;
        this.numOfLegs = numOfLegs;
        this.maxVelocity = maxVelocity;
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public AnimalExternalizable() {
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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(isAggressive);
        out.writeBoolean(isVegetarian);
        out.writeInt(numOfLegs);
        out.writeDouble(maxVelocity);
        out.writeUTF(name);
        out.writeByte(type.ordinal());
        out.writeObject(owner);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.isAggressive = in.readBoolean();
        this.isVegetarian = in.readBoolean();
        this.numOfLegs = in.readInt();
        this.maxVelocity = in.readDouble();
        this.name = in.readUTF();
        this.type = TypeOfAnimal.values()[in.readByte()];
        this.owner = (OwnerOfAnimalExternalizable) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return isAggressive == that.isAggressive &&
                isVegetarian == that.isVegetarian &&
                numOfLegs == that.numOfLegs &&
                Double.compare(that.maxVelocity, maxVelocity) == 0 &&
                Objects.equals(name, that.name) &&
                type == that.type &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAggressive, isVegetarian, numOfLegs, maxVelocity, name, type, owner);
    }
}

class OwnerOfAnimalExternalizable implements Externalizable {
    private String name;
    private String phoneNumber;
    private Gender gender;
    private AddressExternalizable homeAddress;

    public OwnerOfAnimalExternalizable(String name, String phoneNumber, Gender gender, AddressExternalizable address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.homeAddress = address;
    }

    public OwnerOfAnimalExternalizable() {
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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(phoneNumber);
        out.writeByte(gender.ordinal());
        out.writeObject(homeAddress);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        this.phoneNumber = in.readUTF();
        this.gender = Gender.values()[in.readByte()];
        this.homeAddress = (AddressExternalizable) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerOfAnimalExternalizable that = (OwnerOfAnimalExternalizable) o;
        return name.equals(that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                gender == that.gender &&
                Objects.equals(homeAddress, that.homeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, gender, homeAddress);
    }
}

class AddressExternalizable implements Externalizable {
    String country;
    String city;
    String street;
    int numOfHouse;
    int numOfBuilding;
    String liter;
    int room;

    public AddressExternalizable(String country, String city, String street, int numOfHouse, int numOfBuilding, String liter, int room) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.numOfHouse = numOfHouse;
        this.numOfBuilding = numOfBuilding;
        this.liter = liter;
        this.room = room;
    }

    public AddressExternalizable() {
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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(country);
        out.writeUTF(city);
        out.writeUTF(street);
        out.writeInt(numOfHouse);
        out.writeInt(numOfBuilding);
        out.writeUTF(liter);
        out.writeInt(room);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        this.country = in.readUTF();
        this.city = in.readUTF();
        this.street = in.readUTF();
        this.numOfHouse = in.readInt();
        this.numOfBuilding = in.readInt();
        this.liter = in.readUTF();
        this.room = in.readInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressExternalizable that = (AddressExternalizable) o;
        return numOfHouse == that.numOfHouse &&
                numOfBuilding == that.numOfBuilding &&
                room == that.room && country.equals(that.country) &&
                city.equals(that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(liter, that.liter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, numOfHouse, numOfBuilding, liter, room);
    }

}
