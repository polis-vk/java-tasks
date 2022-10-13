package ru.mail.polis.homework.oop.vet;

public class Kangaroo extends Animal implements WildAnimal {

    private String getOrganizationName;

    public Kangaroo() {
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
        return "Shha";
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
    }
}
