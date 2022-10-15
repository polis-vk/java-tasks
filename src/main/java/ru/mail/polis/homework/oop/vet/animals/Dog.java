package ru.mail.polis.homework.oop.vet.animals;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.LegsNumber;
import ru.mail.polis.homework.oop.vet.Pet;

public class Dog extends Animal implements Pet {
    private final static String DOG_SOUND = "Wow-wow";

    public Dog() {
        super(LegsNumber.FOUR.getNumber());
    }

    @Override
    public String say() {
        return DOG_SOUND;
    }
}
