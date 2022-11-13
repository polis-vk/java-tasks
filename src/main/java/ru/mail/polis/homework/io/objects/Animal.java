package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами (минимум 2 булеана и еще что-то), строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 6 полей с разными типами.
 * Во всех полях, кроме примитивов должны поддерживаться null-ы
 * 1 тугрик
 */
public class Animal implements Serializable {
    private final AnimalNursery nursery;
    private final AnimalType type;
    private final String name;
    private final int age;
    private final boolean isSeek;
    private final boolean inNursery;

    public Animal(AnimalNursery nursery, AnimalType type, String name, int age, boolean isSeek, boolean inNursery) {
        this.nursery = nursery;
        this.type = type;
        this.name = name;
        this.age = age;
        this.isSeek = isSeek;
        this.inNursery = inNursery;
    }


    public AnimalNursery getNursery() {
        return nursery;
    }

    public AnimalType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isSeek() {
        return isSeek;
    }

    public boolean isInNursery() {
        return inNursery;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nursery, type, name, age, isSeek, inNursery);
    }

    @Override
    public String toString() {
        return "{\"AnimalNursery\":" + nursery + "," +
                "\"AnimalType\":" + "\"" + type + "\"," +
                "\"Name\":" + "\"" + name + "\"," +
                "\"Age\":" + age + "," +
                "\"isSeek\":" + "\"" + isSeek + "\"," +
                "\"inNursery\":" + "\"" + inNursery + "\"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Animal animal = (Animal) obj;
        return Objects.equals(nursery, animal.nursery) &&
                Objects.equals(type, animal.type) &&
                Objects.equals(name, animal.name) &&
                age == animal.age &&
                isSeek == animal.isSeek &&
                inNursery == animal.inNursery;
    }


    public static byte byteZip(boolean maskOne, int index, byte zipObj) {
        if (maskOne) {
            return (byte) (zipObj & ~(1 << index));
        }
        return (byte) (zipObj | 1 << index);
    }

    public static boolean trueOrNotNullContain(int index, byte zipObj) {
        return (zipObj & (1 << index)) != 0;
    }
}
