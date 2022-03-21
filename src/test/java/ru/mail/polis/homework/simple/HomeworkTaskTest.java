package ru.mail.polis.homework.simple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HomeworkTaskTest {

    @Test
    public void calcIntegral() {
        assertEquals(1000.0 / 3.0, HomeworkTask.calcIntegral(0, 10, x -> x * x, 1e-4), 1e-2);
        assertEquals(50.0, HomeworkTask.calcIntegral(0, 10, x -> x, 1e-4), 1e-2);
        assertEquals(0.0, HomeworkTask.calcIntegral(-10, 10, x -> x, 1e-4), 1e-2);
        assertEquals(2200.0, HomeworkTask.calcIntegral(-10, 10, x -> 3 * x * x - 5 * x + 10, 1e-5), 1e-2);
        assertEquals(0, HomeworkTask.calcIntegral(-Math.PI, Math.PI, Math::sin, 1e-5), 1e-2);
        assertEquals(60, HomeworkTask.calcIntegral(-10, 10, x -> 3, 1e-5), 1e-2);
        assertEquals(-1.08, HomeworkTask.calcIntegral(-10, 10, Math::cos, 1e-6), 1e-2);
        assertEquals(20, HomeworkTask.calcIntegral(-10, 10, x -> Math.sin(x) * Math.sin(x) + Math.cos(x) * Math.cos(x), 1e-4), 1e-2);
    }

    @Test
    public void maxNumber() {
//        assertEquals(2, HomeworkTask.maxNumber(892347384743343L));
//        assertEquals(1, HomeworkTask.maxNumber(99999999999999L));
//        assertEquals(1, HomeworkTask.maxNumber(0L));
//        assertEquals(1, HomeworkTask.maxNumber(1L));
//        assertEquals(2, HomeworkTask.maxNumber(12L));
//        assertEquals(7, HomeworkTask.maxNumber(62467293));
//        assertEquals(5, HomeworkTask.maxNumber(62467237));
//        assertEquals(1, HomeworkTask.maxNumber(Long.MIN_VALUE));
        assertEquals(1, HomeworkTask.maxNumber(Long.MIN_VALUE - 1));
    }

    @Test
    public void lineFunction() {
        assertEquals(10, HomeworkTask.lineFunction(0, 0, 3, 3, 10), 1e-2);
        assertEquals(0, HomeworkTask.lineFunction(0, 0, 2, 3, 0), 1e-2);
        assertEquals(-3, HomeworkTask.lineFunction(0, 0, -2, 2, 3), 1e-2);
        assertEquals(1, HomeworkTask.lineFunction(1, 1, 23, 1, 3), 1e-2);
        assertEquals(18, HomeworkTask.lineFunction(25, 28, -5, 13, 5), 1e-2);
        assertEquals(1.5, HomeworkTask.lineFunction(0, 0, 2, 3, 1), 1e-2);
    }

    @Test
    public void square() {
        assertEquals(0, HomeworkTask.square(0, 0, 0, 0, 0, 0, 0, 0), 1e-2);
        assertEquals(4, HomeworkTask.square(1, 1, 1, -1, -1, -1, -1, 1), 1e-2);
        assertEquals(2, HomeworkTask.square(0, -1, -1, 0, 0, 1, 1, 0), 1e-2);
        assertEquals(2, HomeworkTask.square(5, 4, 4, 5, 5, 6, 6, 5), 1e-2);
        assertEquals(17.5, HomeworkTask.square(1, 4, 3, 2, 8, 11, 6, 10), 1e-2);
        assertEquals(16, HomeworkTask.square(2, 4, 4, 7, 9, 13, 6, 5), 1e-2);
    }
}