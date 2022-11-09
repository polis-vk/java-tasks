package ru.mail.polis.homework.io.objects;

import java.util.Random;

public enum AnimalType {
    MAMMAL,
    BIRD,
    REPTILE,
    INVERTEBRATE,
    FISH;

    private static final Random rnd = new Random();

    public static AnimalType randomAnimalType() {
        AnimalType[] animalTypes = values();
        return animalTypes[rnd.nextInt(animalTypes.length)];
    }
}
