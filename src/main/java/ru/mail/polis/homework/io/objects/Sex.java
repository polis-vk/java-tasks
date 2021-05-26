package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Sex {
    MALE,
    FEMALE,
    OTHER;

    private static final List<Sex> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Sex getRandomSex()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
