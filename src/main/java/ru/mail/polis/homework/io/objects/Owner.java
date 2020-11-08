package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Owner implements Serializable {
    private final String name;
    private final String surname;
    private final int age;

    public Owner() {
        name = "none";
        surname = "none";
        age = 0;
    }

    public Owner(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = (age > 0) ? age : 0;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Owner owner = (Owner) obj;
        return name.equals(owner.name) &&
                surname.equals(owner.surname) &&
                age == owner.age;
     }

    @Override
    public String toString() {
        return "Owner{" +
                "name = " + name +
                "surname = " + surname +
                "age = " + age +
                '}';
    }
}
