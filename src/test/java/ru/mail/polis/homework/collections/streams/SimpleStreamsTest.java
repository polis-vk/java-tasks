package ru.mail.polis.homework.collections.streams;

import junit.framework.TestCase;
import org.junit.Test;

public class SimpleStreamsTest extends TestCase {

    @Test
    public void testCalcDistance() {
        double a = SimpleStreams.calcDistance(50, n -> 50 - n * 5, Math.toRadians(30), 2);
        assertTrue(362.31 - a <= 0.02);

        a = SimpleStreams.calcDistance(50, n -> 0, Math.toRadians(30), 3);
        assertTrue(220.92 - a <= 0.02);

        a = SimpleStreams.calcDistance(30, n -> 30 - 2 * n, Math.toRadians(90), 30);
        assertTrue(0 - a <= 0.02);
    }
}