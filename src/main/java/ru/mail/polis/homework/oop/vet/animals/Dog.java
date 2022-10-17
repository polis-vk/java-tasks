package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.MoveType;
import ru.mail.polis.homework.oop.vet.Pet;

public class Dog extends Animal implements Pet {
    public Dog() {
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
