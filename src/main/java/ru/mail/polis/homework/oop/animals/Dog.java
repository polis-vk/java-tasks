package ru.mail.polis.homework.oop.animals;

import ru.mail.polis.homework.oop.types.MoveType;
import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.Pet;

public class Dog extends Animal implements Pet {
    public Dog(int legs) {
        super(legs);
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
