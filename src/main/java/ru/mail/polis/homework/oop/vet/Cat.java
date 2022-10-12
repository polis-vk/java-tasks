package ru.mail.polis.homework.oop.vet;

public class Cat extends Animal implements Pet{

    public Cat(int legs) {
        super(legs);
    }

    @Override
    public String say() {
        return "Mow-Mow";
    }

}
