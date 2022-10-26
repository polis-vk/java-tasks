package ru.mail.polis.homework.oop.vet;

public class Cat extends Animal implements Pet {
    public Cat() {
        super(4);
    }

    @Override
    public MoveType moveType() {
        return MoveType.RUN;
    }

    @Override
    public String say() {
        return "Mow-Mow";
    }
}
