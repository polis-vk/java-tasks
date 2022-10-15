package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.LegsNumber;
import ru.mail.polis.homework.oop.vet.Pet;

public class Cat extends Animal implements Pet {
    private final static String CAT_SOUND = "Mow-Mow";

    public Cat() {
        super(LegsNumber.FOUR.getNumber());
    }

    @Override
    public String say() {
        return CAT_SOUND;
    }
}
