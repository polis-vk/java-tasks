package ru.mail.polis.homework.io.objects;

import java.io.*;

public enum CharacteristicExternalizable implements Externalizable {
    STUPID("Stupid"),
    SMART("smart"),
    PASSIVE("passive"),
    AGGRESSIVE("aggressive"),
    PEACEFUL("peaceful"),
    HOSTILE("hostile");

    String characteristic;

    CharacteristicExternalizable(String characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(characteristic);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        characteristic = in.readUTF();
    }

    @Override
    public String toString() {
        return characteristic;
    }
}
