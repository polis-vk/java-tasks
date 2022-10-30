package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private boolean isAggressive;
    private boolean isVegetarian;
    private int numOfLegs;
    private double maxVelocity;
    private String name;
    private TypeOfAnimal type;
    private OwnerOfAnimalWithMethods owner;


    public AnimalWithMethods(boolean isAggressive, boolean isVegetarian, int numOfLegs, double maxVelocity,
                             String name, TypeOfAnimal type, OwnerOfAnimalWithMethods owner) {
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

    public void writeObject(ObjectOutput out) throws IOException {
        out.writeBoolean(isAggressive);
        out.writeBoolean(isVegetarian);
        out.writeInt(numOfLegs);
        out.writeDouble(maxVelocity);
        out.writeUTF(name);
        out.writeByte(type.ordinal());
        out.writeObject(owner);
    }

    public void readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        this.isAggressive = in.readBoolean();
        this.isVegetarian = in.readBoolean();
        this.numOfLegs = in.readInt();
        this.maxVelocity = in.readDouble();
        this.name = in.readUTF();
        this.type = TypeOfAnimal.values()[in.readByte()];
        this.owner = (OwnerOfAnimalWithMethods) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return isAggressive == animal.isAggressive &&
                isVegetarian == animal.isVegetarian &&
                numOfLegs == animal.numOfLegs &&
                Double.compare(animal.maxVelocity, maxVelocity) == 0 &&
                Objects.equals(name, animal.name) &&
                type == animal.type &&
                Objects.equals(owner, animal.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAggressive, isVegetarian, numOfLegs, maxVelocity, name, type, owner);
    }
}

class OwnerOfAnimalWithMethods implements Serializable {
    private String name;
    private String phoneNumber;
    private Gender gender;
    private AddressWithMethods homeAddress;

    public OwnerOfAnimalWithMethods(String name, String phoneNumber, Gender gender, AddressWithMethods address) {
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
        OwnerOfAnimalWithMethods that = (OwnerOfAnimalWithMethods) o;
        return name.equals(that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                gender == that.gender &&
                Objects.equals(homeAddress, that.homeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, gender, homeAddress);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(phoneNumber);
        out.writeByte(gender.ordinal());
        out.writeObject(homeAddress);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        this.phoneNumber = in.readUTF();
        this.gender = Gender.values()[in.readByte()];
        this.homeAddress = (AddressWithMethods) in.readObject();
    }
}

class AddressWithMethods implements Serializable {
    String country;
    String city;
    String street;
    int numOfHouse;
    int numOfBuilding;
    String liter;
    int room;

    public AddressWithMethods(String country, String city, String street, int numOfHouse,
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
        AddressWithMethods address = (AddressWithMethods) o;
        return numOfHouse == address.numOfHouse &&
                numOfBuilding == address.numOfBuilding &&
                room == address.room &&
                country.equals(address.country) &&
                city.equals(address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(liter, address.liter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, numOfHouse, numOfBuilding, liter, room);
    }


    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(country);
        out.writeUTF(city);
        out.writeUTF(street);
        out.writeInt(numOfHouse);
        out.writeInt(numOfBuilding);
        out.writeUTF(liter);
        out.writeInt(room);
    }

    public void readExternal(ObjectInput in) throws IOException {
        this.country = in.readUTF();
        this.city = in.readUTF();
        this.street = in.readUTF();
        this.numOfHouse = in.readInt();
        this.numOfBuilding = in.readInt();
        this.liter = in.readUTF();
        this.room = in.readInt();
    }
}