package ru.mail.polis.homework.oop.vet;

import ru.mail.polis.homework.oop.vet.types.MoveType;

/**
 * Дефолтный класс животного от которого необходимо наследоваться
 */
public class Animal {
    private final int legs;
    private final String voice;
    private final MoveType moveType;

    public Animal(int legs, String voice, MoveType moveType) {
        this.legs = legs;
        this.voice = voice;
        this.moveType = moveType;
    }

    public String say() {
        return voice;
    }

    public MoveType moveType() {
        return moveType;
    }

    public int getLegs() {
        return legs;
    }
}
