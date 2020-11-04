package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public enum ColourWithMethods implements Serializable {
    GREY("grey"),
    BLACK("black"),
    WHITE("white"),
    RED("red");
    private String colour;

    ColourWithMethods(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return String.valueOf(colour);
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(colour);
    }

    public ColourWithMethods readObject(ObjectInputStream in) throws IOException {
        return valueOf(in.readUTF());
    }
}