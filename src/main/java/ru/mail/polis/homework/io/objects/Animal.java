package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами (не энам).
 * Всего должно быть минимум 4 поля с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    private final int HP;
    private final List<Type> type;
    private final String name;
    private final List<Animal> evolutions;


    public Animal(int HP, String name, List<Type> type) {
        this(HP, name, type, new ArrayList<>());
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

    public List<Animal> getEvolutions() {
        return evolutions;
    }

    @Override
    public String toString() {
        String evoLine = evolutions != null
                ? evolutions.stream()
                .map(Animal::getName)
                .reduce(this.getName(), (acc, name) -> acc + " -> " + name)
                : "There is no evolution";

        return "\nName: " + name + "\n" +
                "Type: " + this.getType() + "\n" +
                "HP:" + HP + "\n" +
                "Evolution: " + evoLine + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(HP, name, type, evolutions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Animal))
            return false;
        Animal animal = (Animal) o;
        return HP == animal.HP &&
                Objects.equals(name, animal.name) &&
                Objects.equals(type, animal.type) &&
                Objects.equals(evolutions, animal.evolutions);
    }
}
