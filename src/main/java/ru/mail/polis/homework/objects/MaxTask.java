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
        if (array == null || count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[count];
        }
        int[] maximums = new int[count];
        int[] result = new int[count];
        Arrays.fill(maximums, -10);
        for (int j = 0; j < array.length; j++) {
            if (array[j] > maximums[0] && j > maximums.length - 1) {
                maximums[0] = array[j];
                Arrays.sort(maximums);
            }
            if (j < maximums.length) {
                maximums[j] = array[j];
            }
        }
        Arrays.sort(maximums);
        for (int j = 0; j < maximums.length; j++) {
            result[j] = maximums[maximums.length - 1 - j];
        }
        return result;
    }
}
