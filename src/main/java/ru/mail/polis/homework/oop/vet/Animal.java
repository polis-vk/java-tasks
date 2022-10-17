package ru.mail.polis.homework.oop.vet;

/**
 * Дефолтный класс животного от которого необходимо наследоваться
 */
public abstract class Animal {

    public abstract String say();

    public abstract MoveType moveType();

    public abstract int getLegs();
}
