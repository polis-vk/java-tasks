package ru.mail.polis.homework.io.objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля
 * 1 балл
 */
public class Animal {
    private final String name;
    private final AnimalType type;
    private final AnimalOwner owner;

    public Animal(String name, AnimalType type, AnimalOwner owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }
}
