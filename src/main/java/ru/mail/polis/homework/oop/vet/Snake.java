package ru.mail.polis.homework.oop.vet;

public class Snake extends Animal implements Pet, WildAnimal {
    private String organizationName;

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
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
