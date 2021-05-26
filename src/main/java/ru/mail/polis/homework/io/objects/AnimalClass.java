package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum AnimalClass {
    MAMMAL,
    BIRD,
    REPTILE,
    FISH,
    AMPHIBIAN,
    INVERTEBRATE;

    private static final List<AnimalClass> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static AnimalClass getRandomAnimalClass()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}


