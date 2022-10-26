package ru.mail.polis.homework.oop.vet;

public class Snake extends Animal implements WildAnimal, Pet {
    private String organizationName;

    public Snake() {
        super(0);
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
    }

    @Override
    public String say() {
        return "Shhhh";
    }

}
