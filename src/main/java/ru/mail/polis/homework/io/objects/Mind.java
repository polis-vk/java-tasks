package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Mind implements Serializable {
    //plug
    private int brain = 0;

    public Mind(int brain) {
        this.brain = brain;
    }

    public int getBrain() {
        return brain;
    }

    public void setBrain(int brain) {
        this.brain = brain;
    }

    @Override
    public String toString() {
        return "Mind{" +
                "brain=" + brain +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mind mind = (Mind) o;
        return getBrain() == mind.getBrain();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBrain());
    }
}
