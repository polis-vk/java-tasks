package ru.mail.polis.homework.io.objects;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private String name;
    private int age;
    private Fluffiness fluffiness;
    private Stats stats;

    public Animal(String name, int age, Fluffiness fluffiness, Stats stats) {
        this.name = name;
        this.age = age;
        this.fluffiness = fluffiness;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Fluffiness getFluffiness() {
        return fluffiness;
    }

    public void setFluffiness(Fluffiness fluffiness) {
        this.fluffiness = fluffiness;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void serialize(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(name);
        outputStream.writeInt(age);
        outputStream.writeUTF(fluffiness.toString());

        stats.serialize(outputStream);
    }

    public static Animal deserialize(DataInputStream inputStream) throws IOException {
        String name = inputStream.readUTF();
        int age = inputStream.readInt();
        Fluffiness fluffiness = Fluffiness.valueOf(inputStream.readUTF());
        Stats stats = Stats.deserialize(inputStream);
        return new Animal(name, age, fluffiness, stats);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age
                && Objects.equals(name, animal.name)
                && fluffiness == animal.fluffiness
                && Objects.equals(stats, animal.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, fluffiness, stats);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", fluffiness=" + fluffiness +
                ", stats=" + stats +
                '}';
    }
}
