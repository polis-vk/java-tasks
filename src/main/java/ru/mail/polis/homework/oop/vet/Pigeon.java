package ru.mail.polis.homework.oop.vet;

public class Pigeon extends Animal implements Pet, WildAnimal {

    private String organization;

    public Pigeon(int legs) {
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
        return organization;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organization = organizationName;
    }
}
