package ru.mail.polis.homework.oop.vet;

public class Snake extends AnyWildAnimal implements Pet{

    public Snake() {
        super(0);
    }

    @Override
    public String say() {
        return "Shhhh";
    }

    @Override
    public MoveType moveType() {
        return MoveType.CRAWL;
    }
}
