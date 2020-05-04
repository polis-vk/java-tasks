package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    
    private int age;
    private final String name;
    private List<Integer> weightByLastTenDays;
    private final Kind kind;
    private final Owner owner;
    
    public Animal(int age, String name, List<Integer> weightByLastTenDays, Kind kind, Owner owner) {
        this.age = age;
        this.name = name;
        this.weightByLastTenDays = weightByLastTenDays;
        this.kind = kind;
        this.owner = owner;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Integer> getWeightByLastTenDays() {
        return weightByLastTenDays;
    }
    
    public Kind getKind() {
        return kind;
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Animal that = (Animal) obj;
        return this.age == that.age
                && this.name.equals(that.name)
                && this.weightByLastTenDays.equals(that.weightByLastTenDays)
                && this.owner.equals(that.owner)
                && this.kind.equals(that.kind);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", weightByLastTenDays=" + weightByLastTenDays +
                ", kind=" + kind +
                ", owner=" + owner +
                '}';
    }
    
    public enum Owner {
        PERSON,
        ZOO,
        OWNER_LESS
    }
}
