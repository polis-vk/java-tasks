package ru.mail.polis.homework.oop.vet;

public class Pigeon extends WildAnimalImpl {
    public Pigeon() {
        super(2);
    }

    @Override
    public MoveType moveType() {
        return MoveType.FLY;
    }

    @Override
    public String say() {
        return "curls-curls";
    }
}
