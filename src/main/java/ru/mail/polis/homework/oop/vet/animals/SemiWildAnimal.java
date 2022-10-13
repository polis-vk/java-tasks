package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.types.MoveType;
import ru.mail.polis.homework.oop.vet.Animal;

public class SemiWildAnimal extends Animal implements Pet, WildAnimal {
    private String organizationName;

    public SemiWildAnimal(int legs, String voice, MoveType moveType) {
        super(legs, voice, moveType);
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
