package ru.mail.polis.homework.oop.vet;

public class Snake extends Animal implements WildAnimal, Pet {

    String organisationName = "";

    public Snake(int legs) {
        super(legs);
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
