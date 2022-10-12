package ru.mail.polis.homework.oop.vet;

public class Cow extends Animal implements Pet {

    public Cow() {
        super(4);
    }

    @Override
    public String say() {
        return "Moo-Moo";
    }
}
