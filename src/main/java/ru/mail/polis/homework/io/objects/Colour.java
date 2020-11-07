package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Colour {
    WHITE,
    BLACK,
    RED,
    GREEN,
    BLUE,
    UNKNOWN;


    private static final List<Colour> VALUES =
            Arrays.asList(Colour.values());
    private static final int SIZE = VALUES.size();

    public static Colour getRandom(Random random) {
        return VALUES.get(random.nextInt(SIZE));
    }

}
