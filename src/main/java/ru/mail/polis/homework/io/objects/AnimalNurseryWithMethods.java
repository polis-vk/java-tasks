package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class AnimalNurseryWithMethods implements Serializable {
    private static final int NAME_BIT_INDEX = 0;
    private static final int ADDRESS_BIT_INDEX = 1;
    private static final int IS_WORK_BIT_INDEX = 2;
    private String name;
    private String address;
    private boolean isWork;

    public AnimalNurseryWithMethods(String name, String address, boolean isWork) {
        this.name = name;
        this.address = address;
        this.isWork = isWork;
    }

    public boolean isWork() {
        return isWork;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AnimalNurseryWithMethods nursery = (AnimalNurseryWithMethods) obj;
        return Objects.equals(name, nursery.name) &&
                Objects.equals(address, nursery.address) &&
                isWork == nursery.isWork;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, isWork);
    }

    @Override
    public String toString() {
        return "{\"name\":" + "\"" + name + "\"," +
                "\"address\":" + "\"" + address + "\"," +
                "\"isWork\":" + "\"" + isWork + "\"}";
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        boolean nullName = name == null;
        boolean nullAddress = address == null;

        byte zip = byteZip(nullName, NAME_BIT_INDEX, (byte) 0);
        zip = byteZip(nullAddress, ADDRESS_BIT_INDEX, zip);
        zip = byteZip(!isWork, IS_WORK_BIT_INDEX, zip);
        out.writeByte(zip);

        if (!nullName) {
            out.writeUTF(name);
        }

        if (!nullAddress) {
            out.writeUTF(address);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException {
        byte zip = in.readByte();

        if (trueOrNotNullContain(NAME_BIT_INDEX, zip)) {
            name = in.readUTF();
        }

        if (trueOrNotNullContain(ADDRESS_BIT_INDEX, zip)) {
            address = in.readUTF();
        }

        isWork = trueOrNotNullContain(IS_WORK_BIT_INDEX, zip);
    }

    public static byte byteZip(boolean maskOne, int index, byte zipObj) {
        if (maskOne) {
            return (byte) (zipObj & ~(1 << index));
        }
        return (byte) (zipObj | 1 << index);
    }

    public static boolean trueOrNotNullContain(int index, byte zipObj) {
        return (zipObj & (1 << index)) != 0;
    }

}
