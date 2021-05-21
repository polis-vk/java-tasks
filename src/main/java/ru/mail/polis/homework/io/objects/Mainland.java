package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Mainland {
    EUROPE,
    ASIA,
    AFRICA,
    NORTHAMERICA,
    SOUTHAMERICA,
    AUSTRALIA,
    ANTARCTICA,
    UNKNOWN;

    private static final List<Mainland> VALUES = Arrays.asList(Mainland.values());
    private static  final int SIZE = VALUES.size();

    public static  Mainland getRandom(Random random){
        return VALUES.get(random.nextInt(SIZE));
    }
}
