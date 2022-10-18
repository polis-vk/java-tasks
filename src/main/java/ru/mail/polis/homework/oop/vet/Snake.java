package ru.mail.polis.homework.oop.vet;

public class Snake extends Animal implements WildAnimal, Pet {
    private static final int legs = 0;
    private String organizationName;

    public Snake() {
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
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
