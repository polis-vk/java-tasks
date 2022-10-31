package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Serializable {

    private String string;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String string) {
        this.string = string;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeChars(string);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.string = (String) in.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return Objects.equals(string, that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }
}
