package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AbstractWildAnimal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Pigeon extends AbstractWildAnimal implements Pet {
    public Pigeon() {
        super(2);
    }

    @Override
    public String say() {
        return "curls-curls";
    }

    @Override
    public MoveType moveType() {
        return MoveType.FLY;
    }
}
