package ru.mail.polis.homework.oop.vet;

class Snake extends Animal implements WildAnimal, Pet {
    private static final int NUM_OF_LEGS = 0;
    private static final String WHAT_SAYS = "Shhhh";
    private static final MoveType MOVE_TYPE = MoveType.CRAWL;
    private String organizationName;

    @Override
    public String say() {
        return WHAT_SAYS;
    }

    @Override
    public MoveType moveType() {
        return MOVE_TYPE;
    }

    @Override
    public int getLegs() {
        return NUM_OF_LEGS;
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
