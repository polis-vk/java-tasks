package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Colour implements Serializable {
    WHITE("WHITE"),
    BLACK("BLACK"),
    RED("RED"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    UNKNOWN("UNKNOWN");

    private final String value;

    private static final List<Colour> VALUES =
            Arrays.asList(Colour.values());
    private static final int SIZE = VALUES.size();

    Colour(String value) {
        this.value = value;
    }

    void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(value);
    }

    Colour readObject(ObjectInputStream in) throws IOException {
        return valueOf(in.readUTF());
    }

    public static Colour getRandom(Random random) {
        return VALUES.get(random.nextInt(SIZE));
    }

    @Override
    public String toString() {
        return "Colour{" +
                "value='" + value + '\'' +
                '}';
    }
}
