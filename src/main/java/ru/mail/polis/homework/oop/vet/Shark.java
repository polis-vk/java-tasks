package ru.mail.polis.homework.oop.vet;

public class Shark extends Animal implements WildAnimal {

    private String organization;

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
        return organization;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organization = organizationName;
    }
}
