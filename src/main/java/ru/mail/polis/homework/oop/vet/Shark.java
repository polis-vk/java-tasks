package ru.mail.polis.homework.oop.vet;

public class Shark extends AnyWildAnimal{

    public Shark() {
        super(0);
    }

    @Override
    public String say() {
        return "Clack Clack";
    }

    @Override
    public MoveType moveType() {
        return MoveType.SWIM;
    }
}
