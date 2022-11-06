package ru.mail.polis.homework.objects;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class MaxTaskTest {

    @Test
    public void getMaxArray() {
        assertArrayEquals(null, MaxTask.getMaxArray(null, 0));
        assertArrayEquals(new int[] {}, MaxTask.getMaxArray(new int[] {}, 0));
        assertArrayEquals(new int[] {}, MaxTask.getMaxArray(new int[] {1, 2, 3}, 0));
        assertArrayEquals(null, MaxTask.getMaxArray(new int[] {1, 2, 3}, 4));
        assertArrayEquals(new int[] {22, 11}, MaxTask.getMaxArray(new int[] {1, 3, 10, 11, 22, 0}, 2));
        assertArrayEquals(new int[] {22, 22, 11}, MaxTask.getMaxArray(new int[] {1, 3, 22, 11, 22, 0}, 3));
        assertArrayEquals(new int[] {3, 2, 1}, MaxTask.getMaxArray(new int[] {3, 2, 1}, 3));
        assertArrayEquals(new int[] {10, 1}, MaxTask.getMaxArray(new int[] {1, 1, 10}, 2));
        assertArrayEquals(new int[] {-1, -2, -3}, MaxTask.getMaxArray(new int[] {-1, -2, -3}, 3));
        assertArrayEquals(new int[] {-1, -2, -3}, MaxTask.getMaxArray(new int[] {-3, -2, -1}, 3));
        assertArrayEquals(new int[] {10, 9, 8, 8}, MaxTask.getMaxArray(new int[] {5, 7, 1, 8, 9, 1, 4, 5, 10, 2, 8, 3, 1}, 4));
    }
}
