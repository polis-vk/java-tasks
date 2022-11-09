package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class AnimalOwnerExternalizable implements Externalizable {
    private String name;
    private String address;
    private long phoneNumber;

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    public AnimalOwnerExternalizable() {
    }

    public AnimalOwnerExternalizable(String name, String address, long phoneNumber) {
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
        if (!(obj instanceof AnimalOwnerExternalizable)) {
            return false;
        }

        AnimalOwnerExternalizable animalOwner = (AnimalOwnerExternalizable) obj;
        return Objects.equals(this.name, animalOwner.name) && Objects.equals(this.address, animalOwner.address) &&
                this.phoneNumber == animalOwner.phoneNumber;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        byte nameIsNull = (byte) (name == null ? TRUE : FALSE);
        out.writeByte(nameIsNull);
        if (nameIsNull == FALSE) {
            out.writeUTF(name);
        }

        byte addressIsNull = (byte) (address == null ? TRUE : FALSE);
        out.writeByte(addressIsNull);
        if (addressIsNull == FALSE) {
            out.writeUTF(address);
        }

        out.writeLong(phoneNumber);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        if (in.readByte() == FALSE) {
            name = in.readUTF();
        }

        if (in.readByte() == FALSE) {
            address = in.readUTF();
        }

        phoneNumber = in.readLong();
    }
}
