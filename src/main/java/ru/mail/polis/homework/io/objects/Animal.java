package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private final int HP;
    private final List<Type> type;
    private final String name;
    private final List<Animal> evolutions;


    public Animal(int HP, String name, List<Type> type) {
        this(HP, name, type, null);
    }

    public Animal(int HP, String name, List<Type> type, List<Animal> evolutions) {
        this.HP = HP;
        this.name = name;
        this.type = type;
        this.evolutions = evolutions;
    }

    public int getHP() {
        return HP;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type.stream()
                .map(Type::toString)
                .reduce("", (acc, t) -> acc.isEmpty() ? t : acc + "/" + t);
    }

    public String getEvolutions() {
        return evolutions != null
                ? evolutions.stream()
                    .map(Animal::getName)
                    .reduce(this.getName(), (acc, name) -> acc + " -> " + name)
                : "There is no evolution";
    }

    @Override
    public String toString() {
        return "\nName: " + name + "\n" +
                "Type: " + this.getType() + "\n" +
                "HP:" + HP + "\n" +
                "Evolution: " + this.getEvolutions() + "\n";
    }


}
