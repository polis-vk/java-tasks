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

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
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
            this.owner = (OwnerOfAnimalExternalizable) in.readObject();
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
                type == null ? that.type == null : type == that.type &&
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

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
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
            this.homeAddress = (AddressExternalizable) in.readObject();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerOfAnimalExternalizable that = (OwnerOfAnimalExternalizable) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                gender == null ? that.gender == null : that.gender == gender  &&
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

    @Override
    public void readExternal(ObjectInput in) throws IOException {
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

class AnimalNullables {
    private boolean isNull;
    private boolean isNameNull;
    private boolean isTypeNull;
    private boolean isOwnerNull;

    public AnimalNullables(AnimalExternalizable animalExternalizable) {
        if (animalExternalizable == null) {
            isNull = true;
            return;
        }
        isNameNull = animalExternalizable.getName() == null;
        isTypeNull = animalExternalizable.getType() == null;
        isOwnerNull = animalExternalizable.getOwner() == null;
    }

    public AnimalNullables(Animal animal) {
        if (animal == null) {
            isNull = true;
            return;
        }
        isNameNull = animal.getName() == null;
        isTypeNull = animal.getType() == null;
        isOwnerNull = animal.getOwner() == null;
    }
    public AnimalNullables(AnimalWithMethods animalWithMethods) {
        if (animalWithMethods == null) {
            isNull = true;
            return;
        }
        isNameNull = animalWithMethods.getName() == null;
        isTypeNull = animalWithMethods.getType() == null;
        isOwnerNull = animalWithMethods.getOwner() == null;
    }
    public AnimalNullables(byte inp) {
        isOwnerNull = inp % 2 == 1;
        inp >>= 1;
        isTypeNull = inp % 2 == 1;
        inp >>= 1;
        isNameNull = inp % 2 == 1;
        inp >>= 1;
        isNull = inp % 2 == 1;
    }



    public byte toByte() {
        byte ans = (byte) (isNull ? 1 : 0);
        ans <<= 1;
        ans += (byte) (isNameNull ? 1 : 0);
        ans <<= 1;
        ans += (byte) (isTypeNull ? 1 : 0);
        ans <<= 1;
        ans += (byte) (isOwnerNull ? 1 : 0);
        return ans;
    }


    public boolean isNull() {
        return isNull;
    }

    public boolean isNameNull() {
        return isNameNull;
    }

    public boolean isTypeNull() {
        return isTypeNull;
    }

    public boolean isOwnerNull() {
        return isOwnerNull;
    }
}

class OwnerNullables {
    private final boolean isNameNull;
    private final boolean isPhoneNull;
    private final boolean isGenderNull;
    private final boolean isAddressNull;

    public OwnerNullables(OwnerOfAnimalExternalizable owner) {
        isNameNull = owner.getName() == null;
        isPhoneNull = owner.getPhoneNumber() == null;
        isGenderNull = owner.getGender() == null;
        isAddressNull = owner.getHomeAddress() == null;
    }

    public OwnerNullables(OwnerOfAnimalWithMethods ownerOfAnimalWithMethods) {
        isNameNull = ownerOfAnimalWithMethods.getName() == null;
        isPhoneNull = ownerOfAnimalWithMethods.getPhoneNumber() == null;
        isGenderNull = ownerOfAnimalWithMethods.getGender() == null;
        isAddressNull = ownerOfAnimalWithMethods.getHomeAddress() == null;
    }

    public OwnerNullables(OwnerOfAnimal ownerOfAnimal) {
        isNameNull = ownerOfAnimal.getName() == null;
        isPhoneNull = ownerOfAnimal.getPhoneNumber() == null;
        isGenderNull = ownerOfAnimal.getGender() == null;
        isAddressNull = ownerOfAnimal.getHomeAddress() == null;
    }

    public OwnerNullables(byte inp) {
        isNameNull = inp % 2 == 1;
        inp >>= 1;
        isPhoneNull = inp % 2 == 1;
        inp >>= 1;
        isGenderNull = inp % 2 == 1;
        inp >>= 1;
        isAddressNull = inp % 2 == 1;
    }



    public byte toByte() {
        byte ans = (byte) (isAddressNull ? 1 : 0);
        ans <<= 1;
        ans += (byte) (isGenderNull ? 1 : 0);
        ans <<= 1;
        ans += (byte) (isPhoneNull ? 1 : 0);
        ans <<= 1;
        ans += (byte) (isNameNull ? 1 : 0);
        return ans;
    }

    public boolean isNameNull() {
        return isNameNull;
    }

    public boolean isPhoneNull() {
        return isPhoneNull;
    }

    public boolean isGenderNull() {
        return isGenderNull;
    }

    public boolean isAddressNull() {
        return isAddressNull;
    }
}

class AddressNullables {
    private final boolean isCountryNull;
    private final boolean isCityNull;
    private final boolean isStreetNull;
    private final boolean isLiterNull;

    public AddressNullables(AddressExternalizable addressExternalizable) {
        isCountryNull = addressExternalizable.getCountry() == null;
        isCityNull = addressExternalizable.getCity() == null;
        isStreetNull = addressExternalizable.getStreet() == null;
        isLiterNull = addressExternalizable.getLiter() == null;
    }

    public AddressNullables(AddressWithMethods addressWithMethods) {
        isCountryNull = addressWithMethods.getCountry() == null;
        isCityNull = addressWithMethods.getCity() == null;
        isStreetNull = addressWithMethods.getStreet() == null;
        isLiterNull = addressWithMethods.getLiter() == null;
    }

    public AddressNullables(Address address) {
        isCountryNull = address.getCountry() == null;
        isCityNull = address.getCity() == null;
        isStreetNull = address.getStreet() == null;
        isLiterNull = address.getLiter() == null;
    }

    public AddressNullables(byte inp) {
        isCountryNull = inp % 2 == 1;
        inp >>= 1;
        isCityNull = inp % 2 == 1;
        inp >>= 1;
        isStreetNull = inp % 2 == 1;
        inp >>= 1;
        isLiterNull = inp % 2 == 1;
    }



    public byte toByte() {
        byte ans = (byte) (isLiterNull ? 1 : 0);
        ans <<= 1;
        ans += isStreetNull ? 1 : 0;
        ans <<= 1;
        ans += isCityNull ? 1 : 0;
        ans <<= 1;
        ans += isCountryNull ? 1 : 0;
        return ans;
    }

    public boolean isCountryNull() {
        return isCountryNull;
    }

    public boolean isCityNull() {
        return isCityNull;
    }

    public boolean isStreetNull() {
        return isStreetNull;
    }

    public boolean isLiterNull() {
        return isLiterNull;
    }
}