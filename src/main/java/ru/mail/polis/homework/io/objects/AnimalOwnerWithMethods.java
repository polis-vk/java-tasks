package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class AnimalOwnerWithMethods implements Serializable {
    private String name;
    private String address;
    private long phoneNumber;

    public AnimalOwnerWithMethods(String name, String address, long phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AnimalOwnerWithMethods)) {
            return false;
        }

        AnimalOwnerWithMethods animalOwner = (AnimalOwnerWithMethods) obj;
        return Objects.equals(this.name, animalOwner.name) && Objects.equals(this.address, animalOwner.address) &&
                this.phoneNumber == animalOwner.phoneNumber;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        byte nameIsNull = (byte) (name == null ? 1 : 0);
        out.writeByte(nameIsNull);
        if (nameIsNull == 0) {
            out.writeUTF(name);
        }

        byte addressIsNull = (byte) (address == null ? 1 : 0);
        out.writeByte(addressIsNull);
        if (addressIsNull == 0) {
            out.writeUTF(address);
        }

        out.writeLong(phoneNumber);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        if (in.readByte() == 0) {
            name = in.readUTF();
        }

        if (in.readByte() == 0) {
            address = in.readUTF();
        }

        phoneNumber = in.readLong();
    }
}
