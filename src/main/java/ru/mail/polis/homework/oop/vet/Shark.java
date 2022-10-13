package ru.mail.polis.homework.oop.vet;

public class Shark extends Animal implements WildAnimal {

    private String getOrganizationName;

    public Shark() {
        super(0);
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
        return "Clack Clack";
    }

    @Override
    public MoveType moveType() {
        return MoveType.SWIM;
    }
}
