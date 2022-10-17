package ru.mail.polis.homework.oop.vet;

public class Cow extends Animal implements Pet{

    public Cow(int legs) {
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
