package ru.mail.polis.homework.oop.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.WildAnimal;

public class Kangaroo extends Animal implements WildAnimal {
    private static final int LEGS = 2;
    private static String organization = null;

    public Kangaroo() {
        super(LEGS);
    }

    @Override
    public String say() {
        return "Shha";
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
    }

    @Override
    public String getOrganizationName() {
        return organization;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        organization = organizationName;
    }
}