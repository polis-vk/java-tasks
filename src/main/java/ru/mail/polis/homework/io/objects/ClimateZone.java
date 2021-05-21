package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum ClimateZone {
    TUNDRA,
    FOREST,
    STEPPES,
    MOUNTAIN,
    TROPICS,
    UNKNOWN;

    private static final List<ClimateZone> VALUES = Arrays.asList(ClimateZone.values());
    private static  final int SIZE = VALUES.size();

    public static  ClimateZone getRandom(Random random){
        return VALUES.get(random.nextInt(SIZE));
    }
}
