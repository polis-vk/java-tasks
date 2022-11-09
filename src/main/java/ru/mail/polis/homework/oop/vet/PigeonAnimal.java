package ru.mail.polis.homework.oop.vet;

public class PigeonAnimal extends Animal implements WildAnimal, Pet {
    public PigeonAnimal() {
        super(2);
    }

    @Override
    public String say() {
        return "curls-curls";
    }

    @Override
    public MoveType moveType() {
        return MoveType.FLY;
    }

    private String organizationName = "";

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
