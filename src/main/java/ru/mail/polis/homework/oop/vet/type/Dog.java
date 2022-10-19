package ru.mail.polis.homework.oop.vet.type;

import ru.mail.polis.homework.oop.vet.Animal;
import ru.mail.polis.homework.oop.vet.Pet;

public class Dog extends Animal implements Pet {

    public Dog() {
        super(4);
    }

    @Override
    public String say() {
        return "Wow-wow";
    }

}
