package ru.mail.polis.homework.retake.first;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTest {

    @Test
    public void sum() {
        assertEquals(0, Simple.sum(0));
        assertEquals(15, Simple.sum(5));
        assertEquals(55, Simple.sum(10));
        assertEquals(50005000, Simple.sum(10000));
    }

    @Test
    public void snake() {
        assertEquals(8, Simple.snake(10, 3, 2));
        assertEquals(Integer.MAX_VALUE, Simple.snake(10, 2, 2));
        assertEquals(Integer.MAX_VALUE, Simple.snake(100, 10, 20));
        assertEquals(1, Simple.snake(10, 10, 199));
        assertEquals(46, Simple.snake(100, 10, 8));
        assertEquals(13, Simple.snake(100, 10, 2));
        assertEquals(1, Simple.snake(10, 100, 101));

    }

    @Test
    public void kDecimal() {
        assertEquals(0, Simple.kDecimal(10, 1));
        assertEquals(1, Simple.kDecimal(10, 2));
        assertEquals(1, Simple.kDecimal(1010, 2));
        assertEquals(8, Simple.kDecimal(Integer.MAX_VALUE, 5));
        assertEquals(3, Simple.kDecimal(23472373, 3));
        assertEquals(7, Simple.kDecimal(-23472373, 2));
    }

    @Test
    public void factorial() {
        assertEquals(1, Simple.factorial((byte) 0));
        assertEquals(1, Simple.factorial((byte) 1));
        assertEquals(2, Simple.factorial((byte) 2));
        assertEquals(120, Simple.factorial((byte) 5));
        assertEquals(3_628_800, Simple.factorial((byte) 10));
        assertEquals(479_001_600, Simple.factorial((byte) 12));
        assertEquals(2_432_902_008_176_640_000L, Simple.factorial((byte) 20));
    }

    @Test
    public void equation() {
        assertX(parse(Simple.equation(1, 5, 4)), -1, -4);
        assertX(parse(Simple.equation(1, 4, 4)), -2, -2);
        assertX(parse(Simple.equation(1, -4, 4)), 2, 2);
        assertX(parse(Simple.equation(3, -5, 2)), 1, 0.6666666666666);
        assertX(parse(Simple.equation(-3, -5, 2)), 0.333333333333, -2);
        assertX(parse(Simple.equation(2, 3, -4)), 0.850781059358, -2.350781059358);
        assertX(parse(Simple.equation(2, -3, -4)), 2.350781059358, -0.850781059358);
    }

    private double[] parse(String str) {
        String[] arr = str.split(", ");
        return new double[] {Double.parseDouble(arr[0]), Double.parseDouble(arr[1])};
    }

    private void assertX(double[] x12, double expectedX1, double expectedX2) {
        assertEquals(expectedX1, x12[0], 1e-10);
        assertEquals(expectedX2, x12[1], 1e-10);
    }

    @Test
    public void length() {
        assertEquals(5, Simple.length(0, 0, 3, 4), 0.000001);
        assertEquals(5, Simple.length(3, 0, 0, 4), 0.000001);
        assertEquals(1.4142135, Simple.length(0, 1, 1, 0), 0.000001);
        assertEquals(14.142135, Simple.length(0.000000001, 0.000000001, 10, 10), 0.000001);
        assertEquals(20.748939, Simple.length(1.6, 2.444, -1.223, 23), 0.000001);

    }

}