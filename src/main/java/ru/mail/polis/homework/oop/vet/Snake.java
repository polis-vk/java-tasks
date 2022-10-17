package ru.mail.polis.homework.oop.vet;

public class Snake extends Animal implements Pet, WildAnimal {
    private static final int NUMBER_OF_LEGS = 0;
    private static final String VOICE = "Shhhh";
    private static final MoveType MOVE_TYPE = MoveType.CRAWL;
    private String organizationName;

    @Override
    public String say() {
        return VOICE;
    }

    @Override
    public MoveType moveType() {
        return MOVE_TYPE;
    }

    @Override
    public int getLegs() {
        return NUMBER_OF_LEGS;
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
