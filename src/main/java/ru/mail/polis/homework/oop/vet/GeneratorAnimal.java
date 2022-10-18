package ru.mail.polis.homework.oop.vet;

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
        return switch (animalType) {
            case "cat" -> new Cat();
            case "kangaroo" -> new Kangaroo();
            case "dog" -> new Dog();
            case "pigeon" -> new Pigeon();
            case "cow" -> new Cow();
            case "shark" -> new Shark();
            case "snake" -> new Snake();
            default -> null;
        };
    }
}
