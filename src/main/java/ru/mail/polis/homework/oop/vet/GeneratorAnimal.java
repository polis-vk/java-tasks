package ru.mail.polis.homework.oop.vet;

public class GeneratorAnimal {
    private GeneratorAnimal() {
    }

    /**
     * В зависимости от передаваемой строки, должен генерировать разные виды дочерних объектов
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
            case ("cat"):
                return new CatAnimal(animalType);
            case ("dog"):
                return new DogAnimal(animalType);
            case ("cow"):
                return new CowAnimal(animalType);
            case ("kangaroo"):
                return new KangarooAnimal(animalType);
            case ("shark"):
                return new SharkAnimal(animalType);
            case ("pigeon"):
                return new PigeonAnimal(animalType);
            case ("snake"):
                return new SnakeAnimal(animalType);
        }
        throw new IllegalArgumentException(animalType);
    }
}
