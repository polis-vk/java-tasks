package ru.mail.polis.homework.oop.vet;

public class SnakeAnimal extends Animal implements WildAnimal, Pet{
    public SnakeAnimal() {
        super(0);
    }

    private String organizationName = "";

    @Override
    public String say() {
        return "Shhhh";
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
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
