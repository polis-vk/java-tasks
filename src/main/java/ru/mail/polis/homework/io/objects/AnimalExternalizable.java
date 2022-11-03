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
        byte twoBools = (byte) ((isAggressive ? 0 : 1) << 1);
        twoBools += (isVegetarian ? 0 : 1);
        out.writeByte(FieldOfAnimal.IS_AGGRESSIVE_IS_VEGETARIAN.getOrd());
        out.writeByte(twoBools);
        out.writeByte(FieldOfAnimal.NUM_OF_LEGS.getOrd());
        out.writeInt(numOfLegs);
        out.writeByte(FieldOfAnimal.MAX_VELOCITY.getOrd());
        out.writeDouble(maxVelocity);
        if (type != null) {
            out.writeByte(FieldOfAnimal.TYPE.getOrd());
            out.writeByte(type.ordinal());
        }
        if (name != null) {
            out.writeByte(FieldOfAnimal.NAME.getOrd());
            out.writeUTF(name);
        }
        if (owner != null) {
            out.writeByte(FieldOfAnimal.START_OF_OWNER.getOrd());
            out.writeObject(owner);
        }
        out.writeByte(FieldOfAnimal.END_OF_ANIMAL.getOrd());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        while (true) {
            FieldOfAnimal fieldType = FieldOfAnimal.getByOrd(in.readByte());
            switch (fieldType) {
                case IS_AGGRESSIVE_IS_VEGETARIAN:
                    byte twoBools = in.readByte();
                    this.isVegetarian = (twoBools % 2) == 0;
                    twoBools >>= 1;
                    this.isAggressive = (twoBools % 2) == 0;
                    break;
                case NUM_OF_LEGS:
                    this.numOfLegs = in.readInt();
                    break;
                case MAX_VELOCITY:
                    this.maxVelocity = in.readDouble();
                    break;
                case TYPE:
                    this.type = TypeOfAnimal.values()[in.readByte()];
                    break;
                case NAME:
                    this.name = in.readUTF();
                    break;
                case START_OF_OWNER:
                    this.owner = (OwnerOfAnimalExternalizable) in.readObject();
                    break;
            }
            if (fieldType == FieldOfAnimal.END_OF_ANIMAL) {
                break;
            }
        }
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

    public boolean isAggressive() {
        return isAggressive;
    }

    public void setAggressive(boolean aggressive) {
        isAggressive = aggressive;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public int getNumOfLegs() {
        return numOfLegs;
    }

    public void setNumOfLegs(int numOfLegs) {
        this.numOfLegs = numOfLegs;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOfAnimal getType() {
        return type;
    }

    public void setType(TypeOfAnimal type) {
        this.type = type;
    }

    public OwnerOfAnimalExternalizable getOwner() {
        return owner;
    }

    public void setOwner(OwnerOfAnimalExternalizable owner) {
        this.owner = owner;
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
        if (name != null) {
            out.writeByte(FieldOfOwner.NAME.getOrd());
            out.writeUTF(name);
        }
        if (phoneNumber != null) {
            out.writeByte(FieldOfOwner.NUMBER.getOrd());
            out.writeUTF(phoneNumber);
        }
        if (gender != null) {
            out.writeByte(FieldOfOwner.GENDER.getOrd());
            out.writeByte(gender.ordinal());
        }

        if (homeAddress != null) {
            out.writeByte(FieldOfOwner.START_OF_ADDRESS.getOrd());
            out.writeObject(homeAddress);
        }

        out.writeByte(FieldOfOwner.END_OF_OWNER.getOrd());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        while (true) {
            FieldOfOwner field = FieldOfOwner.getByOrd(in.readByte());
            switch (field) {
                case NAME:
                    this.name = in.readUTF();
                    break;
                case NUMBER:
                    this.phoneNumber = in.readUTF();
                    break;
                case GENDER:
                    this.gender = Gender.values()[in.readByte()];
                    break;
                case START_OF_ADDRESS:
                    this.homeAddress = (AddressExternalizable) in.readObject();
                    break;
            }

            if (field == FieldOfOwner.END_OF_OWNER) {
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerOfAnimalExternalizable that = (OwnerOfAnimalExternalizable) o;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public AddressExternalizable getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(AddressExternalizable homeAddress) {
        this.homeAddress = homeAddress;
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
        if (country != null) {
            out.writeByte(FieldOfAddress.COUNTRY.getOrd());
            out.writeUTF(country);
        }
        if (city != null) {
            out.writeByte(FieldOfAddress.CITY.getOrd());
            out.writeUTF(city);
        }
        if (street != null) {
            out.writeByte(FieldOfAddress.STREET.getOrd());
            out.writeUTF(street);
        }
        out.writeByte(FieldOfAddress.NUM_OF_HOUSE.getOrd());
        out.writeInt(numOfHouse);
        out.writeByte(FieldOfAddress.NUM_OF_BUILDING.getOrd());
        out.writeInt(numOfBuilding);
        if (liter != null) {
            out.writeByte(FieldOfAddress.LITER.getOrd());
            out.writeUTF(liter);
        }
        out.writeByte(FieldOfAddress.ROOM.getOrd());
        out.writeInt(room);
        out.writeByte(FieldOfAddress.END_OF_ADDRESS.getOrd());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        while (true) {
            FieldOfAddress key = FieldOfAddress.getByOrd(in.readByte());
            switch (key) {
                case COUNTRY:
                    this.country = in.readUTF();
                    break;
                case CITY:
                    this.city = in.readUTF();
                    break;
                case STREET:
                    this.street = in.readUTF();
                    break;
                case NUM_OF_HOUSE:
                    this.numOfHouse = in.readInt();
                    break;
                case NUM_OF_BUILDING:
                    this.numOfBuilding = in.readInt();
                    break;
                case LITER:
                    this.liter = in.readUTF();
                    break;
                case ROOM:
                    this.room = in.readInt();
                    break;
            }
            if (key == FieldOfAddress.END_OF_ADDRESS) {
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressExternalizable that = (AddressExternalizable) o;
        return numOfHouse == that.numOfHouse &&
                numOfBuilding == that.numOfBuilding &&
                room == that.room && Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(liter, that.liter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, numOfHouse, numOfBuilding, liter, room);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumOfHouse() {
        return numOfHouse;
    }

    public void setNumOfHouse(int numOfHouse) {
        this.numOfHouse = numOfHouse;
    }

    public int getNumOfBuilding() {
        return numOfBuilding;
    }

    public void setNumOfBuilding(int numOfBuilding) {
        this.numOfBuilding = numOfBuilding;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}

enum FieldOfAnimal {
    IS_AGGRESSIVE_IS_VEGETARIAN((byte) 1),
    NUM_OF_LEGS((byte) 2),
    MAX_VELOCITY((byte) 3),
    NAME((byte) 4),
    TYPE((byte) 5),
    START_OF_OWNER((byte) 6),
    END_OF_ANIMAL((byte) 7);

    private final int ord;

    public static FieldOfAnimal getByOrd(byte i) {
        for (FieldOfAnimal field : FieldOfAnimal.values()) {
            if (field.ord == i) {
                return field;
            }
        }
        return null;
    }

    FieldOfAnimal(byte i) {
        this.ord = i;
    }


    public int getOrd() {
        return ord;
    }
}

enum FieldOfOwner {
    NAME((byte) 1),
    NUMBER((byte) 2),
    GENDER((byte) 3),
    START_OF_ADDRESS((byte) 4),
    END_OF_OWNER((byte) 5);

    private final byte ord;

    FieldOfOwner(byte b) {
        this.ord = b;
    }

    public byte getOrd() {
        return ord;
    }

    public static FieldOfOwner getByOrd(byte b) {
        for (FieldOfOwner field : FieldOfOwner.values()) {
            if (field.ord == b) {
                return field;
            }
        }
        return null;
    }
}


enum FieldOfAddress {
    COUNTRY((byte) 1),
    CITY((byte) 2),
    STREET((byte) 3),
    NUM_OF_HOUSE((byte) 4),
    NUM_OF_BUILDING((byte) 5),
    LITER((byte) 6),
    ROOM((byte) 7),
    END_OF_ADDRESS((byte) 8);

    private final byte ord;

    FieldOfAddress(byte b) {
        this.ord = b;
    }

    public byte getOrd() {
        return ord;
    }

    public static FieldOfAddress getByOrd(byte b) {
        for (FieldOfAddress field : FieldOfAddress.values()) {
            if (field.ord == b) {
                return field;
            }
        }
        return null;
    }
}