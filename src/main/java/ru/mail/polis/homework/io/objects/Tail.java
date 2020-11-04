package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Tail implements Serializable {

    private final boolean isLong;

    public Tail(boolean isLong) {
        this.isLong = isLong;
    }

    public boolean isLong() {
        return isLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tail tail = (Tail) o;
        return isLong == tail.isLong;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLong);
    }
}
