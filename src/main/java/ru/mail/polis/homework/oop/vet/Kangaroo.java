package ru.mail.polis.homework.oop.vet;

class Kangaroo extends Animal implements WildAnimal {
    private static final int NUM_OF_LEGS = 2;
    private static final String WHAT_SAYS = "Shha";
    private static final MoveType MOVE_TYPE = MoveType.JUMP;
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
