package ru.mail.polis.homework.objects;

import java.util.Arrays;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {
        if (count > array.length) {
            return null;
        }
        if (count == 0) {
            return new int[0];
        }
        int[] result = new int[count];
        Arrays.fill(result, Integer.MIN_VALUE);
        for (int number : array) {
            if (number > result[count - 1]) {
                result[count - 1] = number;
                for (int i = count - 1; i > 0; i--) {
                    if (result[i] > result[i - 1]) {
                        int temp = result[i];
                        result[i] = result[i - 1];
                        result[i - 1] = temp;
                    }
                }
            }
        }
        return result;
    }

}
