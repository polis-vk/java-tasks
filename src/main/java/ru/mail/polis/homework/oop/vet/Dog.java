package ru.mail.polis.homework.oop.vet;

public class Dog extends Animal implements Pet {
    public Dog(int legs) {
        super(legs);
    }

    @Override
    public String say() {
        return "Wow-wow";
    }
}
