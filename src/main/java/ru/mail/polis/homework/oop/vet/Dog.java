package ru.mail.polis.homework.oop.vet;

class Dog extends Animal implements Pet {
    private static final int NUM_OF_LEGS = 4;
    private static final String WHAT_SAYS = "Wow-wow";
    private static final MoveType MOVE_TYPE = MoveType.RUN;

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
}
