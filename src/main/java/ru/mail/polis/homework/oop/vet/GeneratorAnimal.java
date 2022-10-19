package ru.mail.polis.homework.oop.vet;

import ru.mail.polis.homework.oop.vet.type.*;

public class GeneratorAnimal {
    private GeneratorAnimal() {
    }

    /**
     * В зависимости от передоваемой строки, должен геенрировать разные виды дочерних объектов
     * класса Animal. Дочерние классы должны создаваться на следующие наборы строк:
     * - cat
     * - dog
     * - kangaroo
     * - pigeon
     * - cow
     * - shark
     * - snake
     * Так же при реализации классов стоит учитывать являются ли животные дикими или домашними
     * и в зависимости от этого они должны реализовывать тот или иной интерфейс.
     *
     * @param animalType - тип животного которое надо создать
     * @return - соответствующего потомка
     */
    public static Animal generateAnimal(String animalType) {
        switch (animalType) {
            case "cat":
                return new Cat();
            case "dog":
                return new Dog();
            case "kangaroo":
                return new Kangaroo();
            case "pigeon":
                return new Pigeon();
            case "cow":
                return new Cow();
            case "shark":
                return new Shark();
            case "snake":
                return new Snake();
        }
        throw new IllegalArgumentException("Cannot find animal type");
    }
}
