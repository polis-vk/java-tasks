package ru.mail.polis.homework.oop.vet.type;

import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;
import ru.mail.polis.homework.oop.vet.WildAnimalImpl;

public class Pigeon extends WildAnimalImpl implements Pet {

    public Pigeon(String organizationName) {
        super(2, organizationName);
    }

    public Pigeon() {
        this(null);
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
