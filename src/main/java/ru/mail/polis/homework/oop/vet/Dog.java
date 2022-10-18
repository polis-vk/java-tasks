package ru.mail.polis.homework.oop.vet;

public class Dog extends Animal implements Pet{
    private static final int legs = 4;

    public Dog() {
        super(legs);
    }

    @Override
    public String say() {
        return "Wow-wow";
    }

    @Override
    public MoveType moveType() {
        return MoveType.RUN;
    }
}
