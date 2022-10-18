package ru.mail.polis.homework.oop.vet;

public class Shark extends Animal implements WildAnimal {
    private static final int legs = 0;
    private String organizationName;

    public Shark() {
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
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
