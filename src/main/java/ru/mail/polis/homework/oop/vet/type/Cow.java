package ru.mail.polis.homework.oop.vet.type;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Cow extends Animal implements Pet {

    public Cow() {
        super(4);
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
