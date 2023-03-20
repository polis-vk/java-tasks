package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если массив null или его длина меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     * 4 тугрика
     */
    public static int[] getMaxArray(int[] array, int count) {
        int[] maxValues = new int[count];
        Arrays.fill(maxValues, Integer.MIN_VALUE);

        if (array == null || count > array.length) return null;
        if (count == 0) {
            return new int[]{};
        }

        for (int elem :
                array) {
            shift(maxValues, elem);
        }
        return maxValues;
    }

    public static int binarySearch(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;

        if (value < array[high]) {
            return -1;
        }

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (array[mid] > value) {
                low = mid + 1;
                continue;
            }
            if (array[mid] < value) {
                high = mid;
                continue;
            }
            return mid;
        }
        return low;
    }

    public static void shift(int[] array, int newValue) {
        int shiftPos = binarySearch(array, newValue);
        if (shiftPos >= 0) {
            for (int i = array.length - 1; i > shiftPos; i--) {
                array[i] = array[i - 1];
            }
            array[shiftPos] = newValue;
        }
    }
}
