package ru.mail.polis.homework.simple;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoubleAdvancedTaskTest {
    @Test
    public void equation() {
        assertX(parse(DoubleAdvancedTask.equation(1, -4, -7, 10)), 5, 1, -2);
        assertX(parse(DoubleAdvancedTask.equation(1, 0, 0, 0)), 0, 0, 0);
        assertX(parse(DoubleAdvancedTask.equation(1, -4, 0, 0)), 4, 0, 0);
        assertX(parse(DoubleAdvancedTask.equation(3, 0, -27, 0)), 3, 0, -3);
        assertX(parse(DoubleAdvancedTask.equation(-3, -5, 2, 0)), 0.333333333333, 0, -2);
        assertX(parse(DoubleAdvancedTask.equation(2, 3, -4, -1)), 1, -0.21922359359559, -2.28077640640442);
        assertX(parse(DoubleAdvancedTask.equation(2, 3, -4, 1)), 0.5, 0.41421356237310, -2.41421356237309);
    }

    private double[] parse(String str) {
        String[] arr = str.split(", ");
        return new double[]{Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), Double.parseDouble(arr[2])};
    }

    private void assertX(double[] x12, double expectedX1, double expectedX2, double expectedX3) {
        assertEquals(expectedX1, x12[0], 1e-10);
        assertEquals(expectedX2, x12[1], 1e-10);
        assertEquals(expectedX3, x12[2], 1e-10);
    }

    @Test
    public void length() {
        assertEquals(0, DoubleAdvancedTask.length(1, 1, 2, -1), 0.000001);
        assertEquals(4, DoubleAdvancedTask.length(0, 1, 0, 5), 0.000001);
        assertEquals(0, DoubleAdvancedTask.length(1, 1, 4, 10), 0.000001);
        assertEquals(0, DoubleAdvancedTask.length(-13, 0.000000001, 30, 10), 0.000001);
        assertEquals(0.89442719, DoubleAdvancedTask.length(-2, 4, -2, 2), 0.000001);
    }

    @Test
    public void surfaceFunction() {
        assertEquals(1, DoubleAdvancedTask.surfaceFunction(0, 0, 1,
                1, 1, 1,
                10, 100, 1,
                235, -5), 1e-2);
        assertEquals(-517, DoubleAdvancedTask.surfaceFunction(5, 7, 2,
                2, 0, 4,
                1, -2, -3,
                -10, -5), 1e-2);
        assertEquals(0.6, DoubleAdvancedTask.surfaceFunction(0, 0, 2,
                2, -2, 4,
                1, 4, -3,
                1, 1), 1e-2);
        assertEquals(5.117647, DoubleAdvancedTask.surfaceFunction(4, -1, 5,
                -2, 6, -5,
                5, -5, 7,
                4, -2), 1e-2);
    }

}