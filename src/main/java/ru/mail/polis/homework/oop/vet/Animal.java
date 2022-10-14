package ru.mail.polis.homework.oop.vet;

/**
 * Дефолтный класс животного от которого необходимо наследоваться
 */
public abstract class Animal {
    private final int legs;

    /*
     * Если вдруг для дочернего класса не переопределили методы say(), MoveType() - выводятся дефолтные методы родитель-
     * ского класса.
     */
    public Animal(int legs) {
        this.legs = legs;
    }

    public String say() {
        return "Default";
    }

    public MoveType moveType() {
        return MoveType.DEFAULT;
    }

    public int getLegs() {
        return legs;
    }
}
