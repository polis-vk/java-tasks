package ru.mail.polis.homework.oop.vet;

import ru.mail.polis.homework.oop.vet.Animals.Cat;
import ru.mail.polis.homework.oop.vet.Animals.Cow;
import ru.mail.polis.homework.oop.vet.Animals.Dog;
import ru.mail.polis.homework.oop.vet.Animals.Kangaroo;
import ru.mail.polis.homework.oop.vet.Animals.Pigeon;
import ru.mail.polis.homework.oop.vet.Animals.Shark;
import ru.mail.polis.homework.oop.vet.Animals.Snake;

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
                return new Cat(4);
            case "dog":
                return new Dog(4);
            case "kangaroo":
                return new Kangaroo(2);
            case "pigeon":
                return new Pigeon(2);
            case "cow":
                return new Cow(4);
            case "shark":
                Shark shark = new Shark(0);
                shark.setOrganizationName("GreenPeace");
                return shark;
            case "snake":
                Snake snake = new Snake(0);
                snake.setOrganizationName("GreenPeace");
                return snake;
            default:
                return null;
        }
    }
}
