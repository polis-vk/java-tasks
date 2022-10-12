package ru.mail.polis.homework.oop.vet;

public class Dog extends Animal implements Pet {

    public Dog() {
        super(4);
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
