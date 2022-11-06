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
        AnimalNullables animalNullables = new AnimalNullables(this);
        out.writeByte(animalNullables.toByte());
        byte twoBools = (byte) ((isAggressive ? 1 : 0) << 1 + (isVegetarian ? 1 : 0));
        out.writeByte(twoBools);
        out.writeInt(numOfLegs);
        out.writeDouble(maxVelocity);
        if (!animalNullables.isNameNull()) {
            out.writeUTF(name);
        }
        if (!animalNullables.isTypeNull()) {
            out.writeByte(type.getOrd());
        }
        if (!animalNullables.isOwnerNull()) {
            out.writeObject(owner);
        }
    }

    public void readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        AnimalNullables animalNullables = new AnimalNullables(in.readByte());
        byte twoBools = in.readByte();
        isVegetarian = twoBools % 2 == 1;
        twoBools >>= 1;
        isAggressive = twoBools % 2 == 1;
        this.numOfLegs = in.readInt();
        this.maxVelocity = in.readDouble();
        if (!animalNullables.isNameNull()) {
            this.name = in.readUTF();
        }
        if (!animalNullables.isTypeNull()) {
            this.type = TypeOfAnimal.getByOrd(in.readByte());
        }
        if (!animalNullables.isOwnerNull()) {
            this.owner = (OwnerOfAnimalWithMethods) in.readObject();
        }
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

    public OwnerOfAnimalWithMethods getOwner() {
        return owner;
    }

    public void setOwner(OwnerOfAnimalWithMethods owner) {
        this.owner = owner;
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
        ans.append("Номер телефона - ").append(phoneNumber == null ? "нет" : phoneNumber).append("\n");
        ans.append("Пол - ").append(gender == null ? "нет" : gender.toString()).append("\n");
        ans.append("Домашний адрес - ").append(homeAddress == null ? "нет" : homeAddress.getFullAddress()).append("\n");
        return ans.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerOfAnimalWithMethods that = (OwnerOfAnimalWithMethods) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(homeAddress, that.homeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, gender, homeAddress);
    }

    public void writeObject(ObjectOutput out) throws IOException {
        OwnerNullables ownerNullables = new OwnerNullables(this);
        out.writeByte(ownerNullables.toByte());
        if (!ownerNullables.isNameNull()) {
            out.writeUTF(name);
        }
        if (!ownerNullables.isPhoneNull()) {
            out.writeUTF(phoneNumber);
        }
        if (!ownerNullables.isGenderNull()) {
            out.writeByte(gender.ordinal());
        }
        if (!ownerNullables.isAddressNull()) {
            out.writeObject(homeAddress);
        }
    }

    public void readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        OwnerNullables ownerNullables = new OwnerNullables(in.readByte());
        if (!ownerNullables.isNameNull()) {
            this.name = in.readUTF();
        }
        if (!ownerNullables.isPhoneNull()) {
            this.phoneNumber = in.readUTF();
        }
        if (!ownerNullables.isGenderNull()) {
            this.gender = Gender.values()[in.readByte()];
        }
        if (!ownerNullables.isAddressNull()) {
            this.homeAddress = (AddressWithMethods) in.readObject();
        }
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

    public AddressWithMethods getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(AddressWithMethods homeAddress) {
        this.homeAddress = homeAddress;
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
        if (country != null ) {
            ans.append(country).append(", ");
        }
        if (city != null) {
            ans.append(city).append(", ");
        }
        if (street != null) {
            ans.append(street).append(", ");
        }
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
        AddressWithMethods address = (AddressWithMethods) o;
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


    public void writeObject(ObjectOutput out) throws IOException {
        AddressNullables addressNullables = new AddressNullables(this);
        out.writeByte(addressNullables.toByte());
        if (!addressNullables.isCountryNull()) {
            out.writeUTF(country);
        }
        if (!addressNullables.isCityNull()) {
            out.writeUTF(city);
        }
        if (!addressNullables.isStreetNull()) {
            out.writeUTF(street);
        }
        out.writeInt(numOfHouse);
        out.writeInt(numOfBuilding);
        if (!addressNullables.isLiterNull()) {
            out.writeUTF(liter);
        }
        out.writeInt(room);
    }

    public void readObject(ObjectInput in) throws IOException {
        AddressNullables addressNullables = new AddressNullables(in.readByte());
        if (!addressNullables.isCountryNull()) {
            this.country = in.readUTF();
        }
        if (!addressNullables.isCityNull()) {
            this.city = in.readUTF();
        }
        if (!addressNullables.isStreetNull()) {
            this.street = in.readUTF();
        }
        this.numOfHouse = in.readInt();
        this.numOfBuilding = in.readInt();
        if (!addressNullables.isLiterNull()) {
            this.liter = in.readUTF();
        }
        this.room = in.readInt();
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