package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public enum Colour implements Serializable {
    WHITE("WHITE"),
    BLACK("BLACK"),
    RED("RED"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    UNKNOWN("UNKNOWN");

    private String value;

    Colour(String value) {
        this.value = value;
    }

    void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(value);
    }

    Colour readObject(ObjectInputStream in) throws IOException {
        return valueOf(in.readUTF());
    }

    @Override
    public String toString() {
        return "Colour{" +
                "value='" + value + '\'' +
                '}';
    }
}
