package ru.mail.polis.homework.io.objects;

import java.util.Random;

public class Utils {

    public static final long SEED = 1337;
    public static final Random random = new Random(SEED);

    public static String getRandomString(Random random, int max) {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        final int N = alphabet.length();
        StringBuilder sb = new StringBuilder();
        int end = random.nextInt(max);
        for (int i = 0; i < end; i++) {
            sb.append(alphabet.charAt(random.nextInt(N)));
        }
        return sb.toString();
    }
}
