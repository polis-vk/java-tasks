package ru.mail.polis.homework.oop.animals;

import ru.mail.polis.homework.oop.types.MoveType;
import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.Pet;

public class Cow extends Animal implements Pet {
    public Cow(int legs) {
        super(legs);
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
