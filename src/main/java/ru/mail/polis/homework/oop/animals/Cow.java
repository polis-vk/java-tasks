package ru.mail.polis.homework.oop.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Cow extends Animal implements Pet {
    private static final int LEGS = 4;

    public Cow() {
        super(LEGS);
    }

    @Override
    public String say() {
        return "Moo-Moo";
    }

    @Override
    public MoveType moveType() {
        return MoveType.RUN;
    }
}