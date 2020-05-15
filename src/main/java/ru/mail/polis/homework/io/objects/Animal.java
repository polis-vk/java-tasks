package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    private int age;
    private final String name;
    private final int birthday;
    private final AnimalTypes type;
    private final List<Rewards> rewards;

    public Animal(int age, String name, int birthday, AnimalTypes type, List<Rewards> rewards) {
        this.age = age;
        this.name = name;
        this.birthday = birthday;
        this.type = type;
        this.rewards = rewards;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public int getBirthday() {
        return birthday;
    }

    public AnimalTypes getType() {
        return type;
    }

    public List<Rewards> getRewards() {
        return rewards;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", type=" + type +
                ", rewards=" + rewards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                birthday == animal.birthday &&
                name.equals(animal.name) &&
                type == animal.type &&
                rewards.equals(animal.rewards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, birthday, type, rewards);
    }
}
