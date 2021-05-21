package ru.mail.polis.homework.io.objects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Gender {
    FEMALE,
    MALE,
    UNKNOWN;
    private static final List<Gender> VALUES = Arrays.asList(Gender.values());
    private static final int SIZE = VALUES.size();

    public static Gender getRandom(Random random) {
        return VALUES.get(random.nextInt(SIZE));
    }


}
