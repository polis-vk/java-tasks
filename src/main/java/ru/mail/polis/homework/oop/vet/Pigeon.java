package ru.mail.polis.homework.oop.vet;

public class Pigeon extends Animal implements WildAnimal, Pet {
    private String organisationName;

    public Pigeon() {
        super(2);
    }

    @Override
    public String getOrganizationName() {
        return organisationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organisationName = organizationName;
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
