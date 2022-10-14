package ru.mail.polis.homework.oop.vet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.polis.homework.oop.vet.animals.*;
import ru.mail.polis.homework.oop.vet.types.AnimalType;

import java.util.Locale;

public class GeneratorAnimal {

    private final static Logger log = LoggerFactory.getLogger(GeneratorAnimal.class);

    private GeneratorAnimal() {
    }

    /**
     * В зависимости от передаваемой строки, должен сгенерировать разные виды дочерних объектов
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

        try {
            AnimalType type = AnimalType.valueOf(animalType.toUpperCase(Locale.ROOT));

            switch (type) {
                case CAT:
                    animal = new Cat(4);
                    break;
                case COW:
                    animal = new Cow(4);
                    break;
                case DOG:
                    animal = new Dog(4);
                    break;
                case KANGAROO:
                    animal = new Kangaroo(2);
                    break;
                case PIGEON:
                    animal = new Pigeon(2);
                    break;
                case SHARK:
                    animal = new Shark(0);
                    break;
                case SNAKE:
                    animal = new Snake(0);
                    break;
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid type of animal: {};", animalType);
            e.printStackTrace();
        }

        return animal;
    }
}
