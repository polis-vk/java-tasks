package ru.mail.polis.homework.oop.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Dog extends Animal implements Pet {
    private static final int LEGS = 4;

    public Dog() {
        super(LEGS);
    }

    @Override
    public String say() {
        return "Wow-wow";
    }

    @Override
    public MoveType moveType() {
        return MoveType.RUN;
    }
}