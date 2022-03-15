package ru.mail.polis.homework.simple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerTaskTest {

    @Test
    public void sum() {
        assertEquals(0, IntegerTask.sum(0));
        assertEquals(15, IntegerTask.sum(5));
        assertEquals(55, IntegerTask.sum(10));
        assertEquals(50005000, IntegerTask.sum(10000));
    }

    @Test
    public void snake() {
        assertEquals(8, IntegerTask.snake(10, 3, 2));
        assertEquals(Integer.MAX_VALUE, IntegerTask.snake(10, 2, 2));
        assertEquals(Integer.MAX_VALUE, IntegerTask.snake(100, 10, 20));
        assertEquals(1, IntegerTask.snake(10, 10, 199));
        assertEquals(46, IntegerTask.snake(100, 10, 8));
        assertEquals(13, IntegerTask.snake(100, 10, 2));
        assertEquals(1, IntegerTask.snake(10, 100, 101));

    }

    @Test
    public void kDecimal() {
        assertEquals(0, IntegerTask.kDecimal(10, 1));
        assertEquals(1, IntegerTask.kDecimal(10, 2));
        assertEquals(1, IntegerTask.kDecimal(1010, 2));
        assertEquals(4, IntegerTask.kDecimal(Integer.MIN_VALUE, 6));
        assertEquals(8, IntegerTask.kDecimal(Integer.MAX_VALUE, 5));
        assertEquals(3, IntegerTask.kDecimal(23472373, 3));
        assertEquals(7, IntegerTask.kDecimal(-23472373, 2));
    }

    @Test
    public void factorial() {
        assertEquals(1, IntegerTask.factorial((byte) 0));
        assertEquals(1, IntegerTask.factorial((byte) 1));
        assertEquals(2, IntegerTask.factorial((byte) 2));
        assertEquals(120, IntegerTask.factorial((byte) 5));
        assertEquals(3_628_800, IntegerTask.factorial((byte) 10));
        assertEquals(479_001_600, IntegerTask.factorial((byte) 12));
        assertEquals(2_432_902_008_176_640_000L, IntegerTask.factorial((byte) 20));
    }
}