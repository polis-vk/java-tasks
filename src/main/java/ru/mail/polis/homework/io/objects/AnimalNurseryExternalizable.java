package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class AnimalNurseryExternalizable implements Externalizable {
    private static final int NAME_BIT_INDEX = 0;
    private static final int ADDRESS_BIT_INDEX = 1;
    private static final int IS_WORK_BIT_INDEX = 2;
    private String name;
    private String address;
    private boolean isWork;

    public AnimalNurseryExternalizable() {}

    public AnimalNurseryExternalizable(String name, String address, boolean isWork) {
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

        AnimalNurseryExternalizable nursery = (AnimalNurseryExternalizable) obj;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        boolean nullName = name == null;
        boolean nullAddress = address == null;

        byte zip = AnimalNurseryWithMethods.byteZip(nullName, NAME_BIT_INDEX, (byte) 0);
        zip = AnimalNurseryWithMethods.byteZip(nullAddress, ADDRESS_BIT_INDEX, zip);
        zip = AnimalNurseryWithMethods.byteZip(!isWork, IS_WORK_BIT_INDEX, zip);
        out.writeByte(zip);

        if (!nullName) {
            out.writeUTF(name);
        }

        if (!nullAddress) {
            out.writeUTF(address);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        byte zip = in.readByte();

        if (AnimalNurseryWithMethods.trueOrNotNullContain(NAME_BIT_INDEX, zip)) {
            name = in.readUTF();
        }

        if (AnimalNurseryWithMethods.trueOrNotNullContain(ADDRESS_BIT_INDEX, zip)) {
            address = in.readUTF();
        }

        isWork = AnimalNurseryWithMethods.trueOrNotNullContain(IS_WORK_BIT_INDEX, zip);
    }
}
