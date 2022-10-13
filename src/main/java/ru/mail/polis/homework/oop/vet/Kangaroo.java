package ru.mail.polis.homework.oop.vet;

public class Kangaroo extends AbstractWildAnimal {
    public Kangaroo() {
        super(2);
    }

    @Override
    public String say() {
        return "Shha";
    }

    @Override
    public MoveType moveType() {
        return MoveType.JUMP;
    }
}
