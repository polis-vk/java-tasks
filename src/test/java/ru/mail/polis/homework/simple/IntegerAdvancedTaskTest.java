package ru.mail.polis.homework.simple;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerAdvancedTaskTest {
    @Test
    public void progression() {
        assertEquals(0, IntegerAdvancedTask.progression(0, 0, 100));
        assertEquals(5, IntegerAdvancedTask.progression(5, 0, 12));
        assertEquals(0, IntegerAdvancedTask.progression(0, 4, 52));
        assertEquals(7, IntegerAdvancedTask.progression(1, 2, 3));
        assertEquals(-16777215, IntegerAdvancedTask.progression(5, -4, 12));
        assertEquals(67108865, IntegerAdvancedTask.progression(5, -4, 13));
        assertEquals(765, IntegerAdvancedTask.progression(3, 2, 8));
        assertEquals(3, IntegerAdvancedTask.progression(3, 1231233232, 1));
        assertEquals(12, IntegerAdvancedTask.progression(3, 1, 4));
        assertEquals(3, IntegerAdvancedTask.progression(3, 1, 1));
        assertEquals(340, IntegerAdvancedTask.progression(34, 1, 10));
    }

    @Test
    public void snake() {
        assertEquals(2, IntegerAdvancedTask.snake(10, 3, 5, 5, 20, 11));
        assertEquals(Integer.MAX_VALUE, IntegerAdvancedTask.snake(10, 3, 10, 5, 11, 20));
        assertEquals(Integer.MAX_VALUE, IntegerAdvancedTask.snake(1, 2, 3, 4, 3, 2));
        assertEquals(1, IntegerAdvancedTask.snake(5, 2, 39, 4, 4, 3));
        assertEquals(46, IntegerAdvancedTask.snake(100, 10, 200, 8, 100, 23333));
        assertEquals(13, IntegerAdvancedTask.snake(10, 3,2, 4, 32, 100));
        assertEquals(1, IntegerAdvancedTask.snake(5, 2, 39, 4, 2, 23));

    }

    @Test
    public void kDecimal() {
        System.out.println(Integer.MAX_VALUE);
        assertEquals('D', IntegerAdvancedTask.kDecimal(454355, 2));
        assertEquals('9', IntegerAdvancedTask.kDecimal(345644523, 6));
        assertEquals('9', IntegerAdvancedTask.kDecimal(547545, 1));
        assertEquals('7', IntegerAdvancedTask.kDecimal(Integer.MAX_VALUE, 8));
        assertEquals('F', IntegerAdvancedTask.kDecimal(Integer.MAX_VALUE, 5));
        assertEquals('8', IntegerAdvancedTask.kDecimal(23472373, 3));
        assertEquals('1', IntegerAdvancedTask.kDecimal(1, 1));
        assertEquals('1', IntegerAdvancedTask.kDecimal(17, 1));
        assertEquals('2', IntegerAdvancedTask.kDecimal(32, 2));
    }

    @Test
    public void minNumber() {
        System.out.println(Integer.MAX_VALUE);
        assertEquals(1, IntegerAdvancedTask.minNumber(4543552));
        assertEquals(6, IntegerAdvancedTask.minNumber(3456445236L));
        assertEquals(5, IntegerAdvancedTask.minNumber(5475451));
        assertEquals(16, IntegerAdvancedTask.minNumber(Long.MAX_VALUE));
        assertEquals(6, IntegerAdvancedTask.minNumber(12334332339L));
        assertEquals(1, IntegerAdvancedTask.minNumber(234723733));
        assertEquals(1, IntegerAdvancedTask.minNumber(11));
        assertEquals(2, IntegerAdvancedTask.minNumber(171));
        assertEquals(3, IntegerAdvancedTask.minNumber(322));
    }
}