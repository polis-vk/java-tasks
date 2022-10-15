package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AnimalFromOrganization;
import ru.mail.polis.homework.oop.vet.LegsNumber;
import ru.mail.polis.homework.oop.vet.MoveType;

public class Shark extends AnimalFromOrganization {
    private final static String SHARK_SOUND = "Clack Clack";

    public Shark() {
        super(LegsNumber.ZERO.getNumber());
    }

    @Override
    public String say() {
        return SHARK_SOUND;
    }

    @Override
    public MoveType moveType() {
        return MoveType.SWIM;
    }
}
