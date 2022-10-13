package ru.mail.polis.homework.oop.vet;

public class Snake extends Animal implements WildAnimal, Pet {

    private String getOrganizationName;

    public Snake() {
        super(0);
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
        return "Shhhh";
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
    }
}
