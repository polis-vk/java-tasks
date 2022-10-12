package ru.mail.polis.homework.oop.vet;

import org.graalvm.compiler.lir.sparc.SPARCMove;

public class Snake extends WildAnimalImpl implements Pet {
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
