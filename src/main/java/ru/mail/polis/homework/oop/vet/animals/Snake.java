package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AnimalFromOrganization;
import ru.mail.polis.homework.oop.vet.LegsNumber;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Snake extends AnimalFromOrganization implements Pet {
    private final static String SNAKE_SOUND = "Shhhh";

    public Snake() {
        super(LegsNumber.ZERO.getNumber());
    }

    @Override
    public String say() {
        return SNAKE_SOUND;
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
    }
}
