package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.types.MoveType;
import ru.mail.polis.homework.oop.vet.Animal;

public class PetImpl extends Animal implements Pet {

    public PetImpl(int legs, String voice, MoveType moveType) {
        super(legs, voice, moveType);
    }
}
