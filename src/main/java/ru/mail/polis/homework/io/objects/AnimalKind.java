package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum AnimalKind {
    FISHES,
    BIRDS,
    REPTILES,
    SPIDERS,
    MAMMALS,
    UNKNOWN,
    INSECTS;

    private static final List<AnimalKind> VALUES = Arrays.asList(AnimalKind.values());
    private static final int SIZE = VALUES.size();

    public static AnimalKind getRandom(Random random) {
        return VALUES.get(random.nextInt(SIZE));
    }

}
