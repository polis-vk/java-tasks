package ru.mail.polis.homework.oop.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;
import ru.mail.polis.homework.oop.vet.WildAnimal;

public class Snake extends Animal implements WildAnimal, Pet {
    private static final int LEGS = 0;
    private static String organization = null;

    public Snake() {
        super(LEGS);
    }

    @Override
    public String say() {
        return "Shhhh";
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
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