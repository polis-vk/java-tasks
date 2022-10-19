package ru.mail.polis.homework.oop.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;
import ru.mail.polis.homework.oop.vet.WildAnimal;

public class Pigeon extends Animal implements WildAnimal, Pet {
    private static final int LEGS = 2;
    private static String organization = null;

    public Pigeon() {
        super(LEGS);
    }

    @Override
    public String say() {
        return "curls-curls";
    }

    @Override
    public MoveType moveType() {
        return MoveType.FLY;
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