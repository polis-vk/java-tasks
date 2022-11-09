package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Owner implements Serializable {
    private String surname;
    private String name;
    private int age;

    public Owner(String surname, String name, int age) {
        this.surname = surname;
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return age == owner.age && Objects.equals(surname, owner.surname) && Objects.equals(name, owner.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, age);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }
}
