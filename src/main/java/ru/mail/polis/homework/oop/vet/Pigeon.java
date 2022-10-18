package ru.mail.polis.homework.oop.vet;

public class Pigeon extends AnyWildAnimal implements Pet{

    public Pigeon() {
        super(2);
    }

    @Override
    public String say() {
        return "curls-curls";
    }

    @Override
    public MoveType moveType() {
        return MoveType.FLY;
    }
}
