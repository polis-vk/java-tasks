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
    public enum MoveType {
        RUN, FLY, CRAWL
    }

    public static class AddressExternalizable implements Externalizable {
        private String street;
        private int house;
        private String phoneNumber;

        public AddressExternalizable() {
        }

        public AddressExternalizable(String street, int house, String phoneNumber) {
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
            AddressExternalizable address = (AddressExternalizable) o;
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

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            writeString(out, street);
            out.writeInt(house);
            writeString(out, phoneNumber);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException {
            street = readString(in);
            house = in.readInt();
            phoneNumber = readString(in);
        }
    }

    private boolean isHappy;
    private boolean isAngry;
    private int legs;
    private String name;
    private MoveType moveType;
    private AddressExternalizable homeAddress;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(boolean isHappy, boolean isAngry, int legs, String name, MoveType moveType, AddressExternalizable homeAddress) {
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

    public AddressExternalizable getHomeAddress() {
        return homeAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable animal = (AnimalExternalizable) o;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(isHappy);
        out.writeBoolean(isAngry);
        out.writeInt(legs);
        writeString(out, name);
        writeString(out, (moveType == null ? null : moveType.name()));
        out.writeObject(homeAddress);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        isHappy = in.readBoolean();
        isAngry = in.readBoolean();
        legs = in.readInt();
        name = readString(in);
        String moveStr = readString(in);
        if (moveStr != null) {
            moveType = MoveType.valueOf(moveStr);
        } else {
            moveType = null;
        }
        homeAddress = (AddressExternalizable) in.readObject();
    }

    private static void writeString(ObjectOutput out, String str) throws IOException{
        boolean isStrNull = (str == null);
        out.writeBoolean(isStrNull);
        if (!isStrNull) {
            out.writeUTF(str);
        }
    }

    private static String readString(ObjectInput in) throws IOException {
        boolean isStrNull = in.readBoolean();
        if (isStrNull) {
            return null;
        }
        return in.readUTF();
    }
}
