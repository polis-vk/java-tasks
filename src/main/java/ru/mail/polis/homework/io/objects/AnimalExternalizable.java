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
        private static final int IS_STREET_NULL_POS = 0;
        private static final int IS_PHONE_NULL_POS = 1;
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
            byte booleans = setBoolAt((byte) 0, IS_STREET_NULL_POS, street == null);
            booleans = setBoolAt(booleans, IS_PHONE_NULL_POS, phoneNumber == null);
            out.writeByte(booleans);
            if (!getBoolAt(booleans, IS_STREET_NULL_POS)) out.writeUTF(street);
            out.writeInt(house);
            if (!getBoolAt(booleans, IS_PHONE_NULL_POS)) out.writeUTF(phoneNumber);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException {
            byte booleans = in.readByte();
            if (!getBoolAt(booleans, IS_STREET_NULL_POS)) street = in.readUTF();
            house = in.readInt();
            if (!getBoolAt(booleans, IS_PHONE_NULL_POS)) phoneNumber = in.readUTF();
        }
    }

    private final static int IS_HAPPY_POS = 0;
    private final static int IS_ANGRY_POS = 1;
    private final static int IS_NAME_NULL_POS = 2;
    private final static int IS_MOVE_NULL_POS = 3;

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
        byte booleans = setBoolAt((byte) 0, IS_HAPPY_POS, isHappy);
        booleans = setBoolAt(booleans, IS_ANGRY_POS, isAngry);
        booleans = setBoolAt(booleans, IS_NAME_NULL_POS, name == null);
        booleans = setBoolAt(booleans, IS_MOVE_NULL_POS, moveType == null);

        out.writeByte(booleans);
        out.writeInt(legs);
        if (!getBoolAt(booleans, IS_NAME_NULL_POS)) out.writeUTF(name);
        if (!getBoolAt(booleans, IS_MOVE_NULL_POS)) out.writeUTF(moveType.name());
        out.writeObject(homeAddress);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte booleans = in.readByte();
        isHappy = getBoolAt(booleans, IS_HAPPY_POS);
        isAngry = getBoolAt(booleans, IS_ANGRY_POS);
        legs = in.readInt();
        if (!getBoolAt(booleans, IS_NAME_NULL_POS)) name = in.readUTF();
        if (!getBoolAt(booleans, IS_MOVE_NULL_POS)) moveType = MoveType.valueOf(in.readUTF());
        homeAddress = (AddressExternalizable) in.readObject();
    }

    private static byte setBoolAt(byte byt, int i, boolean bool) {
        if (bool) {
            return (byte) (byt | (1 << i));
        }
        return (byte) (byt & ~(1 << i));
    }

    private static boolean getBoolAt(byte byt, int i) {
        return (byt & (1 << i)) != 0;
    }
}
