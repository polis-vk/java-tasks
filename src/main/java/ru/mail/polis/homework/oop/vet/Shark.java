package ru.mail.polis.homework.oop.vet;

public class Shark extends Animal implements WildAnimal {

    String organisationName = "";

    public Shark(int legs) {
        super(legs);
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
        return organisationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organisationName = organizationName;
    }
}
