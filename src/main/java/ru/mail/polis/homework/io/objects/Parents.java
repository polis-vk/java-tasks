package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Parents implements Serializable {
    private String mother;
    private String father;

    Parents() {}

    public Parents(String mother, String father) {
        this.mother = mother;
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public String getFather() {
        return father;
    }

    @Override
    public String toString() {
        return '{' +
                "mother='" + mother + '\'' +
                ", father'" + father + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Parents parents = (Parents) obj;
        return mother.equals(parents.mother) &&
                father.equals(parents.father);
    }
}

