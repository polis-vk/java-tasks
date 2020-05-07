package ru.mail.polis.homework.io.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal {
    private final int age;
    private final String name;
    private final Kind kind;
    private boolean isPet;
    private final List<Float> sizes;  //Список размеров (что бы это ни было, контекст неважен)
    private final Human owner;        // Некоторый сапмописный объект

    public Animal(int age, String name, Kind kind, int ownerAge, String ownerName, List<Float> sizes, boolean isPet) {
        this.age = age;
        this.name = name;
        this.kind = kind;
        this.isPet = isPet;
        this.owner = new Human(ownerName, ownerAge);
        this.sizes = new ArrayList<>(sizes);
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

    public List<Float> getSizes() {
        return sizes;
    }

    public boolean isPet() {
        return isPet;
    }

    public Human getOwner() {
        return owner;
    }
}
