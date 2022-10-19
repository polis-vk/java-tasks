package ru.mail.polis.homework.oop.vet.type;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.Pet;

public class Cat extends Animal implements Pet {

    public Cat() {
        super(4);
    }

    @Override
    public String say() {
        return "Mow-Mow";
    }

}
