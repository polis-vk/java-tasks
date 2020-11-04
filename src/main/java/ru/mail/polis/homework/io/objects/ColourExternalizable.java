package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public enum ColourExternalizable implements Externalizable {
    GREY("grey"),
    BLACK("black"),
    WHITE("white"),
    RED("red");
    private String colour;

    ColourExternalizable() {}

    ColourExternalizable(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return String.valueOf(colour);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(colour);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        colour = in.readUTF();
    }
}
