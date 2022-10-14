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
        Animal animal = null;
        switch (animalType) {
            case "cat":
                animal = new Cat(4);
                break;

            case "dog":
                animal = new Dog(4);
                break;

            case "kangaroo":
                animal = new Kangaroo(2);
                break;

            case "pigeon":
                animal = new Pigeon(2);
                break;

            case "cow":
                animal = new Cow(4);
                break;

            case "shark":
                animal = new Shark(0);
                break;

            case "snake":
                animal = new Snake(0);
                break;
        }
        return animal;
    }
}
