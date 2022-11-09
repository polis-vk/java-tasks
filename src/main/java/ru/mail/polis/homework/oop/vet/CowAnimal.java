package ru.mail.polis.homework.oop.vet;

public class CowAnimal extends Animal implements Pet {
    public CowAnimal() {
        super(4);
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
