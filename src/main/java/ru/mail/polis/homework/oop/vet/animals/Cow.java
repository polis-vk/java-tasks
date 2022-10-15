package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.LegsNumber;
import ru.mail.polis.homework.oop.vet.Pet;

public class Cow extends Animal implements Pet {
    private final static String COW_SOUND = "Moo-Moo";

    public Cow() {
        super(LegsNumber.FOUR.getNumber());
    }

    @Override
    public String say() {
        return COW_SOUND;
    }
}
