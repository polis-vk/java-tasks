package ru.mail.polis.homework.oop.vet;

public class Kangaroo extends Animal implements WildAnimal {

    String organisationName = "";

    public Kangaroo(int legs) {
        super(legs);
    }

    @Override
    public String say() {
        return "Shha";
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
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
