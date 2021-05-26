package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Color {
    BLACK,
    RED,
    BLUE,
    WHITE,
    GREEN,
    YELLOW;

    private static final List<Color> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Color getRandomColor()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
