package ru.mail.polis.homework.oop.vet;

/**
 * Дефолтный класс животного от которого необходимо наследоваться
 */
public class Animal {
    private final int legs;

    public Animal(int legs) {
        this.legs = legs;
    }

    public String say() {
        return "Default say animal";
    }

    public MoveType moveType() {
        return MoveType.RUN;
    }

    public int getLegs() {
        return legs;
    }
}
