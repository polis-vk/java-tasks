package ru.mail.polis.homework.oop.vet;

import ru.mail.polis.homework.oop.vet.animals.PetImpl;
import ru.mail.polis.homework.oop.vet.animals.SemiWildAnimal;
import ru.mail.polis.homework.oop.vet.animals.WildAnimalImpl;
import ru.mail.polis.homework.oop.vet.types.AnimalParamsType;

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
        try {
            AnimalParamsType animal = AnimalParamsType.valueOf(animalType.toUpperCase());
            switch (animal.getFreedomType()) {
                case WILD:
                    return new WildAnimalImpl(
                            animal.getLegs(),
                            animal.getVoice(),
                            animal.getMoveType()
                    );
                case PET:
                    return new PetImpl(
                            animal.getLegs(),
                            animal.getVoice(),
                            animal.getMoveType()
                    );
                case ALL:
                    return new SemiWildAnimal(
                            animal.getLegs(),
                            animal.getVoice(),
                            animal.getMoveType()
                    );
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
