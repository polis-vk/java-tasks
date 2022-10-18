package ru.mail.polis.homework.oop.vet;

public class Pigeon extends Animal implements WildAnimal, Pet {
    private static final int legs = 2;
    private String organizationName;

    public Pigeon() {
        super(legs);
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
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
