package ru.mail.polis.homework.oop.vet;

public class Shark extends Animal implements WildAnimal {
    private String organisationName;

    public Shark() {
        super(0);
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
        return "Clack Clack";
    }

    @Override
    public MoveType moveType() {
        return MoveType.SWIM;
    }
}
