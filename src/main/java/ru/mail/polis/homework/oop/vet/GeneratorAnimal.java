package ru.mail.polis.homework.oop.vet;

public class GeneratorAnimal {
    private GeneratorAnimal() {
    }

    private static final String CAT = "cat";
    private static final String DOG = "dog";
    private static final String COW = "cow";
    private static final String KANGAROO = "kangaroo";
    private static final String PIGEON = "pigeon";
    private static final String SHARK = "shark";
    private static final String SNAKE = "snake";

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
            case CAT:
                return new Cat();
            case DOG:
                return new Dog();
            case COW:
                return new Cow();
            case KANGAROO:
                return new Kangaroo();
            case PIGEON:
                return new Pigeon();
            case SHARK:
                return new Shark();
            case SNAKE:
                return new Snake();
        }
        return null;
    }
}
