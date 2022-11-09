package ru.mail.polis.homework.oop.vet;

public class DogAnimal extends Animal implements Pet {
    public DogAnimal() {
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
