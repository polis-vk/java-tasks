package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AnimalFromOrganization;
import ru.mail.polis.homework.oop.vet.LegsNumber;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Pigeon extends AnimalFromOrganization implements Pet {
    private final static String PIGEON_SOUND = "curls-curls";

    public Pigeon() {
        super(LegsNumber.TWO.getNumber());
    }

    @Override
    public String say() {
        return PIGEON_SOUND;
    }

    @Override
    public MoveType moveType() {
        return MoveType.FLY;
    }
}
