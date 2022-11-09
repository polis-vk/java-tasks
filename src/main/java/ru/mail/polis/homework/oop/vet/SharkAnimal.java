package ru.mail.polis.homework.oop.vet;

public class SharkAnimal extends Animal implements WildAnimal {
    public SharkAnimal() {
        super(0);
    }

    private String organizationName = "";

    @Override
    public String say() {
        return "Сlack Сlack";
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
