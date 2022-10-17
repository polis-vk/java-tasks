package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AbstractWildAnimal;
import ru.mail.polis.homework.oop.vet.MoveType;

public class Shark extends AbstractWildAnimal {
    public Shark() {
        super(0);
    }

    @Override
    public String say() {
        return "Clack Clack";
    }

    @Override
    public MoveType moveType() {
        return MoveType.SWIM;
    }
}
