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
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (array == null || count > array.length) {
            return null;
        }
        int[] result = new int[count];
        if (count != 0) {
            Arrays.fill(result, Integer.MIN_VALUE);
            for (int element : array) {
                if (element > result[count - 1]) {
                    result[count - 1] = element;
                    for (int i = count - 2; i >= 0; i--) {
                        if (element > result[i]) {
                            result[i + 1] = result[i];
                            result[i] = element;
                        }
                    }
                }
            }
        }
        return result;
    }

}
