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
        if (array == null || array.length < count) {
            return null;
        }

        int[] maxArray = new int[count];

        if (count == 0) {
            return maxArray;
        }

        Arrays.fill(maxArray, Integer.MIN_VALUE);

        for (int value : array) {
            for (int j = 0; j < count; ++j) {
                if (value >= maxArray[j]) {
                    for (int k = count - 2; k >= j; --k) {
                        maxArray[k + 1] = maxArray[k];
                    }
                    maxArray[j] = value;
                    break;
                }
            }
        }
        return maxArray;
    }

}
