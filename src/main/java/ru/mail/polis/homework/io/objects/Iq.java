package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Iq implements Serializable {
    private final int iqSize;

    public Iq(int iq) {
        this.iqSize = iq;
    }

    public int getIqSize() {
        return iqSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Iq iq = (Iq) o;
        return Objects.equals(getIqSize(), iq.getIqSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIqSize());
    }
}
