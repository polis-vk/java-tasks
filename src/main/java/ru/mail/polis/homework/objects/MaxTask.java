package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array.length < count) {
            return null;
        }
        int[] result = new int[count];
        Arrays.fill(result, Integer.MIN_VALUE);
        int j;
        for (int value : array) {
            for (j = 0; j < count; j++) {
                if (result[j] < value) {
                    break;
                }
            }
            if (j != count) {
                for (int k = count - 1; k != j; k--) {
                    result[k] = result[k - 1];
                }
                result[j] = value;
            }
        }
        return result;
    }
}

