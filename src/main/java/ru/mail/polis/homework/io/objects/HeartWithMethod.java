package ru.mail.polis.homework.io.objects;

import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

@Getter
public class HeartWithMethod implements Serializable {

    private boolean isAlive;

    public HeartWithMethod(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public HeartWithMethod() {
        this.isAlive = true;
    }

    public void kill() {
        isAlive = false;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeBoolean(isAlive);
    }

    private void readObject(ObjectInputStream ois) throws IOException {
        isAlive = ois.readBoolean();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartWithMethod that = (HeartWithMethod) o;
        return isAlive == that.isAlive;
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
