package ru.mail.polis.homework.oop.vet;

public class Pigeon extends Animal implements WildAnimal, Pet {

    private String getOrganizationName;

    public Pigeon() {
        super(2);
    }

    @Override
    public String getOrganizationName() {
        return getOrganizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.getOrganizationName = organizationName;
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
