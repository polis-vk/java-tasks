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
        if (array == null || count > array.length) {
            return null;
        }
        int[] result = new int[count];
        int[] tempArr = Arrays.copyOf(array, array.length);
        for (int i = 0; i < count; i++) {
            int maxValue = Integer.MIN_VALUE;
            int index = 0;
            for (int j = 0; j < array.length; j++) {
                if (tempArr[j] > maxValue) {
                    maxValue = array[j];
                    index = j;
                }
            }
            tempArr[index] = Integer.MIN_VALUE;
            result[i] = maxValue;
        }
        return result;
    }
}

