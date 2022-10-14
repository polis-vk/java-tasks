package ru.mail.polis.homework.oop.vet;

public class Snake extends Animal implements WildAnimal, Pet {
    private String organisationName;

    public Snake() {
        super(0);
    }

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
        return organisationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organisationName = organizationName;
    }
}
