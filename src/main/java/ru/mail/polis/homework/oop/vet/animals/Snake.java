package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AbstractWildAnimal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Snake extends AbstractWildAnimal implements Pet {
    public Snake() {
        super(0);
    }

    @Override
    public String say() {
        return "Shhhh";
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
    }
}
