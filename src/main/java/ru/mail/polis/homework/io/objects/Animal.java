package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private int age;
    private boolean isFriendly;
    private String name;
    private List<Integer> weightByLastDays;
    private Kind kind;
    private Owner owner;
    
    public Animal(int age, boolean isFriendly, String name, List<Integer> weightByLastDays, Kind kind, Owner owner) {
        this.age = age;
        this.isFriendly = isFriendly;
        this.name = name;
        this.weightByLastDays = weightByLastDays;
        this.kind = kind;
        this.owner = owner;
    }
    
    public int getAge() {
        return age;
    }
    
    public boolean isFriendly() {
        return isFriendly;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Integer> getWeightByLastDays() {
        return weightByLastDays;
    }
    
    public Kind getKind() {
        return kind;
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                isFriendly == animal.isFriendly &&
                Objects.equals(name, animal.name) &&
                Objects.equals(weightByLastDays, animal.weightByLastDays) &&
                Objects.equals(kind, animal.kind) &&
                owner == animal.owner;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(age, isFriendly, name, weightByLastDays, kind, owner);
    }
    
    public enum Owner {
        PERSON,
        ZOO,
        OWNER_LESS
    }
}
