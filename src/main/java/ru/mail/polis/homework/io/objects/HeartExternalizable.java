package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class HeartExternalizable implements Externalizable {

    private boolean isAlive;

    public HeartExternalizable(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public HeartExternalizable() {
        this.isAlive = true;
    }

    public void kill() {
        isAlive = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartExternalizable heart = (HeartExternalizable) o;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(isAlive);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        isAlive = in.readBoolean();
    }
}
