package ru.mail.polis.homework.oop.vet;

public class Kangaroo extends Animal implements WildAnimal {
    private String organisationName;

    public Kangaroo() {
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
        return "Shha";
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
    }
}
