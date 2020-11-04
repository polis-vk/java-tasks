package ru.mail.polis.homework.io.objects;

import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

@Getter
public class Heart implements Serializable {

    private boolean isAlive;

    public Heart(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Heart() {
        this.isAlive = true;
    }

    public void kill() {
        isAlive = false;
    }

    public void myWriteObject(ObjectOutputStream oos) throws IOException {
        oos.writeBoolean(isAlive);
    }

    public Heart myReadObject(ObjectInputStream ois) throws IOException {
        this.isAlive = ois.readBoolean();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Heart heart = (Heart) o;
        return isAlive == heart.isAlive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAlive);
    }

    @Override
    public String toString() {
        return "Heart{" +
                "isAlive=" + isAlive +
                '}';
    }
}
