package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public enum AnimalKind implements Serializable {
    DOG("DOG"),
    CAT("CAT"),
    MONKEY("MONKEY"),
    ELEPHANT("ELEPHANT"),
    SNAKE("SNAKE"),
    UNKNOWN("UNKNOWN");

    private String kind;

    AnimalKind(String kind) {
        this.kind = kind;
    }

    void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(kind);
    }

    AnimalKind readObject(ObjectInputStream in) throws IOException {
        return valueOf(in.readUTF());
    }

    @Override
    public String toString() {
        return "AnimalKind{" +
                "kind='" + kind + '\'' +
                '}';
    }

}
