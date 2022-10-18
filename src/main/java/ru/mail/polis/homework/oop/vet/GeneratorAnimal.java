package ru.mail.polis.homework.oop.vet;

public class GeneratorAnimal {
    private static final String ANIMAL_TYPE_CAT = "cat";
    private static final String ANIMAL_TYPE_DOG = "dog";
    private static final String ANIMAL_TYPE_KANGAROO = "kangaroo";
    private static final String ANIMAL_TYPE_PIGEON = "pigeon";
    private static final String ANIMAL_TYPE_COW = "cow";
    private static final String ANIMAL_TYPE_SHARK = "shark";
    private static final String ANIMAL_TYPE_SNAKE = "snake";

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
        switch (animalType) {
            case ANIMAL_TYPE_CAT:
                animal = new Cat(4);
                break;
            case ANIMAL_TYPE_DOG:
                animal = new Dog(4);
                break;
            case ANIMAL_TYPE_KANGAROO:
                animal = new Kangaroo(2);
                break;
            case ANIMAL_TYPE_PIGEON:
                animal = new Pigeon(2);
                break;
            case ANIMAL_TYPE_SHARK:
                animal = new Shark(0);
                break;
            case ANIMAL_TYPE_COW:
                animal = new Cow(4);
                break;
            case ANIMAL_TYPE_SNAKE:
                animal = new Snake(0);
                break;
        }
        return animal;
    }
}
