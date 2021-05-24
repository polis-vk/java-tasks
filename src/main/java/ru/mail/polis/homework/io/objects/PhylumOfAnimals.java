package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum PhylumOfAnimals {
    ANNELIDS,
    ARTHROPODS,
    BRYOZOA,
    CHORDATES,
    CNIDARIA,
    ENCHINODERMS,
    MOLLUSCS,
    NEMATODES,
    PLATYHELMINTHES,
    ROTIFERS,
    SPONGES;

    private static final List<PhylumOfAnimals> VALUES = Arrays.asList(PhylumOfAnimals.values());
    private static final int SIZE = VALUES.size();

    public static PhylumOfAnimals getRandom(Random random) {
        return VALUES.get(random.nextInt(SIZE));
    }
}