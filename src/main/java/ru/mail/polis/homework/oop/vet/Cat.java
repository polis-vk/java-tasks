package ru.mail.polis.homework.oop.vet;

public class Cat extends Animal implements Pet {
    private static final int legs = 4;

    public Cat() {
        super(legs);
    }

    @Override
    public String say() {
        return "Mow-Mow";
    }

    @Override
    public MoveType moveType() {
       return MoveType.RUN;
    }
}
