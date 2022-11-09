package ru.mail.polis.homework.oop.vet;

public class CatAnimal extends Animal implements Pet {
    CatAnimal() {
        super(4);
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
