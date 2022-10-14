package ru.mail.polis.homework.oop.vet;

import java.util.NoSuchElementException;

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
        Animal generatedAnimal;
        switch (animalType) {
            case "cat":
                generatedAnimal = new Cat();
                break;
            case "dog":
                generatedAnimal = new Dog();
                break;
            case "kangaroo":
                generatedAnimal = new Kangaroo();
                break;
            case "pigeon":
                generatedAnimal = new Pigeon();
                break;
            case "cow":
                generatedAnimal = new Cow();
                break;
            case "shark":
                generatedAnimal = new Shark();
                break;
            case "snake":
                generatedAnimal = new Snake();
                break;
            default:
                throw new NoSuchElementException();
        }
        return generatedAnimal;
    }
}
