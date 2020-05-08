package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Owner implements Serializable {
    private final String name;
    private int age;
    private String location = "world";

    public Owner(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        try {
            if (age < this.age) {
                throw new IllegalArgumentException("Age lower");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
            age = this.age;
        }
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location != null) {
            this.location = location;
        }
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
        return this.name.equals(owner.name)
          && this.age == owner.age
          && this.location.equals(owner.location);

    }

    @Override
    public String toString() {
        return "Owner{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", location='" + location + '\'' +
          '}';
    }
}
