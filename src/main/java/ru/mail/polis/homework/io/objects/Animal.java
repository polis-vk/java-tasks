package ru.mail.polis.homework.io.objects;

import java.io.*;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    
    private int age;
    private final String name;
    private final Kind kind;
    private Owner owner;
    
    public Animal(int age, String name, Kind kind, Owner owner) {
        this.age = age;
        this.name = name;
        this.kind = kind;
        this.owner = owner;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getName() {
        return name;
    }
    
    public Kind getKind() {
        return kind;
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    @Override
    public String toString() {
        return "Animal{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", kind=" + kind +
                ", owner=" + owner +
                '}';
    }
    
    public static class Kind implements Serializable {
        private final String name;
        private final long populationSize;
        
        public Kind(String name, long populationSize) {
            this.name = name;
            this.populationSize = populationSize;
        }
        
        public String getName() {
            return name;
        }
        
        public long getPopulationSize() {
            return populationSize;
        }
        
        @Override
        public String toString() {
            return "Kind{" +
                    "name='" + name + '\'' +
                    ", populationSize=" + populationSize +
                    '}';
        }
    }
    
    public enum Owner {
        PERSON,
        ZOO,
        OWNER_LESS
    }
}
