package ru.mail.polis.homework.simple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoubleTaskTest {

    @Test
    public void equation() {
        assertX(parse(DoubleTask.equation(1, 5, 4)), -1, -4);
        assertX(parse(DoubleTask.equation(1, 4, 4)), -2, -2);
        assertX(parse(DoubleTask.equation(1, -4, 4)), 2, 2);
        assertX(parse(DoubleTask.equation(3, -5, 2)), 1, 0.6666666666666);
        assertX(parse(DoubleTask.equation(-3, -5, 2)), 0.333333333333, -2);
        assertX(parse(DoubleTask.equation(2, 3, -4)), 0.850781059358, -2.350781059358);
        assertX(parse(DoubleTask.equation(2, -3, -4)), 2.350781059358, -0.850781059358);
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
        assertEquals(5, DoubleTask.length(0, 0, 3, 4), 0.000001);
        assertEquals(5, DoubleTask.length(3, 0, 0, 4), 0.000001);
        assertEquals(1.4142135, DoubleTask.length(0, 1, 1, 0), 0.000001);
        assertEquals(14.142135, DoubleTask.length(0.000000001, 0.000000001, 10, 10), 0.000001);
        assertEquals(20.748939, DoubleTask.length(1.6, 2.444, -1.223, 23), 0.000001);

    }
}