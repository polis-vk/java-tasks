package ru.mail.polis.homework.oop.vet;

public class Kangaroo extends Animal implements Pet, WildAnimal{
    private String organizationName;

    public Kangaroo() {
        super(2);
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
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}