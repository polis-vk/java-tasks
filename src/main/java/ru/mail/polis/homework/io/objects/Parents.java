package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.Objects;

public class Parents implements Serializable {
    private final boolean doHaveFather;
    private final boolean doHaveMother;

    Parents() {
        this.doHaveFather = true;
        this.doHaveMother = true;
    }

    Parents(boolean doHaveFather, boolean doHaveMother) {
        this.doHaveFather = doHaveFather;
        this.doHaveMother = doHaveMother;
    }

    public boolean isDoHaveFather() {
        return doHaveFather;
    }

    public boolean isDoHaveMother() {
        return doHaveMother;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parents parents = (Parents) o;
        return doHaveFather == parents.doHaveFather &&
                doHaveMother == parents.doHaveMother;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doHaveFather, doHaveMother);
    }

    @Override
    public String toString() {
        return "Parents{" +
                "doHaveFather=" + doHaveFather +
                ", doHaveMother=" + doHaveMother +
                '}';
    }
}
