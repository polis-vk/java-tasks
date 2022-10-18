package ru.mail.polis.homework.oop.vet;

public class Cow extends Animal implements Pet {
    private static final int legs = 4;

    public Cow() {
        super(legs);
    }

    @Override
    public String say() {
        return "Moo-Moo";
    }

    @Override
    public MoveType moveType() {
        return MoveType.RUN;
    }
}
