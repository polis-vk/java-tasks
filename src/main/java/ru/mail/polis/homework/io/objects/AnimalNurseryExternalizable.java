package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class AnimalNurseryExternalizable implements Externalizable {
    private static final int ADDRESS_BIT_INDEX = 0;
    private static final int IS_WORK_BIT_INDEX = 1;
    private String address;
    private boolean isWork;

    public AnimalNurseryExternalizable() {
    }

    public AnimalNurseryExternalizable(String address, boolean isWork) {
        this.address = address;
        this.isWork = isWork;
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
        return Objects.equals(address, nursery.address) &&
                isWork == nursery.isWork;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, isWork);
    }

    @Override
    public String toString() {
        return "{\"address\":" + "\"" + address + "\"," +
                "\"isWork\":" + "\"" + isWork + "\"}";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        boolean nullAddress = address == null;

        byte zip = Animal.byteZip(nullAddress, ADDRESS_BIT_INDEX, (byte) 0);
        zip = Animal.byteZip(!isWork, IS_WORK_BIT_INDEX, zip);

        out.writeByte(zip);

        if (!nullAddress) {
            out.writeUTF(address);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        byte zip = in.readByte();

        if (Animal.trueOrNotNullContain(ADDRESS_BIT_INDEX, zip)) {
            address = in.readUTF();
        }

        isWork = Animal.trueOrNotNullContain(IS_WORK_BIT_INDEX, zip);
    }
}
