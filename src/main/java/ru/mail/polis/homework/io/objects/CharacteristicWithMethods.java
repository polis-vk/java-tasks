package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum CharacteristicWithMethods implements Serializable {
    STUPID("Stupid"),
    SMART("smart"),
    PASSIVE("passive"),
    AGGRESSIVE("aggressive"),
    PEACEFUL("peaceful"),
    HOSTILE("hostile");

    String characteristic;

    CharacteristicWithMethods(String characteristic) {
        this.characteristic = characteristic;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(characteristic);
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        characteristic = in.readUTF();
    }

    @Override
    public String toString() {
        return characteristic;
    }
}
