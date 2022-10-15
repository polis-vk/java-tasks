package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.AnimalFromOrganization;
import ru.mail.polis.homework.oop.vet.LegsNumber;
import ru.mail.polis.homework.oop.vet.MoveType;

public class Kangaroo extends AnimalFromOrganization {
    private final static String KANGAROO_SOUND = "Shha";

    public Kangaroo() {
        super(LegsNumber.TWO.getNumber());
    }

    @Override
    public String say() {
        return KANGAROO_SOUND;
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
    }
}
