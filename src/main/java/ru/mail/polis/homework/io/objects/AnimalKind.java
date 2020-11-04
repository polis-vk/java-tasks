package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum AnimalKind implements Serializable {
    DOG("DOG"),
    CAT("CAT"),
    MONKEY("MONKEY"),
    ELEPHANT("ELEPHANT"),
    SNAKE("SNAKE"),
    UNKNOWN("UNKNOWN");

    private final String kind;

    private static final List<AnimalKind> VALUES =
            Arrays.asList(AnimalKind.values());
    private static final int SIZE = VALUES.size();

    AnimalKind(String kind) {
        this.kind = kind;
    }

    void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(kind);
    }

    AnimalKind readObject(ObjectInputStream in) throws IOException {
        return valueOf(in.readUTF());
    }

    public static AnimalKind getRandom(Random random) {
        return VALUES.get(random.nextInt(SIZE));
    }

    @Override
    public String toString() {
        return "AnimalKind{" +
                "kind='" + kind + '\'' +
                '}';
    }

}
