package ru.mail.polis.homework.io.objects;

import java.io.*;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal implements Serializable {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        Animal animal = new Animal(1, "Sigismund", new Kind("Fish", 10000000L), Owner.PERSON);
        System.out.println("Before Serialize: " + "\n" + animal);
        
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("output.txt"));
        objectOutputStream.writeObject(animal);
        objectOutputStream.close();
    
        ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream("output.txt"));
        Animal animalRestored = (Animal) objectInputStream.readObject();
        objectInputStream.close();
    
        System.out.println("After Restored From Byte: " + "\n" + animalRestored);
    }
    
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
