package ru.mail.polis.homework.oop.vet;

/**
 * Дефолтный класс животного от которого необходимо наследоваться
 */
public abstract class Animal {

    private final int legs;

    public Animal(int legs) {
        this.legs = legs;
    }

    public abstract String say();

    public abstract MoveType moveType();

    public int getLegs() {
        return legs;
    }
}
