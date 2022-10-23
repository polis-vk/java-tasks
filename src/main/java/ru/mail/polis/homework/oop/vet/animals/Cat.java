package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Cat extends Animal implements Pet {

    public Cat(int legs) {
        super(legs);
    }

    @Override
    public String say() {
        return "Mow-Mow";
    }

    @Override
    public MoveType moveType() {
        return MoveType.RUN;
    }

    @Override
    public int getLegs() {
        return super.getLegs();
    }
}
