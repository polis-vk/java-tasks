package ru.mail.polis.homework.oop.vet.type;

import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;
import ru.mail.polis.homework.oop.vet.WildAnimalImpl;

public class Snake extends WildAnimalImpl implements Pet {

    public Snake(String organizationName) {
        super(0, organizationName);
    }

    public Snake() {
        this(null);
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
