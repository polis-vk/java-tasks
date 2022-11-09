package ru.mail.polis.homework.oop.vet;

public class KangarooAnimal extends Animal implements WildAnimal {
    public KangarooAnimal() {
        super(2);
    }

    private String organizationName = "";

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
