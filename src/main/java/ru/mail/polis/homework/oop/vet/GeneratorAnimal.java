package ru.mail.polis.homework.oop.vet;

import ru.mail.polis.homework.oop.animals.*;
import ru.mail.polis.homework.oop.types.AnimalType;

import java.util.Locale;

public class GeneratorAnimal {
    private final static int FOUR_LEGS = 4;
    private final static int TWO_LEGS = 2;
    private final static int NO_LEGS = 0;

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
        Animal animal = null;

        AnimalType typename = AnimalType.valueOf(animalType.toUpperCase(Locale.ROOT));

        switch (typename) {
            case CAT:
                animal = new Cat(FOUR_LEGS);
                break;
            case COW:
                animal = new Cow(FOUR_LEGS);
                break;
            case DOG:
                animal = new Dog(FOUR_LEGS);
                break;
            case SHARK:
                animal = new Shark(NO_LEGS);
                break;
            case SNAKE:
                animal = new Snake(NO_LEGS);
                break;
            case PIGEON:
                animal = new Pigeon(TWO_LEGS);
                break;
            case KANGAROO:
                animal = new Kangaroo(TWO_LEGS);
                break;
        }

        return animal;
    }
}
