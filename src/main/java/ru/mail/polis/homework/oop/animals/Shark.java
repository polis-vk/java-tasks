package ru.mail.polis.homework.oop.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.WildAnimal;

public class Shark extends Animal implements WildAnimal {
    private static final int LEGS = 0;
    private static String organization = null;

    public Shark() {
        super(LEGS);
    }

    @Override
    public String say() {
        return "Clack Clack";
    }

    @Override
    public MoveType moveType() {
        return MoveType.SWIM;
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