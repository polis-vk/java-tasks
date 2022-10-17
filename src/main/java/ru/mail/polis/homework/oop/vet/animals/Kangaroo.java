package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AbstractWildAnimal;
import ru.mail.polis.homework.oop.vet.MoveType;

public class Kangaroo extends AbstractWildAnimal {
    public Kangaroo() {
        super(2);
    }

    @Override
    public String say() {
        return "Shha";
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
    }
}
